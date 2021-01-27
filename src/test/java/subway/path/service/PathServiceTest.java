package subway.path.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import subway.line.dao.LineDao;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PathServiceTest {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private LineDao lineDao;

    private PathService pathService;

    @BeforeEach
    public void setUp() {
        pathService = new PathService(stationDao, lineDao);
    }

    @Test
    @DisplayName("PathResponse 거리 계산 테스트")
    public void findPathResponseDistance() {
        // when
        PathResponse expected = pathService.findPathResponse(3L, 5L, 30);

        // then
        assertThat(expected.getDistance()).isEqualTo(40);
    }

    @Test
    @DisplayName("PathResponse 요금 계산 테스트")
    public void findPathResponseFare() {
        // when
        PathResponse expected = pathService.findPathResponse(3L, 5L, 30);

        // then
        assertThat(expected.getFare()).isEqualTo(1850);
    }
}