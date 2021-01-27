package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.util.FareCalculator;
import subway.station.domain.Station;

import java.util.List;

public class KakaoMap {
    List<Line> lines;
    ComplimentaryAge complimentaryAge;
    MetroMap metroMap;
    Station source;
    Station target;

    public KakaoMap(List<Line> lines, Station source, Station target) {
        this(lines, source, target, ComplimentaryAge.ADULT);
    }

    public KakaoMap(List<Line> lines, Station source, Station target, ComplimentaryAge complimentaryAge) {
        this.lines = lines;
        this.source = source;
        this.target = target;
        this.complimentaryAge = complimentaryAge;
        this.metroMap = new MetroMap(lines);
    }

    public List<Station> getShortestPath() {
        return metroMap.getShortestPath(source, target);
    }

    public int getTotalFare() {
        return FareCalculator.getFinalFare(complimentaryAge,
                FareCalculator.getLineExtraFare(getShortestPathSections(), lines)
                        + FareCalculator.getDistanceFare(getTotalDistance()));
    }

    public List<Section> getShortestPathSections() {
        return metroMap.getShortestPathSections(source, target);
    }

    public int getTotalDistance() {
        return metroMap.getTotalDistance(source, target);
    }
}
