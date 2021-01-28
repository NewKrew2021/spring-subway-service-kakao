package subway.path.domain;

import java.util.Objects;

public class Vertex {
    public static final int DEFAULT_LINE_EXTRA_FARE = 0;
    Long stationId;
    int lineExtraFare;

    private Vertex(Long stationId, int extraFare) {
        this.stationId = stationId;
        this.lineExtraFare = extraFare;
    }

    public static Vertex of(Long stationId) {
        return new Vertex(stationId, DEFAULT_LINE_EXTRA_FARE);
    }

    public static Vertex of(Long stationId, int lineExtraFare) {
        return new Vertex(stationId, lineExtraFare);
    }

    public Long getStationId() {
        return stationId;
    }

    public int getLineExtraFare() {
        return lineExtraFare;
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
