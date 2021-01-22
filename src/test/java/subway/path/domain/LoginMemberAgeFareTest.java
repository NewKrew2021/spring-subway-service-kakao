package subway.path.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.LoginMemberAgeFare;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginMemberAgeFareTest {


    @ParameterizedTest
    @MethodSource("generateLoginMember")
    void getFare(LoginMember loginMember, int fare, int expected){
        //when
        int result = LoginMemberAgeFare.getFare(loginMember, fare);

        //then
        assertThat(result).isEqualTo(expected);

    }

    private static Stream<Arguments> generateLoginMember() {
        return Stream.of(
                Arguments.of(
                        new LoginMember(1L, "test", 5),
                        1350,
                        0
                ),
                Arguments.of(
                        new LoginMember(1L, "test", 6),
                        1350,
                        850
                ),
                Arguments.of(
                        new LoginMember(1L, "test", 12),
                        1350,
                        850
                ),
                Arguments.of(
                        new LoginMember(1L, "test", 13),
                        1350,
                        1150
                ),
                Arguments.of(
                        new LoginMember(1L, "test", 18),
                        1350,
                        1150
                ),
                Arguments.of(
                        new LoginMember(1L, "test", 19),
                        1350,
                        1350
                )
        );
    }
}
