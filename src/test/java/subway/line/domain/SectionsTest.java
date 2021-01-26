package subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SectionsTest {

    private Station 강남역;
    private Station 서초역;
    private Station 방배역;

    private Section section1;
    private Section section2;

    @BeforeEach
    public void setUp() {
        강남역 = new Station(1L, "강남역");
        서초역 = new Station(2L, "서초역");
        방배역 = new Station(3L, "방배역");

        section1 = new Section(1L, 1L, 강남역, 서초역, 10);
        section2 = new Section(2L, 1L, 서초역, 방배역, 10);
    }

    @Test
    public void createSections1() {
        Sections sections = new Sections();

        sections.addSection(section1);
        sections.addSection(section2);

        assertThat(sections.getSections().size()).isEqualTo(2);
        assertThat(sections.getSections().get(0)).isEqualTo(section1);
        assertThat(sections.getSections().get(1)).isEqualTo(section2);
    }

    @Test
    public void createSections2() {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(section1);
        sectionList.add(section2);

        Sections sections = new Sections(sectionList);

        assertThat(sections.getSections().size()).isEqualTo(2);
        assertThat(sections.getSections().get(0)).isEqualTo(section1);
        assertThat(sections.getSections().get(1)).isEqualTo(section2);
    }

    @Test
    public void addSection() {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(section1);

        Sections sections = new Sections(sectionList);
        sections.addSection(section2);

        assertThat(sections.getSections().get(1)).isEqualTo(section2);
    }

    @Test
    public void removeStation1() {
        Sections sections = new Sections();

        sections.addSection(section1);
        sections.addSection(section2);

        sections.removeStation(방배역);

        assertThat(sections.getSections().size()).isEqualTo(1);
        assertThat(sections.getSections().get(0)).isEqualTo(section1);
    }

    @Test
    public void removeStation2() {
        Sections sections = new Sections();

        sections.addSection(section1);
        sections.addSection(section2);

        sections.removeStation(서초역);

        assertThat(sections.getSections().size()).isEqualTo(1);
        assertThat(sections.getSections().get(0)).isEqualTo(
                new Section(2L, 1L, 강남역, 방배역, 20)
        );
    }

}
