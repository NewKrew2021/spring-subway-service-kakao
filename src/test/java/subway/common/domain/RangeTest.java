package subway.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("범위 관련 기능")
class RangeTest {
    @DisplayName("하한선이 상한선보다 작으면 범위를 생성다.")
    @Test
     void validate() {
        // given
        int lowerBound = 1;
        int upperBound = 10;

        // then
        assertThat(Range.of(lowerBound, upperBound)).isInstanceOf(Range.class);
    }

    @DisplayName("하한선이 상한선과 같으면 범위 생성에 실패한다.")
    @Test
     void validate2() {
        // given
        int lowerBound = 10;
        int upperBound = 10;

        // then
        assertThatThrownBy(() -> Range.of(lowerBound, upperBound))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("하한선은 상한선보다 작아야합니다.");
    }

    @DisplayName("하한선이 상한선보다 크면 범위 생성에 실패한다.")
    @Test
     void validate3() {
        // given
        int lowerBound = 20;
        int upperBound = 10;

        // then
        assertThatThrownBy(() -> Range.of(lowerBound, upperBound))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("하한선은 상한선보다 작아야합니다.");
    }

    @DisplayName("하한선보다 크거나 같고 상한선보다 작은 값이 들어오면 해당 범위에 속한다.")
    @Test
     void belong() {
        // given
        int lowerBound = 1;
        int upperBound = 10;
        Range range = Range.of(lowerBound, upperBound);

        // then
        assertThat(range.isBelong(lowerBound)).isTrue();
        assertThat(range.isBelong(lowerBound + 1)).isTrue();
        assertThat(range.isBelong(upperBound - 1)).isTrue();
    }

    @DisplayName("하한선보다 작은 값이 들어오면 해당 범위에 속하지 않는다.")
    @Test
     void belong2() {
        // given
        int lowerBound = 1;
        int upperBound = 10;
        Range range = Range.of(lowerBound, upperBound);

        // then
        assertThat(range.isBelong(lowerBound - 1)).isFalse();
    }

    @DisplayName("상한선보다 크거나 같은 값이 들어오면 해당 범위에 속하지 않는다.")
    @Test
     void belong3() {
        // given
        int lowerBound = 1;
        int upperBound = 10;
        Range range = Range.of(lowerBound, upperBound);

        // then
        assertThat(range.isBelong(upperBound)).isFalse();
        assertThat(range.isBelong(upperBound + 1)).isFalse();
    }
}
