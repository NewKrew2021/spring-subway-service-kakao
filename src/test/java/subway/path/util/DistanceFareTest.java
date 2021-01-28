package subway.path.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DistanceFareTest {

    @ParameterizedTest
    @CsvSource({"10,0", "11,100", "15,100", "16,200", "50,800", "51,900", "58,900", "59,1000"})
    void getFare(int distance, int expected) {
        // when
        int result = DistanceFare.getFare(distance);

        // then
        assertThat(result).isEqualTo(expected);
    }

}