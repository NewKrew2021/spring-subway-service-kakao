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

}
