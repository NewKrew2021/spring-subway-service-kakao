package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PathService {
    private StationDao stationDao;
    private LineDao lineDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findMinDistance(LoginMember loginMember, Long source, Long target) {
        return dijkstra(loginMember, source, target);
    }

    private DijkstraShortestPath dijkstra2(List<Line> lines) {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station.getId())));

        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation().getId(),
                        section.getDownStation().getId()), section.getDistance())));

        return new DijkstraShortestPath(graph);
    }

    private PathResponse dijkstra(LoginMember loginMember, Long source, Long target) {
        List<Line> lines = lineDao.findAll();
        DijkstraShortestPath dijkstraShortestPath = dijkstra2(lines);
        List<Long> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int totalDistance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new PathResponse(shortestPath.stream()
                .map(id -> StationResponse.of(stationDao.findById(id)))
                .collect(Collectors.toList()),
                totalDistance, getTotalFare(loginMember, getMaxExtraFare(getShortestPathSections(shortestPath), lines) + calculateDistanceFare(totalDistance)));
    }

    private int getTotalFare(LoginMember loginMember, int totalFare) {
        if (loginMember.isLoginMember() && 13 <= loginMember.getAge() && loginMember.getAge() < 19) {
            totalFare -= (totalFare - 350) * 0.2;
        }
        if (loginMember.isLoginMember() && 6 <= loginMember.getAge() && loginMember.getAge() < 13) {
            totalFare -= (totalFare - 350) * 0.5;
        }

        return totalFare;
    }

    private int calculateDistanceFare(int totalDistance) {
        int distanceFare = 1250;

        if (10 < totalDistance && totalDistance <= 50) {
            distanceFare += Math.ceil((totalDistance - 10) / (double) 5) * 100;
        }
        if (totalDistance > 50) {
            distanceFare += 800 + Math.ceil((totalDistance - 50) / (double) 8) * 100;
        }
        return distanceFare;
    }

    private List<Section> getShortestPathSections(List<Long> shortestPath) {
        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            sections.add(new Section(stationDao.findById(shortestPath.get(i)), stationDao.findById(shortestPath.get(i + 1))));
        }
        return sections;
    }

    private int getMaxExtraFare(List<Section> sections, List<Line> lines) {
        int maxExtraFare = 0;
        for (Line line : lines) {
            for (Section section : sections) {
                if (line.getSections().isExist(section) && maxExtraFare < line.getExtraFare()) {
                    maxExtraFare = line.getExtraFare();
                }
            }
        }
        return maxExtraFare;
    }
}
