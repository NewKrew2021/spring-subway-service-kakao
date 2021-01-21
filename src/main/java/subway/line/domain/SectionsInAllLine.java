package subway.line.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import subway.line.exception.InvalidStationIdException;
import subway.station.domain.Station;

public class SectionsInAllLine {

  private List<Section> sections;
  private Map<Long, Station> stationMap;

  public SectionsInAllLine(List<Section> sections) {
    this.sections = sections;
    this.stationMap = createStationMap();
  }

  public List<Section> getSections() {
    return sections;
  }

  private Map<Long, Station> createStationMap() {
    return sections.stream()
        .flatMap(section -> section.getStations().stream())
        .distinct()
        .collect(Collectors.toMap(Station::getId, Station::getSelf));
  }

  public Station findStation(Long stationId) {
    return Optional.ofNullable(stationMap.get(stationId))
            .orElseThrow(() -> new InvalidStationIdException(stationId));
  }

}
