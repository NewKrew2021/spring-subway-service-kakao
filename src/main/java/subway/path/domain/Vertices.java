package subway.path.domain;

import java.util.Collections;
import java.util.List;

public class Vertices {
    private final List<Vertex> vertices;

    public Vertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public int getExtraFare() {
        return vertices.stream()
                .map(v -> v.getExtraFare())
                .max(Integer::compare).orElse(Vertex.DEFAULT_EXTRA_FARE);
    }
}
