package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.domain.DistanceFare;
import subway.path.domain.LineFare;
import subway.path.domain.LoginMemberAgeFare;
import subway.path.domain.Path;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {

    private final LineService lineService;
    private final StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public Path findPaths(long source, long target, LoginMember loginMember) {
        WeightedMultigraph<Station, DistanceLineEdge> graph = new WeightedMultigraph<>(DistanceLineEdge.class);

        List<Line> lines = lineService.findLines();
        for (Line line : lines) {
            for (Section section : line.getSections()) {
                graph.addVertex(section.getUpStation());
                graph.addVertex(section.getDownStation());
                graph.addEdge(section.getUpStation(), section.getDownStation(), new DistanceLineEdge(section.getDistance(), line));
            }
        }

        DijkstraShortestPath<Station, DistanceLineEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, DistanceLineEdge> graphPath
                = dijkstraShortestPath.getPath(stationService.findStationById(source), stationService.findStationById(target));

        int fare = DistanceFare.getFare((int) graphPath.getWeight());
        List<Station> stations = graphPath.getVertexList();
        List<Line> targetLines = graphPath.getEdgeList()
                .stream()
                .map(DistanceLineEdge::getLine)
                .collect(Collectors.toList());
        fare += LineFare.getFare(targetLines);

        if (!loginMember.equals(LoginMember.NOT_LOGINED)) {
            fare = LoginMemberAgeFare.getFare(loginMember, fare);
        }

        return new Path(stations, (int) graphPath.getWeight(), fare);
    }

    static class DistanceLineEdge extends DefaultWeightedEdge {
        private int distance;
        private Line line;

        public DistanceLineEdge(int distance, Line line) {
            this.distance = distance;
            this.line = line;
        }

        @Override
        protected double getWeight() {
            return distance;
        }

        public Line getLine() {
            return line;
        }
    }
}
