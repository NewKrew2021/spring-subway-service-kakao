package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.member.domain.LoginMember;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
    @DisplayName("로그인된 유저의 정보를 받아서 연령대를 반환한다")
    @Test
    void testOf() {
        LoginMember newborn = new LoginMember(1L, "newborn@email.com", 5);
        LoginMember youngChild = new LoginMember(2L, "youngchild@email.com", 6);
        LoginMember child = new LoginMember(3L, "child@email.com", 12);
        LoginMember youngTeen = new LoginMember(4L, "youngteen@email.com", 13);
        LoginMember teen = new LoginMember(5L, "teen@email.com", 18);
        LoginMember adult = new LoginMember(6L, "adult@email.com", 19);
        LoginMember noLogin = null;

        assertThat(Person.of(newborn)).isEqualTo(Person.NEWBORN);
        assertThat(Person.of(youngChild)).isEqualTo(Person.CHILD);
        assertThat(Person.of(child)).isEqualTo(Person.CHILD);
        assertThat(Person.of(youngTeen)).isEqualTo(Person.TEEN);
        assertThat(Person.of(teen)).isEqualTo(Person.TEEN);
        assertThat(Person.of(adult)).isEqualTo(Person.ADULT);
        assertThat(Person.of(noLogin)).isEqualTo(Person.ADULT);
    }
}
