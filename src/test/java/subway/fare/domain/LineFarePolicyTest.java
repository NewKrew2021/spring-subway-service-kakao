package subway.fare.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.fare.domain.LineFarePolicy.findLineExtraFare;

class LineFarePolicyTest {

    @ParameterizedTest
    @DisplayName("Line의 추가요금들이 주어졌을 때 적용되는 추가요금을 구한다.")
    @MethodSource("generateArgumentsStream")
    void findLineExtraFareTest(List<Integer> extraFares, int expected) {
        assertThat(findLineExtraFare(extraFares)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateArgumentsStream() {
        List<Arguments> listOfArguments = new LinkedList<>();
        listOfArguments.add(Arguments.of(Arrays.asList(100, 200), 200));
        listOfArguments.add(Arguments.of(Arrays.asList(0, 200, 500), 500));
        return listOfArguments.stream();
    }
}