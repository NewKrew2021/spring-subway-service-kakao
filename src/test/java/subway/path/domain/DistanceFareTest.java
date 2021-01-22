package subway.path.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import subway.path.domain.fare.DistanceFare;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DistanceFareTest {

    @ParameterizedTest
    @CsvSource({"10,1250", "11,1350", "15,1350", "16,1450", "50,2050", "51,2150", "58,2150", "59,2250"})
    void getFare(int distance, int expected) {
        // when
        int result = DistanceFare.getFare(distance);

        // then
        assertThat(result).isEqualTo(expected);
    }

}
