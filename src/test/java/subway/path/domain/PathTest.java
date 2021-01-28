package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.AcceptanceTest;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest extends AcceptanceTest {
    private Station 강남역 = new Station("강남역");
    private Station 양재역 = new Station("양재역");
    private Station 교대역 = new Station("교대역");
    private Path path = new Path(Arrays.asList(강남역, 양재역, 교대역), 100, 5350);

    private static Stream<Arguments> provideLoginMember() {
        return Stream.of(
                Arguments.of(new LoginMember(1L, "test@test.com", 5), 5350),
                Arguments.of(new LoginMember(1L, "test@test.com", 6), 2850),
                Arguments.of(new LoginMember(1L, "test@test.com", 12), 2850),
                Arguments.of(new LoginMember(1L, "test@test.com", 13), 4350),
                Arguments.of(new LoginMember(1L, "test@test.com", 18), 4350),
                Arguments.of(new LoginMember(1L, "test@test.com", 19), 5350)
        );
    }

    @DisplayName("나이에 따른 할인 운임을 계산한다.")
    @ParameterizedTest
    @MethodSource("provideLoginMember")
    void discountedPath(LoginMember loginMember, int fare) {
        Path newPath = path.discountedPath(loginMember);
        assertThat(newPath.getFare()).isEqualTo(fare);
    }
}
