package subway.line.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SectionsTest {

    private Station 강남역;
    private Station 서초역;
    private Station 교대역;
    private Station 양재역;

    private Sections sections;

    @BeforeEach
    public void setUp() {
        강남역 = new Station(1L, "강남역");
        서초역 = new Station(2L, "서초역");
        교대역 = new Station(3L, "교대역");
        양재역 = new Station(4L, "양재역");

        Section lineTwoSection = new Section(1L, 1L, 강남역, 서초역, 10);
        Section lineThreeSection = new Section(2L, 2L, 교대역, 양재역, 15);

        List<Section> sectionList = Arrays.asList(
                new Section(1L, 1L, 강남역, 서초역, 10),
                new Section(2L, 1L, 서초역, 교대역, 20),
                new Section(2L, 2L, 교대역, 양재역, 15)
        );

        sections = new Sections(sectionList);
    }

    @Test
    @DisplayName("getStations 를 호출하면 sections 내 저장된 역들이 모두 반환된다")
    void getStationsTest() {
        assertThat(sections.getStations()).containsExactly(강남역, 서초역, 교대역, 양재역);
    }

    @Test
    @DisplayName("Sections 에 저장된 Section 들의 Line Id 를 반환한다.")
    void getDistinctLineIdsTest() {
        assertThat(sections.getDistinctLineIds()).containsExactly(1L, 2L);
    }

}