package subway.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.exception.WrongInputDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("멤버 유닛 테스트")
public class MemberTest {

    @Test
    void 멤버생성_테스트(){
        assertThat(new Member("adult@email.com","1234",5)).isEqualTo(new Member("adult@email.com","1234",5));
    }

    @Test
    void 멤버생성_실패_테스트(){
        assertThatExceptionOfType(WrongInputDataException.class).isThrownBy(()->{
            new Member("adult.com","1234",5);
        });
    }
}
