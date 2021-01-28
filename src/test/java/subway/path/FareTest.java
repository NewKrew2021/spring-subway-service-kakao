package subway.path;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.member.domain.Age;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;
import subway.path.domain.fare.Fare;
import subway.path.domain.fare.FareParam;
import subway.path.domain.fare.strategy.DistanceFare;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FareTest {
    @ParameterizedTest
    @MethodSource("fareParameter")
    void getTotalFare(int age, int distance, int lineExtraFare, int totalFare) {
        LoginMember loginMember = LoginMember.of(Member.of("email@email.com", "password", age));
        FareParam fareParam = FareParam.of(lineExtraFare, distance);
        Fare fare = Fare.of(loginMember, fareParam);

        assertThat(fare.getFare()).isEqualTo(totalFare);
    }

    static Stream<Arguments> fareParameter() {
        return Stream.of(
                Arguments.arguments(5, 100, 200, Age.getAge(5).discount(new DistanceFare(100).getExtraFare() + 200)),
                Arguments.arguments(6, 10, 200, Age.getAge(6).discount(new DistanceFare(10).getExtraFare() + 200)),
                Arguments.arguments(13, 11, 200, Age.getAge(13).discount(new DistanceFare(11).getExtraFare() + 200)),
                Arguments.arguments(19, 58, 200, Age.getAge(19).discount(new DistanceFare(58).getExtraFare() + 200)),

                Arguments.arguments(5, 100, 1000, Age.getAge(5).discount(new DistanceFare(100).getExtraFare() + 1000)),
                Arguments.arguments(6, 10, 1000, Age.getAge(6).discount(new DistanceFare(10).getExtraFare() + 1000)),
                Arguments.arguments(13, 11, 1000, Age.getAge(13).discount(new DistanceFare(11).getExtraFare() + 1000)),
                Arguments.arguments(19, 58, 1000, Age.getAge(19).discount(new DistanceFare(58).getExtraFare() + 1000))
        );
    }

}
