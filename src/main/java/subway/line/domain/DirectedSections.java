package subway.line.domain;

import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectedSections extends Section{
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int DEFAULT_PRICE = 1250;
    private static final int ADDITIONAL_PRICE = 100;
    private final List<DirectedSection> sections;
    private final int maxExtraFare;

    public DirectedSections(List<DirectedSection> sections, int maxExtraFare) {
        this.sections = sections;
        this.maxExtraFare = maxExtraFare;
    }

    public int getMaxExtraFare() {
        return maxExtraFare;
    }

    public int getPrice() {
        int distance = getDistance();
        if (distance <= MIN_DISTANCE) {
            return DEFAULT_PRICE + maxExtraFare;
        }
        if (distance <= MAX_DISTANCE) {
            return DEFAULT_PRICE + ((distance - MIN_DISTANCE) / 5 + 1) * ADDITIONAL_PRICE + maxExtraFare;
        }
        return DEFAULT_PRICE + ((distance - MIN_DISTANCE) / 8 + 1) * ADDITIONAL_PRICE + maxExtraFare;
    }

    public int getDistance() {
        return sections.stream()
                .map(Section::getDistance)
                .reduce(0, Integer::sum);
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
