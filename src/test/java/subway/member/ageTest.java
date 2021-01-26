package subway.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.member.domain.AGE;

import static org.assertj.core.api.Assertions.assertThat;

public class ageTest {

    @DisplayName("회원 나이를 파악한다..")
    @Test
    void 나이확인() {
        assertThat(AGE.getAge(5)).isEqualTo(AGE.BABY);
        assertThat(AGE.getAge(6)).isEqualTo(AGE.CHILD);
        assertThat(AGE.getAge(12)).isEqualTo(AGE.CHILD);
        assertThat(AGE.getAge(13)).isEqualTo(AGE.TEENAGER);
        assertThat(AGE.getAge(18)).isEqualTo(AGE.TEENAGER);
        assertThat(AGE.getAge(19)).isEqualTo(AGE.ADULT);
    }
}
