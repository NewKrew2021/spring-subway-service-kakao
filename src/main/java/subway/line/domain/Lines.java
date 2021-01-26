package subway.line.domain;

import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Lines {

    private final List<Line> lines;

    public Lines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Station> getUniqueStations() {
        return lines.stream()
                .flatMap(line -> line.getSections().getStations().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Section> getAllSections() {
        return lines.stream()
                .flatMap(line -> line.getSections().getSections().stream())
                .collect(Collectors.toList());
    }

    public int getTotalExtraFares(){
        return lines.stream()
                .mapToInt(Line::getFare)
                .sum();
    }

}
