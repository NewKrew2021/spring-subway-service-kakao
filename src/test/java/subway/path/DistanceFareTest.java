package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import subway.path.domain.DistanceFare;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceFareTest {

    @DisplayName("기본 운임 (10km 이내)")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 9, 10})
    void defaultDistance(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);

        assertThat(distanceFare.calculateExtraFareByDistance(distance)).isEqualTo(1250);
    }

    @DisplayName("1번의 추가 운임 (10km 초과 50km 이내)")
    @ParameterizedTest
    @ValueSource(ints = {11, 12, 15})
    void overTenKm_OneTime(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);

        assertThat(distanceFare.calculateExtraFareByDistance(distance)).isEqualTo(1350);
    }

    @DisplayName("8번의 추가 운임 (10km 초과 50km 이내)")
    @ParameterizedTest
    @ValueSource(ints = {46, 49, 50})
    void overTenKm_EightTime(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);

        assertThat(distanceFare.calculateExtraFareByDistance(distance)).isEqualTo(2050);
    }

    @DisplayName("1번의 추가 운임 (50km 초과)")
    @ParameterizedTest
    @ValueSource(ints = {51, 52, 57, 58})
    void overFifty_OneTime(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);

        assertThat(distanceFare.calculateExtraFareByDistance(distance)).isEqualTo(2150);
    }

    @DisplayName("2번의 추가 운임 (50km 초과)")
    @ParameterizedTest
    @ValueSource(ints = {59, 65, 66})
    void overFifty_TwoTime(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);

        assertThat(distanceFare.calculateExtraFareByDistance(distance)).isEqualTo(2250);
    }
}
