package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirectedSections {
    private final List<DirectedSection> sections;

    public DirectedSections(List<DirectedSection> sections) {
        this.sections = sections;
    }

    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Arrays.asList();
        }

        List<Station> stations = sections.stream()
                .map(DirectedSection::getTargetStation)
                .collect(Collectors.toList());
        stations.add(0, sections.get(0).getSourceStation());

        return stations;
    }

    public int getDistance() {
        return sections.stream()
                .map(Section::getDistance)
                .reduce(0, Integer::sum);
    }

    public int getPrice() {
        return SubwayPrice.getPrice(getDistance(), getMaxExtraFare());
    }

    private int getMaxExtraFare() {
        return sections.stream()
                .map(DirectedSection::getLine)
                .map(Line::getExtraFare)
                .max(Integer::compare)
                .orElse(0);
    }
}
