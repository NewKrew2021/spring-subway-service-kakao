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
    private Station source;
    private Station target;

    public MetroNavigator(List<Line> lines, Station source, Station target) {
        this(lines, source, target, ComplimentaryAge.ADULT);
    }

    public MetroNavigator(List<Line> lines, Station source, Station target, ComplimentaryAge complimentaryAge) {
        this.lines = lines;
        this.source = source;
        this.target = target;
        this.complimentaryAge = complimentaryAge;
        this.metroShortcutExplorer = new MetroShortcutExplorer(lines);
    }

    public List<Station> getShortestPath() {
        return metroShortcutExplorer.getShortestPath(source, target);
    }

    public int getTotalFare() {
        return FareCalculator.getFinalFare(complimentaryAge,
                FareCalculator.getLineExtraFare(getShortestPathSections(), lines)
                        + FareCalculator.getDistanceFare(getTotalDistance()));
    }

    public List<Section> getShortestPathSections() {
        return metroShortcutExplorer.getShortestPathSections(source, target);
    }

    public int getTotalDistance() {
        return metroShortcutExplorer.getTotalDistance(source, target);
    }
}
