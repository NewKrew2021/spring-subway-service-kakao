package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.member.domain.LoginMember;
import subway.member.domain.LoginMemberType;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectedSections {
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int DEFAULT_PRICE = 1250;
    private static final int ADDITIONAL_PRICE = 100;
    private static final int PER_NEAR_DISTANCE = 5;
    private static final int PER_FAR_DISTANCE = 8;
    private static final int FARE_FREE_PRICE = 350;
    private static final int RATE_HALF = 2;
    private static final int RATE_FIFTH = 5;
    private final List<DirectedSection> sections;
    private final ExtraFare maxExtraFare;
    private final Station source;
    private final Station destination;
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> graph;

    public DirectedSections(SubwayMap subwayMap, Station source, Station destination) {
        this.source = source;
        this.destination = destination;
        List<Section> sections = subwayMap.findAllSections();
        graph = createGraph(sections);
        List<Station> orderedStations = graph.getPath(source, destination).getVertexList();
        this.sections = new ArrayList<>();
        for (int i = 0; i < orderedStations.size() - 1; i++) {
            Station sourceStation = orderedStations.get(i);
            Station targetStation = orderedStations.get(i + 1);
            DirectedSection directedSection = new DirectedSection(findSection(sections, sourceStation, targetStation), sourceStation);
            this.sections.add(directedSection);
        }
        this.maxExtraFare = ExtraFare.of(subwayMap, this.sections);
    }

    private DijkstraShortestPath<Station, DefaultWeightedEdge> createGraph(List<Section> sections) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph =
                new WeightedMultigraph<>(DefaultWeightedEdge.class);

        for (Section section : sections) {
            graph.addVertex(section.getUpStation());
            graph.addVertex(section.getDownStation());
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
        }

        return new DijkstraShortestPath<>(graph);
    }

    private Section findSection(List<Section> sections, Station stationA, Station stationB) {
        return sections.stream()
                .filter(section -> section.contains(stationA) && section.contains(stationB))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private int getPrice() {
        int distance = getDistance();
        if (distance <= MIN_DISTANCE) {
            return DEFAULT_PRICE + maxExtraFare.getValue();
        }
        if (distance <= MAX_DISTANCE) {
            return DEFAULT_PRICE +
                    under50Bonus(distance) * ADDITIONAL_PRICE +
                    maxExtraFare.getValue();
        }
        return DEFAULT_PRICE +
                (MAX_DISTANCE - MIN_DISTANCE) / PER_NEAR_DISTANCE * ADDITIONAL_PRICE +
                over50Bonus(distance) * ADDITIONAL_PRICE +
                maxExtraFare.getValue();
    }

    private int under50Bonus(int distance) {
        return (distance - MIN_DISTANCE) / PER_NEAR_DISTANCE +
                ((distance - MIN_DISTANCE) % PER_NEAR_DISTANCE == 0 ? 0 : 1);
    }

    private int over50Bonus(int distance) {
        return (distance - MAX_DISTANCE) / PER_FAR_DISTANCE +
                ((distance - MIN_DISTANCE) % PER_FAR_DISTANCE == 0 ? 0 : 1);
    }

    public int getResultPrice(LoginMember loginMember) {
        int defaultPrice = getPrice();
        if (loginMember.getType() == LoginMemberType.KID) {
            return 0;
        }
        if (loginMember.getType() == LoginMemberType.CHILD) {
            return defaultPrice - (defaultPrice - FARE_FREE_PRICE) / RATE_HALF;
        }
        if (loginMember.getType() == LoginMemberType.ADOLESCENT) {
            return defaultPrice - (defaultPrice - FARE_FREE_PRICE) / RATE_FIFTH;
        }
        return defaultPrice;
    }

    public int getDistance() {
        return (int) graph.getPathWeight(source, destination);
    }

    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Collections.emptyList();
        }

        List<Station> stations = new ArrayList<>();
        stations.add(sections.get(0).getSourceStation());

        for (DirectedSection section : sections) {
            stations.add(section.getTargetStation());
        }

        return stations;
    }

}
