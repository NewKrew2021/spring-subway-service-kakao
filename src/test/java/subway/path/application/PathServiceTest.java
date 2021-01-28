package subway.path.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.path.dto.PathDto;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PathServiceTest {

    @InjectMocks
    private PathService pathService;

    @Mock
    private SectionDao sectionDao;

    private List<Section> allSections;

    /**
     * 1L  ---- (10) ----   2L       5L ---- (5) ---- 6L
     * |                    |
     * (2)                (10)
     * |                    |
     * 3L  ---- (3) ----   4L
     */

    @BeforeEach
    void setUp() {
        allSections = List.of(
                new Section(1L, new Station(1L), new Station(2L), 10),
                new Section(2L, new Station(1L), new Station(3L), 2),
                new Section(3L, new Station(2L), new Station(4L), 10),
                new Section(4L, new Station(3L), new Station(4L), 3),
                new Section(5L, new Station(5L), new Station(6L), 5)
        );
    }

    @DisplayName("source에서 target까지 최단 경로와 이동거리를 구한다.")
    @ParameterizedTest
    @MethodSource("providePath")
    void searchShortestPath(Long source, Long target, PathDto expected) {
        // stub
        when(sectionDao.findAll()).thenReturn(allSections);

        // when
        PathDto actual = pathService.searchShortestPath(source, target);

        // then
        assertThat(actual.getShortestPath()).containsExactlyElementsOf(expected.getShortestPath());
        assertThat(actual.getDistance()).isEqualTo(expected.getDistance());
    }

    private static Stream<Arguments> providePath() {
        return Stream.of(
                Arguments.of(1L, 2L, new PathDto(List.of(1L, 2L), 10)),
                Arguments.of(1L, 4L, new PathDto(List.of(1L, 3L, 4L), 5)),
                Arguments.of(3L, 2L, new PathDto(List.of(3L, 1L, 2L), 12)),
                Arguments.of(5L, 6L, new PathDto(List.of(5L, 6L), 5))
        );
    }

    @DisplayName("source에서 target까지 경로가 존재하지 않으면 빈 경로와 이동거리 0을 반환한다.")
    @Test
    void searchShortestPath2() {
        // given
        Long source = 1L;
        Long target = 6L;

        // stub
        when(sectionDao.findAll()).thenReturn(allSections);

        // when
        PathDto actual = pathService.searchShortestPath(source, target);

        // then
        assertThat(actual.getShortestPath()).isEmpty();
        assertThat(actual.getDistance()).isEqualTo(0L);
    }
}
