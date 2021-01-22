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
        Set<Station> uniqueStation = new HashSet<>();
        for (Line line : lines) {
            uniqueStation.addAll(line.getSections().getStations());
        }
        return new ArrayList<>(uniqueStation);
    }

    public List<Section> getAllSections() {
        List<Section> sections = new ArrayList<>();
        for (Line line : lines) {
            sections.addAll(line.getSections().getSections());
        }
        return sections;
    }

    public int getTotalExtraFares(){
        return lines.stream().mapToInt(Line::getFare).sum();
    }

}
