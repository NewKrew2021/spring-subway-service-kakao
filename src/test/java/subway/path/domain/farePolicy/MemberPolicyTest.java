package subway.path.domain.farePolicy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.member.domain.LoginMember;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberPolicyTest {

    private static LoginMember child1 = new LoginMember(1L,"a",6);
    private static LoginMember child2 = new LoginMember(1L,"a",12);
    private static LoginMember teenager1 = new LoginMember(1L,"a",13);
    private static LoginMember teenager2 = new LoginMember(1L,"a",18);
    private static LoginMember adult1 = new LoginMember(1L,"a",20);
    private static BasicFare fare;

    @DisplayName("기본 지하철 요금 1250원 설정")
    @BeforeEach
    void create() {
        fare = new SubwayFare();
    }

    @DisplayName("어린이가 탑승할 경우 현재 금액의 과세액 350을 뺀 금액의 50퍼센트가 할인된 금액이 반환된다.")
    @Test
    void getFareTest1() {
        assertThat(new MemberPolicy(fare,child1).getFare()).isEqualTo(800);
        assertThat(new MemberPolicy(fare,child2).getFare()).isEqualTo(800);
    }

    @DisplayName("청소년이 탑승할 경우 현재 금액의 과세액 350을 뺀 금액의 20퍼센트가 할인된 금액이 반환된다.")
    @Test
    void getFareTest2() {
        assertThat(new MemberPolicy(fare,teenager1).getFare()).isEqualTo(1070);
        assertThat(new MemberPolicy(fare,teenager2).getFare()).isEqualTo(1070);
    }

    @DisplayName("어른이라 로그인하지 않은 멤버 탑승할 경우 현재 금액이 그대로 반환된")
    @Test
    void getFareTest3() {
        assertThat(new MemberPolicy(fare,adult1).getFare()).isEqualTo(1250);
        assertThat(new MemberPolicy(fare,LoginMember.NOT_LOGIN_MEMBER).getFare()).isEqualTo(1250);
    }


}
