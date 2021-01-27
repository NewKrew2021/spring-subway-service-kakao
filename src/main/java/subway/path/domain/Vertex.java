package subway.path.domain;

import java.util.Objects;

public class Vertex {
    private final Long stationId;

    private Vertex(Long stationId) {
        this.stationId = stationId;
    }

    public static Vertex of(Long stationId) {
        return new Vertex(stationId);
    }

    public Long getStationId() {
        return stationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(stationId, vertex.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId);
    }
}
