package subway.path.domain.fare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FareCalculatorImplTest {

    private FareCalculator fareCalculator;

    private Line 추가요금_900;
    private Line 추가요금_500;
    private Line 추가요금_없음;

    private LoginMember 어른;
    private LoginMember 청소년;
    private LoginMember 어린이;
    private LoginMember 비로그인;


    @BeforeEach
    void setUp() {
        fareCalculator = new FareCalculatorImpl(new FarePolicyFactory());

        추가요금_900 = new Line("신분당선", "bg-red-600", 900);
        추가요금_500 = new Line("이호선", "bg-red-600", 500);
        추가요금_없음 = new Line("삼호선", "bg-red-600", 0);

        어른 = new LoginMember(1L, "test1@test.com", 30);
        청소년 = new LoginMember(2L, "test2@test.com", 18);
        어린이 = new LoginMember(3L, "test3@test.com", 12);
        비로그인 = LoginMember.NOT_LOGINED;
    }

    @DisplayName("기본 거리 + 추가요금 X + 어른")
    @Test
    void total1() {
        // when
        int result = fareCalculator.getFare(5, Arrays.asList(추가요금_없음), 어른);

        // then
        assertThat(result).isEqualTo(1250);
    }

    @DisplayName("10km 초과 + 추가요금 500 + 어린이")
    @Test
    void total2() {
        // when
        // 거리: 2050, 추가요금 500, 어린이 할인률 50%
        int result = fareCalculator.getFare(49, Arrays.asList(추가요금_없음, 추가요금_500), 어린이);

        // then
        assertThat(result).isEqualTo(1450);
    }

    @DisplayName("50km 초과 + 추가요금 900 + 청소년")
    @Test
    void total3() {
        // when
        // 거리: 2150, 추가요금 900, 청소년 할인률 20%
        int result = fareCalculator.getFare(51, Arrays.asList(추가요금_500, 추가요금_900), 청소년);

        // then
        assertThat(result).isEqualTo(2510);
    }

    @DisplayName("50km 초과 + 추가요금 900 + 비로그인")
    @Test
    void total4() {
        // when
        // 거리: 2150, 추가요금 900, 비로그인 할인 x
        int result = fareCalculator.getFare(51, Arrays.asList(추가요금_500, 추가요금_900), 비로그인);

        // then
        assertThat(result).isEqualTo(3050);
    }
}
