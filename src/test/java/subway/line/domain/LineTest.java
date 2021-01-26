package subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
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
    public void addSection1() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);

        이호선.addSection(newSection);

        assertThat(이호선.getSections().getSections().get(1)).isEqualTo(newSection);
    }

    @Test
    public void addSection2() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);

        이호선.addSection(서초역, 방배역, 10);

        assertThat(이호선.getSections().getSections().get(1)).isEqualTo(newSection);
    }

    @Test
    public void removeSection1() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);

        이호선.addSection(newSection);
        이호선.removeSection(방배역);

        assertThat(이호선.getSections().getSections().size()).isEqualTo(1);
        assertThat(이호선.getSections().getSections().get(0)).isEqualTo(section);
    }

    @Test
    public void removeSection2() {
        Section newSection = new Section(2L, 1L, 서초역, 방배역, 10);
        이호선.addSection(newSection);
        이호선.removeSection(서초역);

        assertThat(이호선.getSections().getSections().size()).isEqualTo(1);
        assertThat(이호선.getSections().getSections().get(0)).isEqualTo(
                new Section(2L, 1L, 강남역, 방배역, 20)
        );
    }
}
