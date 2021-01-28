package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Vertices;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VerticesTest {
    private Lines lines;

    @BeforeEach
    void setUp() {
        List<Section> sections = Arrays.asList(
                new Section(new Station(1L, "잠실새내"), new Station(2L, "잠실"), 1)
        );

        List<Section> sections2 = Arrays.asList(
                new Section(new Station(2L, "잠실"), new Station(3L, "복정"), 1)
        );

        lines = Lines.of(Arrays.asList(
                Line.of(1L, "2호선", "bg-red-600", 400, new Sections(sections)),
                Line.of(1L, "8호선", "bg-red-600", 1000, new Sections(sections2))
        ));
    }

    @Test
    void getStations() {
        Vertices vertices = Vertices.of(lines.getVertices(1L, 3L));

        assertThat(vertices.getStationIds()).isEqualTo(Arrays.asList(1L, 2L, 3L));
    }

    @Test
    void getMaxlineExtraFare() {
        Vertices vertices = Vertices.of(lines.getVertices(1L, 2L));
        assertThat(vertices.getMaxLineExtraFare()).isEqualTo(400);

        vertices = Vertices.of(lines.getVertices(1L, 3L));
        assertThat(vertices.getMaxLineExtraFare()).isEqualTo(1000);
    }
}
