package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.member.domain.Age;
import subway.member.domain.LoginMember;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("연령별 할인 테스트")
class LoginMemberFareStrategyTest {
    private LoginMember loginMember;


    @DisplayName("영유아의 경우 무임으로 승차 가능하다.")
    @Test
    void When_loginMemberIsInfants_getFare() {
        //given
        loginMember = new LoginMember(0L,"email@email.com",new Age(5));
        FareStrategy fareStrategy = new LoginMemberFareStrategy(10,0,loginMember);
        int expected = 0;

        assertThat(fareStrategy.getFare()).isEqualTo(expected);

    }

    @DisplayName("어린이의 경우 공제금액을 제외하고 50%할인이 들어간다.")
    @Test
    void When_loginMemberIsChild_getFare() {
        //given
        loginMember = new LoginMember(0L,"email@email.com",new Age(12));
        FareStrategy fareStrategy = new LoginMemberFareStrategy(10,0,loginMember);
        int expected = (int) (GeneralFareStrategy.BASIC_FARE - (GeneralFareStrategy.BASIC_FARE - 350) * 0.5);

        assertThat(fareStrategy.getFare()).isEqualTo(expected);
    }

    @DisplayName("청소년의 경우 공제금액을 제외하고 20%할인이 들어간다.")
    @Test
    void When_loginMemberIsTeenager_getFare() {
        //given
        loginMember = new LoginMember(0L,"email@email.com",new Age(18));
        FareStrategy fareStrategy = new LoginMemberFareStrategy(10,0,loginMember);
        int expected = (int) (GeneralFareStrategy.BASIC_FARE - (GeneralFareStrategy.BASIC_FARE - 350) * 0.2);

        assertThat(fareStrategy.getFare()).isEqualTo(expected);
    }

    @DisplayName("성인의 경우 연령할인 해당사항이 없다.")
    @Test
    void When_loginMemberIsAdult_getFare() {
        //given
        loginMember = new LoginMember(0L,"email@email.com",new Age(19));
        FareStrategy fareStrategy = new LoginMemberFareStrategy(10,0,loginMember);
        int expected = GeneralFareStrategy.BASIC_FARE;

        assertThat(fareStrategy.getFare()).isEqualTo(expected);
    }

}