package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.path.domain.AgeFare;
import subway.path.domain.DistanceFare;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PathService {
    private LineDao lineDao;
    private StationDao stationDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.lineDao = lineDao;
        this.stationDao = stationDao;
    }

    public PathResponse findShortestPathResponse(LoginMember loginMember, Long source, Long target) {
        Path shortestPath = getShortestPath(loginMember, stationDao.findById(source), stationDao.findById(target));
        return PathResponse.of(shortestPath);
    }

    public Path getShortestPath(LoginMember loginMember, Station source, Station target) {
        List<Line> lines = lineDao.findAll();
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        addStationVertex(graph, lines);
        addSectionEdge(graph, lines);

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        List<Station> shortestPathOnlyVertex
                = dijkstraShortestPath.getPath(source, target).getVertexList();
        int shortestDistance = (int) dijkstraShortestPath.getPathWeight(source, target);

        DistanceFare distanceFare = DistanceFare.getDistanceFare(shortestDistance);
        int fare = distanceFare.calculateExtraFareByDistance(shortestDistance);
        fare += getMaxExtraFareOfLine(shortestPathOnlyVertex, lines);
        if (loginMember != null) {
            AgeFare ageFare = AgeFare.getAgeFare(loginMember.getAge());
            fare -= ageFare.calculateDiscountFareByAge(fare);
        }

        return new Path(shortestPathOnlyVertex, shortestDistance, fare);
    }

    private void addStationVertex(WeightedMultigraph<Station, DefaultWeightedEdge> graph, List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void addSectionEdge(WeightedMultigraph<Station, DefaultWeightedEdge> graph, List<Line> lines) {
        lines.forEach(line -> {
            Sections sections = line.getSections();
            sections.getSections()
                    .forEach(section -> graph.setEdgeWeight(
                            graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
                    );
        });
    }

    private int getMaxExtraFareOfLine(List<Station> shortestPathOnlyVertex, List<Line> lines) {
        Sections allSections = new Sections();
        lines.forEach(line -> allSections.addSections(line.getSections()));
        Set<Integer> extraFares = new HashSet<>();
        for (int i = 0; i < shortestPathOnlyVertex.size() - 1; i++) {
            Station upStation = shortestPathOnlyVertex.get(i);
            Station downStation = shortestPathOnlyVertex.get(i + 1);
            Section section = allSections.getSection(upStation, downStation);
            extraFares.add(lines.stream()
                    .filter(line -> line.getSections().hasSection(section))
                    .findFirst()
                    .get()
                    .getExtraFare());
        }

        return Collections.max(extraFares);
    }
}