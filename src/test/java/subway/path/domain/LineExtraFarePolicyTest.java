package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.path.dto.Fare;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class LineExtraFarePolicyTest {

    @DisplayName("포함된 노선 중, 가장 비싼 추가 요금 조회 테스트")
    @Test
    void extraFareTest(){
        int fare = new LineExtraFarePolicy().apply(Arrays.asList(200, 1000));
        assertThat(fare).isEqualTo(1000);
    }
}
