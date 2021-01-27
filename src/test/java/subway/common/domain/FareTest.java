package subway.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.common.exception.NegativeNumberException;
import subway.common.exception.NotExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("요금 관련 기능")
class FareTest {
    @DisplayName("더 많은 요금 값을 가진 객체를 반환한다.")
    @Test
    void max() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);

        // then
        assertThat(Fare.max(f1, f2)).isEqualTo(f2);
    }

    @DisplayName("max의 파라미터가 하나라도 null이면 예외를 던진다.")
    @Test
    void max2() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);

        // then
        assertThatThrownBy(() -> Fare.max(f1, null))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 요금은 비교할 수 없습니다.");
        assertThatThrownBy(() -> Fare.max(null, f2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 요금은 비교할 수 없습니다.");
    }

    @DisplayName("두 요금을 더하면 더한 값을 요금으로 갖는다.")
    @Test
    void add() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);
        Fare f3 = Fare.from(10);

        // then
        assertThat(Fare.add(f1, f2)).isEqualTo(f3);
    }

    @DisplayName("add의 파라미터가 하나라도 null이면 예외를 던진다.")
    @Test
    void add2() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);

        // then
        assertThatThrownBy(() -> Fare.add(f1, null))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 요금은 연산할 수 없습니다.");
        assertThatThrownBy(() -> Fare.add(null, f2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 요금은 연산할 수 없습니다.");
    }

    @DisplayName("두 요금을 뺀 값이 음수가 아니면 뺀 값을 요금으로 갖는다.")
    @Test
    void sub() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);
        Fare f3 = Fare.from(4);

        // then
        assertThat(f2.sub(f1)).isEqualTo(f3);
        assertThat(f2.sub(f3)).isEqualTo(f1);
    }

    @DisplayName("두 요금을 뺀 값이 음수이면 예외를 던진다.")
    @Test
    void sub2() {
        // given
        Fare f1 = Fare.from(3);
        Fare f2 = Fare.from(7);

        // then
        assertThatThrownBy(() -> f1.sub(f2))
                .isInstanceOf(NegativeNumberException.class)
                .hasMessage("요금은 음수가 될 수 없습니다.");
    }

    @DisplayName("sub의 파라미터가 null이면 예외를 던진다.")
    @Test
    void sub3() {
        // given
        // given
        Fare fare = Fare.from(3);

        // then
        assertThatThrownBy(() -> fare.sub(null))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 요금은 연산할 수 없습니다.");
    }

    @DisplayName("요금에 정수를 곱하면 곱한 값을 요금으로 갖는다.")
    @Test
    void multiply() {
        // given
        Fare fare = Fare.from(3);
        int number = 7;

        // then
        assertThat(fare.multiply(number)).isEqualTo(Fare.from(21));
    }

    @DisplayName("요금에 소수를 곱하면 곱한 값에 정수를 취해 요금으로 갖는다.")
    @Test
    void multiply2() {
        // given
        Fare fare = Fare.from(1250);
        double number1 = 0.2;
        double number2 = 0.5;

        // then
        assertThat(fare.multiply(number1)).isEqualTo(Fare.from(250));
        assertThat(fare.multiply(number2)).isEqualTo(Fare.from(625));
    }
}
