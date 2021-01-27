package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.AcceptanceTest;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathBuilderTest extends AcceptanceTest {
    StationDao stationDao = mock(StationDao.class);
    LineDao lineDao = mock(LineDao.class);
    private PathBuilder pathBuilder;

    @BeforeEach
    public void before() {
        Station 강남역 = new Station(1L, "강남역");
        Station 판교역 = new Station(2L, "판교역");
        Station 정자역 = new Station(3L, "정자역");
        Station 역삼역 = new Station(4L, "역삼역");
        Station 잠실역 = new Station(5L, "잠실역");

        List<Station> stations = Arrays.asList(강남역, 판교역, 정자역, 역삼역, 잠실역);
        when(stationDao.findAll()).thenReturn(stations);

        when(lineDao.findAll()).thenReturn(
                Arrays.asList(
                        new Line(1L, "신분당선", "Red", 0, new Sections(
                                Arrays.asList(
                                        new Section(강남역, 판교역, 10),
                                        new Section(판교역, 정자역, 10)
                                )
                        )),
                        new Line(2L, "2호선", "GREEN", 0, new Sections(
                                Arrays.asList(
                                        new Section(강남역, 역삼역, 10),
                                        new Section(역삼역, 잠실역, 10)
                                )
                        ))
                )
        );

        pathBuilder = new PathBuilder()
                .initializePath(stationDao.findAll(), lineDao.findAll())
                .makeGraphPath(3L, 5L);
    }

    @Test
    @DisplayName("요금 계산 테스트")
    public void findFare() {
        // when
        pathBuilder.calculateFare();
        int expected = pathBuilder.fare;

        // then
        assertThat(expected).isEqualTo(1850);
    }

    @Test
    @DisplayName("요금 할인 테스트 1 : 어린이")
    public void discountFare() {
        // when
        pathBuilder.calculateFare()
                .discountByAge(7);
        int expected = pathBuilder.fare;

        // then
        assertThat(expected).isEqualTo(1100);
    }

    @Test
    @DisplayName("요금 할인 테스트 2 : 청소년")
    public void discountFare2() {
        // when
        pathBuilder.calculateFare()
                .discountByAge(15);
        int expected = pathBuilder.fare;

        // then
        assertThat(expected).isEqualTo(1550);
    }

}