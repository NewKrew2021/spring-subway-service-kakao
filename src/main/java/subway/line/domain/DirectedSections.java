package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import subway.member.domain.LoginMember;
import subway.member.domain.LoginMemberType;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectedSections extends Section {
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int DEFAULT_PRICE = 1250;
    private static final int ADDITIONAL_PRICE = 100;
    private final List<DirectedSection> sections;
    private final ExtraFare maxExtraFare;
    private final Station source;
    private final Station destination;
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> graph;

    public DirectedSections(List<DirectedSection> sections, Station source, Station destination,DijkstraShortestPath<Station, DefaultWeightedEdge> graph,ExtraFare maxExtraFare) {
        this.sections = sections;
        this.source = source;
        this.destination = destination;
        this.maxExtraFare = maxExtraFare;
        this.graph = graph;
    }

    public int getMaxExtraFare() {
        return maxExtraFare.getValue();
    }

    public int getPrice() {
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
                (MAX_DISTANCE - MIN_DISTANCE) / 5 * ADDITIONAL_PRICE +
                over50Bonus(distance) * ADDITIONAL_PRICE +
                maxExtraFare.getValue();
    }

    public int under50Bonus(int distance) {
        return (distance - MIN_DISTANCE) / 5 + ((distance - MIN_DISTANCE) % 5 == 0 ? 0 : 1);
    }

    public int over50Bonus(int distance) {
        return (distance - MAX_DISTANCE) / 8 + ((distance - MIN_DISTANCE) % 8 == 0 ? 0 : 1);
    }

    public int getResultPrice(LoginMember loginMember) {
        int defaultPrice = getPrice();
        if (loginMember.getType() == LoginMemberType.KID) {
            return 0;
        }
        if (loginMember.getType() == LoginMemberType.CHILD) {
            return defaultPrice - (defaultPrice - 350) / 2;
        }
        if (loginMember.getType() == LoginMemberType.ADOLESCENT) {
            return defaultPrice - (defaultPrice - 350) / 5;
        }
        return defaultPrice;
    }

    public int getDistance() {
        return (int) graph.getPathWeight(source,destination);
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
