package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Fare 클래스")
public class FareTest {

    @DisplayName("거리에 따른 요금 계산")
    @ParameterizedTest
    @CsvSource(delimiter = ':', value = {
            "5:1250",
            "10:1250",
            "14:1350",
            "37:1850",
            "50:2050",
            "53:2150",
            "78:2450"
    })
    public void calculate_distance_Test(int distance, int expectedFare) {
        //given
        List<Integer> extraFares = Arrays.asList(0, 0);

        //when
        int resultFare = Fare.calculate(distance, extraFares);

        //then
        assertThat(resultFare).isEqualTo(expectedFare);
    }

    @DisplayName("extraFare들 중에서 가장 큰 값을 더한다.")
    @Test
    public void calculate_extraFare_test() {
        //given
        List<Integer> extraFares = Arrays.asList(8, 2, 100, 45, 211);
        int distance = 10;
        int expectedFare = 1250 + 211;

        //when
        int resultFare = Fare.calculate(distance, extraFares);

        //then
        assertThat(resultFare).isEqualTo(expectedFare);
    }

    @DisplayName("나이에 따라 할인적용")
    @ParameterizedTest
    @CsvSource(delimiter = ':', value = {
            "5:0",
            "6:800",
            "11:800",
            "13:1070",
            "18:1070",
            "19:1250",
            "20:1250",
    })
    public void discountByAgeTest(int givenAge, int expectedFare) {
        //given
        int fare = 1250;

        //when
        int result = Fare.discountByAge(fare, givenAge);

        //then
        assertThat(result).isEqualTo(expectedFare);
    }
}
