package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.caculator.FareCalculator;
import subway.station.domain.Station;

import java.util.List;

public class MetroNavigator {
    private List<Line> lines;
    private ComplimentaryAge complimentaryAge;
    private MetroShortcutExplorer metroShortcutExplorer;
    private Station startStation;
    private Station destStation;

    public MetroNavigator(List<Line> lines, Station startStation, Station destStation) {
        this(lines, startStation, destStation, ComplimentaryAge.ADULT);
    }

    public MetroNavigator(List<Line> lines, Station startStation, Station destStation, ComplimentaryAge complimentaryAge) {
        this.lines = lines;
        this.startStation = startStation;
        this.destStation = destStation;
        this.complimentaryAge = complimentaryAge;
        this.metroShortcutExplorer = new MetroShortcutExplorer(lines);
    }

    public List<Station> getShortestPath() {
        return metroShortcutExplorer.getShortestPath(startStation, destStation);
    }

    public int getTotalFare() {
        return FareCalculator.getFinalFare(complimentaryAge,
                FareCalculator.getLineExtraFare(getShortestPathSections(), lines)
                        + FareCalculator.getDistanceFare(getTotalDistance()));
    }

    public List<Section> getShortestPathSections() {
        return metroShortcutExplorer.getShortestPathSections(startStation, destStation);
    }

    public int getTotalDistance() {
        return metroShortcutExplorer.getTotalDistance(startStation, destStation);
    }
}
