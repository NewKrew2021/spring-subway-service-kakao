package subway.path.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FareServiceTest {

    @Autowired
    private FareService fareService;

    @DisplayName("거리에 따른 운임을 계산한다.")
    @ParameterizedTest
    @CsvSource({"10,1250", "11,1350", "15,1350", "50,2050", "51,2150", "58,2150"})
    void getFareByDistance(int distance, int fare) {
        assertThat(fareService.getDistanceFare(distance)).isEqualTo(fare);
    }

    @DisplayName("나이에 따른 할인 운임을 계산한다.")
    @ParameterizedTest
    @CsvSource({"5,0", "6,2850", "12,2850", "13,4350", "18,4350", "19,5350"})
    void discount(int age, int discountedFare) {
        int fare = 5350;
        assertThat(fareService.discount(fare, age)).isEqualTo(discountedFare);
    }
}
