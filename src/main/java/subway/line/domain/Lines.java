package subway.line.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Lines {

  List<Line> lines;

  public Lines(List<Line> lines) {
    this.lines = lines;
  }

  public SectionsInAllLine getSectionsInAllLine() {
    return new SectionsInAllLine(lines.stream()
        .flatMap(line -> line.getSections().getSections().stream())
        .collect(Collectors.toList()));
  }

}
