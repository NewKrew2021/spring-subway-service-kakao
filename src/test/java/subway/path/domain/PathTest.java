package subway.path.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Section;
import subway.line.domain.SectionWithFare;
import subway.line.domain.SectionsInAllLine;
import subway.path.exceptions.UnconnectedPathException;
import subway.station.domain.Station;

public class PathTest {

  Station 강남역 = new Station(1L, "강남역");
  Station 망포역 = new Station(2L, "망포역");
  Station 역삼역 = new Station(3L, "역삼역");
  Station 수원역 = new Station(4L, "수원역");
  Station 판교역 = new Station(5L, "판교역");
  Station 제주역 = new Station(6L, "제주역");
  Station 서귀포역 = new Station(7L, "서귀포역");

  Section 강남_망포 = new Section(1L, 강남역, 망포역, 5);
  Section 망포_역삼 = new Section(2L, 망포역, 역삼역, 5);
  Section 판교_망포 = new Section(3L, 판교역, 망포역, 5);
  Section 망포_수원 = new Section(4L, 망포역, 수원역, 5);
  Section 제주_서귀포 = new Section(5L, 제주역, 서귀포역, 5);

  SectionsInAllLine sections;
  PathGraph pathGraph;

  // 강남 - 망포 - 역삼
  // 판교 - 망포 - 수원
  @BeforeEach
  void setUp() {
    sections = new SectionsInAllLine(Stream.of(강남_망포, 망포_역삼, 판교_망포, 망포_수원, 제주_서귀포)
        .map(section -> new SectionWithFare(0, section))
        .collect(Collectors.toList()));
    pathGraph = new PathGraph(sections);
  }

  @DisplayName("최단경로를 반환한다.")
  @Test
  void findShortestPathTest() {
    Path path = pathGraph.getPath(강남역, 수원역);
    assertThat(path.getStations()).containsExactly(강남역, 망포역, 수원역);
  }

  @Test
  void failToFindShortestPathTest() {
    assertThatThrownBy(() -> pathGraph.getPath(강남역, 서귀포역))
        .isInstanceOf(UnconnectedPathException.class);
    assertThatThrownBy(() -> pathGraph.getPath(수원역, 제주역))
        .isInstanceOf(UnconnectedPathException.class);
  }
}
