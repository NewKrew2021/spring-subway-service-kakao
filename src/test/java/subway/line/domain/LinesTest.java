package subway.line.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.exception.InvalidStationIdException;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class LinesTest {
    Station 강남역 = new Station(1L, "강남역");
    Station 망포역 = new Station(2L, "망포역");
    Station 역삼역 = new Station(3L, "역삼역");
    Station 수원역 = new Station(4L, "수원역");

    Section 강남_망포 = new Section(1L, 강남역, 망포역, 5);
    Section 망포_역삼 = new Section(2L, 망포역, 역삼역, 5);
    Section 역삼_수원 = new Section(3L, 역삼역, 수원역, 5);
    Section 망포_강남 = new Section(4L, 망포역, 강남역, 5);
    Section 역삼_망포 = new Section(5L, 역삼역, 망포역, 5);

    Sections sections = new Sections(Arrays.asList(강남_망포, 망포_역삼, 역삼_수원));
    Sections sections1 = new Sections(Arrays.asList(망포_강남, 역삼_망포));

    Line 수인선 = new Line(1L, "수인선", "yellow", sections);
    Line 신분당선 = new Line(2L, "신분당선", "red", sections1);

    @DisplayName("모든 라인 내 모든 섹션을 반환한다.")
    @Test
    void getSectionsInAllLineTest() {
        Lines lines = new Lines(Arrays.asList(수인선, 신분당선));
        assertThat(lines.getSectionsInAllLine()
                .getSections()
                .stream()
                .map(SectionWithFare::getSection)
                .collect(Collectors.toList())).contains(강남_망포, 망포_역삼, 역삼_수원, 망포_강남, 역삼_망포);
    }

    @DisplayName("섹션들에 역이 포함되어 있는지 테스트")
    @Test
    void findStationTest() {
        SectionsInAllLine sections = new SectionsInAllLine(Stream.of(강남_망포, 망포_역삼).map(section -> new SectionWithFare(0, section)).collect(Collectors.toList()));

        assertThat(sections.findStation(강남역.getId())).isEqualTo(강남역);
        assertThat(sections.findStation(망포역.getId())).isEqualTo(망포역);
        assertThat(sections.findStation(역삼역.getId())).isEqualTo(역삼역);

        assertThatThrownBy(() -> sections.findStation(수원역.getId())).isInstanceOf(InvalidStationIdException.class);
    }
}
