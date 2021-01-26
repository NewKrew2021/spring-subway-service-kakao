package subway.path.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PathServiceTest {

    @Autowired
    private PathService pathService;

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

    @DisplayName("거리에 따른 운임을 계산한다.")
    @ParameterizedTest
    @CsvSource({"10,1250", "11,1350", "15,1350", "50,2050", "51,2150", "58,2150"})
    void getFareByDistance(int distance, int fare) {
        assertThat(pathService.getFareByDistance(distance)).isEqualTo(fare);
    }

    @DisplayName("나이에 따른 할인 운임을 계산한다.")
    @ParameterizedTest
    @MethodSource("provideLoginMember")
    void fareWithDiscount(LoginMember loginMember, int fare) {
        PathResponse pathResponse = new PathResponse(null, 0, 5350);
        assertThat(pathService.fareWithDiscount(pathResponse, loginMember)).isEqualTo(fare);
    }
}
