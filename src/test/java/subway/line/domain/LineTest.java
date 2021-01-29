package subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    private Station 강남역;
    private Station 서초역;
    private Station 방배역;

    private Section section;
    private Line 이호선;

    @BeforeEach
    public void setUp() {
        강남역 = new Station(1L, "강남역");
        서초역 = new Station(2L, "서초역");
        방배역 = new Station(3L, "방배역");

        section = new Section(1L, 1L, 강남역, 서초역, 10);

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(section);

        Sections sections = new Sections(sectionList);

        이호선 = new Line(1L, "2호선", "red", 500, sections);

    }

    @Test
    @DisplayName("새로운 구간을 추가한다")
    public void addSection1() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);

        이호선.addSection(newSection);

        assertThat(이호선.getSections().getSections()).contains(newSection);
    }

    @Test
    @DisplayName("한쪽 끝에 있는 역을 지운다")
    public void removeSection1() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);

        이호선.addSection(newSection);
        이호선.removeSection(방배역);

        assertThat(이호선.getSections().getSections()).containsExactly(section);
    }

    @Test
    @DisplayName("중간에 있는 역을 지운다")
    public void removeSection2() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);
        이호선.addSection(newSection);
        이호선.removeSection(서초역);

        assertThat(이호선.getSections().getSections()).containsExactly(
                new Section(2L, 1L, 강남역, 방배역, 20)
        );
    }
}
