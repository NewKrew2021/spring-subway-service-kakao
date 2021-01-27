package subway.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AGETest {

    @Test
    void testGetAge() {
        assertThat(AGE.getAge(1)).isEqualTo(AGE.BABY);
        assertThat(AGE.getAge(5)).isEqualTo(AGE.BABY);
        assertThat(AGE.getAge(6)).isEqualTo(AGE.CHILD);
        assertThat(AGE.getAge(12)).isEqualTo(AGE.CHILD);
        assertThat(AGE.getAge(13)).isEqualTo(AGE.TEENAGER);
        assertThat(AGE.getAge(18)).isEqualTo(AGE.TEENAGER);
        assertThat(AGE.getAge(19)).isEqualTo(AGE.ADULT);
    }
}