package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.domain.DirectedSection;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectedSectionTest {
    Line 이호선 = new Line(1L, "2호선", "그린");
    Station 강남역 = new Station(1L, "강남역");
    Station 역삼역 = new Station(2L, "역삼역");
    Section 강남_역삼_구간 = new Section(1L, 강남역, 역삼역, 10);

    @DisplayName("출발지, 도착지 테스트")
    @Test
    void testDirection() {
        이호선.addSection(강남_역삼_구간);
        Station 출발역 = 강남역;

        DirectedSection 역방향구간 = new DirectedSection(이호선, 강남_역삼_구간, 출발역);
        assertThat(역방향구간.getSourceStation()).isEqualTo(강남역);
        assertThat(역방향구간.getTargetStation()).isEqualTo(역삼역);
    }
}
