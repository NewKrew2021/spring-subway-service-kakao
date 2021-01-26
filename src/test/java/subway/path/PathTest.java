package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.domain.Path;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

    private Path path;

    @BeforeEach
    void setUp() {
        Station station1 = new Station(1L, "A");
        Station station2 = new Station(2L, "B");
        List<Station> stations = new ArrayList<>(Arrays.asList(station1, station2));

        path = new Path(stations, 45, 1350);
    }

    @DisplayName("거리별 운임 적용")
    @Test
    void applyDistancePolicy() {
        path.applyDistanceFarePolicy();

        assertThat(path.getFare()).isEqualTo(1950 + 1350);
    }

    @DisplayName("청소년 운임 적용")
    @Test
    void applyAgePolicy_Teen() {
        path.applyAgeFarePolicy(16);

        assertThat(path.getFare()).isEqualTo(1150);
    }

    @DisplayName("어린이 운임 적용")
    @Test
    void applyAgePolicy_Child() {
        path.applyAgeFarePolicy(8);

        assertThat(path.getFare()).isEqualTo(850);
    }

    @DisplayName("무임 적용")
    @Test
    void applyAgePolicy_Free() {
        path.applyAgeFarePolicy(4);

        assertThat(path.getFare()).isEqualTo(0);
    }
}
