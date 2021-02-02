package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Path {
    private static final int BASE_FARE = 1250;
    private static final int HUNDRED = 100;
    private static final int BASIC_FARE_DISTANCE = 10;
    private static final int EXTRA_FARE_DISTANCE = 50;
    private static final int OVER_EXTRA_DISTANCE_FARE = 800;
    private static final double MIDDLE_DISTANCE_DENOMINATOR = 5;
    private static final double HIGH_DISTANCE_DENOMINATOR = 8;
    private final DijkstraShortestPath dijkstraShortestPath;
    private final Lines lines;

    public Path(Lines lines) {
        this.lines = lines;
        this.dijkstraShortestPath = new DijkstraShortestPath(getGraph());
    }

    private WeightedMultigraph getGraph() {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        addStation(graph);
        addSection(graph);

        return graph;
    }

    private void addStation(WeightedMultigraph<Long, DefaultWeightedEdge> graph) {
        lines.getLines().forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station.getId())));
    }

    private void addSection(WeightedMultigraph<Long, DefaultWeightedEdge> graph) {
        lines.getLines().forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation().getId(),
                        section.getDownStation().getId()), section.getDistance())));
    }

    public int getTotalDistance(Long source, Long target) {
        return (int) dijkstraShortestPath.getPathWeight(source, target);
    }

    public List<Long> getShortestPath(Long source, Long target) {
        return dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    private List<Section> getShortestPathSections(Long source, Long target, List<Station> stations) {
        List<Long> shortestPath = getShortestPath(source, target);
        List<Section> sections = new ArrayList<>();
        Map<Long, Station> mapStations = stations.stream()
                .collect(Collectors.toMap(Station::getId, Function.identity()));
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            sections.add(new Section(mapStations.get(shortestPath.get(i)), mapStations.get(shortestPath.get(i + 1))));
        }
        return sections;
    }

    public int getMaxExtraFare(Long source, Long target, List<Station> stations) {
        List<Section> sections = getShortestPathSections(source, target, stations);
        return lines.getLines().stream()
                .filter(line -> sections.stream()
                        .anyMatch(section -> line.getSections().isExist(section)))
                .max(Comparator.comparingInt(Line::getExtraFare))
                .get()
                .getExtraFare();
    }

    public int calculateDistanceFare(Long source, Long target) {
        int totalDistance = getTotalDistance(source, target);
        int distanceFare = BASE_FARE;

        if (BASIC_FARE_DISTANCE < totalDistance && totalDistance <= EXTRA_FARE_DISTANCE) {
            distanceFare += Math.ceil((totalDistance - BASIC_FARE_DISTANCE) / MIDDLE_DISTANCE_DENOMINATOR) * HUNDRED;
        }
        if (totalDistance > EXTRA_FARE_DISTANCE) {
            distanceFare += OVER_EXTRA_DISTANCE_FARE + Math.ceil((totalDistance - EXTRA_FARE_DISTANCE) / HIGH_DISTANCE_DENOMINATOR) * HUNDRED;
        }
        return distanceFare;
    }

    public int getAgeFare(LoginMember loginMember, int totalFare) {
        if (loginMember.isChild()) {
            return new ChildFareStrategy().getTotalFare(loginMember, totalFare);
        }
        if (loginMember.isTeenager()) {
            return new TeenagerFareStrategy().getTotalFare(loginMember, totalFare);
        }
        return new DefaultFareStrategy().getTotalFare(loginMember, totalFare);
    }
}
