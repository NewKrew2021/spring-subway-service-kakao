package subway.path.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Vertices {
    public static final int DEFAULT_EXTRA_FARE = 0;
    private final List<Vertex> vertices;

    private Vertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public static Vertices of(List<Vertex> vertices) {
        return new Vertices(vertices);
    }

    public List<Long> getStationIds() {
        return vertices.stream()
                .map(Vertex::getStationId)
                .collect(Collectors.toList());
    }

    public int getMaxLineExtraFare() {
        return vertices.stream()
                .map(v -> v.getLineExtraFare())
                .max(Integer::compare).orElse(DEFAULT_EXTRA_FARE);
    }
}
