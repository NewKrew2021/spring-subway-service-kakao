package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import subway.line.dao.LineDao;
import subway.station.dao.StationDao;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PathBuilderTest {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private LineDao lineDao;

    private PathBuilder pathBuilder;

    @BeforeEach
    public void setUp() {
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