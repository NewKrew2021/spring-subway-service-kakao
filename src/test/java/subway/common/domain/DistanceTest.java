package subway.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.common.exception.NegativeNumberException;
import subway.common.exception.NotExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("거리 관련 기능")
class DistanceTest {
    @DisplayName("거리가 0보다 크거나 같으면 거리를 생성한다.")
    @Test
     void validate() {
        // given
        int distance1 = 0;
        int distance2 = 1;

        // then
        assertThat(Distance.from(distance1)).isInstanceOf(Distance.class);
        assertThat(Distance.from(distance2)).isInstanceOf(Distance.class);
    }

    @DisplayName("거리가 0보다 작으면 거리 생성에 실패한다.")
    @Test
     void validate2() {
        // given
        int distance = -1;

        // then
        assertThatThrownBy(() -> Distance.from(distance))
                .isInstanceOf(NegativeNumberException.class)
                .hasMessage("거리는 음수가 될 수 없습니다.");
    }

    @DisplayName("더 작은 거리를 갖는 객체를 반환한다.")
    @Test
     void min() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);

        // then
        assertThat(Distance.min(distance1, distance2)).isEqualTo(distance1);
        assertThat(Distance.min(distance2, distance1)).isEqualTo(distance1);
    }

    @DisplayName("min의 파라미터가 둘 중 하나라도 null이면 에러를 던진다.")
    @Test
     void min2() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);

        // then
        assertThatThrownBy(() -> Distance.min(distance1, null))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 거리는 비교할 수 없습니다.");
        assertThatThrownBy(() -> Distance.min(null, distance2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 거리는 비교할 수 없습니다.");

    }

    @DisplayName("거리가 더 짧으면 참을 반환한다.")
    @Test
     void shorter() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);

        // then
        assertThat(distance1.isShorter(distance2)).isTrue();
    }

    @DisplayName("isShorter의 파라미터가 null이면 예외를 던진다.")
    @Test
     void shorter2() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = null;

        // then
        assertThatThrownBy(() -> distance1.isShorter(distance2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 거리는 비교할 수 없습니다.");
    }

    @DisplayName("거리가 같으면 거짓을 반환한다.")
    @Test
     void shorter3() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(3);

        // then
        assertThat(distance1.isShorter(distance2)).isFalse();
    }

    @DisplayName("거리가 더 멀면 거짓을 반환한다.")
    @Test
     void shorter4() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);

        // then
        assertThat(distance2.isShorter(distance1)).isFalse();
    }

    @DisplayName("두 거리를 차이를 구하면 절대 값을 거리로 갖는다.")
    @Test
     void difference() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);
        Distance distance3 = Distance.from(10);

        // then
        assertThat(distance1.getDifference(distance3)).isEqualTo(distance2);
        assertThat(distance3.getDifference(distance1)).isEqualTo(distance2);
    }

    @DisplayName("getDifference의 파라미터가 null이면 예외를 던진다.")
    @Test
     void difference2() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = null;

        // then
        assertThatThrownBy(() -> distance1.getDifference(distance2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 거리는 연산할 수 없습니다.");
    }

    @DisplayName("두 거리를 더하면 더한 값을 거리로 갖는다.")
    @Test
     void sum() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = Distance.from(7);
        Distance distance3 = Distance.from(10);

        // then
        assertThat(distance1.getSum(distance2)).isEqualTo(distance3);
        assertThat(distance2.getSum(distance1)).isEqualTo(distance3);
    }

    @DisplayName("getSum의 파라미터가 null이면 예외를 던진다.")
    @Test
     void sum2() {
        // given
        Distance distance1 = Distance.from(3);
        Distance distance2 = null;

        // then
        assertThatThrownBy(() -> distance1.getSum(distance2))
                .isInstanceOf(NotExistException.class)
                .hasMessage("존재하지 않는 거리는 연산할 수 없습니다.");
    }
}
