package subway.line.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import subway.line.exception.InvalidStationIdException;
import subway.station.domain.Station;

public class SectionsInAllLine {
  private final List<SectionWithFare> sections;
  private final Map<Long, Station> stationMap;

  public SectionsInAllLine(List<SectionWithFare> sections) {
    this.sections = sections;
    this.stationMap = createStationMap();
  }

  public List<SectionWithFare> getSections() {
    return sections;
  }

  private Map<Long, Station> createStationMap() {
    return sections.stream()
        .flatMap(section -> Stream.of(section.getUpStation(), section.getDownStation()))
        .distinct()
        .collect(Collectors.toMap(Station::getId, Function.identity()));
  }

  public Station findStation(Long stationId) {
    return Optional.ofNullable(stationMap.get(stationId))
            .orElseThrow(() -> new InvalidStationIdException(stationId));
  }

}
