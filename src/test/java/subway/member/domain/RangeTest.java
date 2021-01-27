package subway.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RangeTest {
    Range range = Range.of(3,5);

    @DisplayName("해당값이 Range에 속하는지 경계값을 테스트한다.")
    @Test
    void isBelong() {
        assertThat(range.isBelong(3)).isTrue();
        assertThat(range.isBelong(4)).isTrue();
        assertThat(range.isBelong(5)).isFalse();
    }
}