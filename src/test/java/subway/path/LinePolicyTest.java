package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.path.domain.farePolicy.LinePolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LinePolicyTest {

    private static Line line1 = new Line(1L,"a","a",300, new Sections());
    private static Line line2 = new Line(2L,"b","b",500, new Sections());
    private static Line line3 = new Line(3L,"c","c",700, new Sections());
    private static Line line4 = new Line(4L,"d","d",900, new Sections());
    private static Line line5 = new Line(5L,"e","e",1000, new Sections());


    @DisplayName("라인의 목록과 현재 요금이 정해졌을 때 라인에 따른 추가 요금이 더해진 금액을 확인한다.")
    @Test
    void getFareTest() {
        List<Line> lines1 = new ArrayList<>(Arrays.asList(line1,line2,line3));
        List<Line> lines2 = new ArrayList<>(Arrays.asList(line1,line2,line4));
        List<Line> lines3 = new ArrayList<>(Arrays.asList(line3,line4,line5));
        assertThat(new LinePolicy(1250,lines1).getFare()).isEqualTo(1950);
        assertThat(new LinePolicy(1250,lines2).getFare()).isEqualTo(2150);
        assertThat(new LinePolicy(1250,lines3).getFare()).isEqualTo(2250);
    }


}
