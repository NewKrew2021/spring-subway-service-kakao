package subway.path.domain;

import java.util.List;

public class Edges {
    private final List<Edge> edges;

    public Edges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getExtraFare() {
        return edges.stream()
                .map(v -> v.getExtraFare())
                .max(Integer::compare).orElse(Edge.DEFAULT_EXTRA_FARE);
    }
}
