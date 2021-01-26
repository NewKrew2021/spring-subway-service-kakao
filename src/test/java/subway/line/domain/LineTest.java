package subway.line.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class LineTest {

    @DisplayName("노선의 첫차시간, 열차 간격이 주어지면 특정 DateTime 이후 가장 가까운 DateTime을 구한다")
    @ParameterizedTest
    @CsvSource({"6,0,6,0", "6,10,6,15", "6,55,7,0"})
    void nextDepartureTime(int hour, int minute, int expectedHour, int expectedMinute) {
        // given
        Line line = new Line("1호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 15);
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 26, hour, minute);

        // when
        LocalDateTime result = line.getNextDepartureTimeOf(dateTime);

        // then
        assertThat(result).isEqualTo(LocalDateTime.of(2021, 1, 26, expectedHour, expectedMinute));
    }

    @DisplayName("특정 DateTime의 시간이 막차 시간 이후라면 예외가 발생한다")
    @Test
    void nextDepartureTimeFail() {
        // given
        Line line = new Line("1호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 15);
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 26, 22, 5);

        // then
        assertThatIllegalArgumentException()
                // when
                .isThrownBy(() -> line.getNextDepartureTimeOf(dateTime))
                .withMessage("이 시간 이후로 출발하는 열차가 존재하지 않습니다");
    }
}
