package subway.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import subway.exception.custom.NoSuchMemberException;
import subway.member.application.MemberService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@DisplayName("멤버 관련 서비스")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("없는 멤버를 찾는다.")
    public void findMemberTest() {
        assertThatThrownBy(() -> memberService.findMember(-1L)).isInstanceOf(NoSuchMemberException.class);
    }
}
