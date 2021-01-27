package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.line.domain.Section;
import subway.line.domain.SectionWithFare;
import subway.line.domain.SectionsInAllLine;
import subway.path.exceptions.InvalidPathException;
import subway.station.domain.Station;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class PathTest {
    static Station 강남역 = new Station(1L, "강남역");
    static Station 망포역 = new Station(2L, "망포역");
    static Station 역삼역 = new Station(3L, "역삼역");
    static Station 수원역 = new Station(4L, "수원역");
    static Station 판교역 = new Station(5L, "판교역");
    static Station 제주역 = new Station(6L, "제주역");
    static Station 서귀포역 = new Station(7L, "서귀포역");

    Section 강남_망포 = new Section(1L, 강남역, 망포역, 5);
    Section 망포_역삼 = new Section(2L, 망포역, 역삼역, 5);
    Section 판교_망포 = new Section(3L, 판교역, 망포역, 5);
    Section 망포_수원 = new Section(4L, 망포역, 수원역, 5);
    Section 제주_서귀포 = new Section(5L, 제주역, 서귀포역, 5);

    SectionsInAllLine sections;
    PathGraph pathGraph;

    /**          판교역
     *             |
     *  강남역 --- 망포역 --- 역삼역
     *             |
     *           수원역
     *
     *   제주역 ----------- 서귀포역
     */
    // 강남 - 망포 - 역삼
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

    @DisplayName("연결되지 않은 역들의 경로를 조회한다.")
    @ParameterizedTest
    @MethodSource("provideStation")
    void failToFindShortestPathTest(Station source, Station destination) {
        assertThatThrownBy(() -> pathGraph.getPath(source, destination)).isInstanceOf(InvalidPathException.class);
    }

    private static Stream<Arguments> provideStation() {
        return Stream.of(
                Arguments.of(강남역, 서귀포역),
                Arguments.of(수원역, 제주역)
        );
    }
}
