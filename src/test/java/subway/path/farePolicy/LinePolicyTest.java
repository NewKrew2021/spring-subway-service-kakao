package subway.path.farePolicy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.path.domain.WeightWithLine;
import subway.path.domain.farePolicy.BasicFare;
import subway.path.domain.farePolicy.LinePolicy;
import subway.path.domain.farePolicy.SubwayFare;

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

    private static BasicFare fare;

    @DisplayName("기본 지하철 요금 1250원 설정")
    @BeforeEach
    void create() {
        fare = new SubwayFare();
    }


    @DisplayName("라인의 목록과 현재 요금이 정해졌을 때 라인에 따른 추가 요금이 더해진 금액을 확인한다.")
    @Test
    void getFareTest() {

        List<Line> lines1 = new ArrayList<>(Arrays.asList(line1,line2,line3));
        List<Line> lines2 = new ArrayList<>(Arrays.asList(line1,line2,line4));
        List<Line> lines3  = new ArrayList<>(Arrays.asList(line3,line4,line5));
        assertThat(new LinePolicy(fare,lines1).getFare()).isEqualTo(1950);
        assertThat(new LinePolicy(fare,lines2).getFare()).isEqualTo(2150);
        assertThat(new LinePolicy(fare,lines3).getFare()).isEqualTo(2250);
    }


}
