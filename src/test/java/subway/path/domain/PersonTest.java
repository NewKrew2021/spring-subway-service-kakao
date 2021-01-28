package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
    @DisplayName("로그인된 유저의 정보를 받아서 연령대를 반환한다")
    @Test
    void testOf() {
        long newborn = 5;
        long youngChild = 6;
        long child = 12;
        long youngTeen = 13;
        long teen = 18;
        long adult = 19;

        assertThat(Person.of(newborn)).isEqualTo(Person.NEWBORN);
        assertThat(Person.of(youngChild)).isEqualTo(Person.CHILD);
        assertThat(Person.of(child)).isEqualTo(Person.CHILD);
        assertThat(Person.of(youngTeen)).isEqualTo(Person.TEEN);
        assertThat(Person.of(teen)).isEqualTo(Person.TEEN);
        assertThat(Person.of(adult)).isEqualTo(Person.ADULT);
    }
}
