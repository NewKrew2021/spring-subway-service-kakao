package subway.line.domain;

import subway.line.exception.LineNotFoundException;
import subway.station.domain.Station;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubwayMap {
    private List<Line> lines;

    public SubwayMap(List<Line> lines) {
        this.lines = lines;
    }

    public List<Section> findAllSections() {
        return this.lines.stream()
                .map(Line::getSections)
                .map(Sections::getSections)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public int getExtraFare(Station stationA, Station stationB) {
        Optional<Line> lineOptional = lines.stream()
                .filter((line) -> line.getSections().hasStations(stationA,stationB)
                ).findFirst();

        if (!lineOptional.isPresent()) throw new LineNotFoundException();
        return lineOptional.get().getExtraFare();
    }

    public DirectedSections getShortestPath(Station source, Station destination) {
        return new DirectedSections(this, source, destination);
    }

    public void refresh(List<Line> lines) {
        this.lines = lines;
    }
}
