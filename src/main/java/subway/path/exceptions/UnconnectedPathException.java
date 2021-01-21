package subway.path.exceptions;

import subway.station.domain.Station;

public class UnconnectedPathException extends RuntimeException {

  private static final String MESSAGE_FORMAT = "%s에서 %s으로 가는 경로를 찾을 수 없습니다.";

  public UnconnectedPathException(Station source, Station target) {
    super(String.format(MESSAGE_FORMAT, source.getName(), target.getName()));
  }
}
