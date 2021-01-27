package subway.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgeTest {

    @DisplayName("Age 생성자에 대한 경계값테스트")
    @Test
    void create() {
        Age age = new Age(1);
        assertThatThrownBy(() -> new Age(0)).isInstanceOf(IllegalArgumentException.class);
    }
}