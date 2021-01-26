package subway.path.domain;

import java.util.Objects;

public class Vertex {
    private final Long stationId;
    private final int extraFare;
    public static final int DEFAULT_EXTRA_FARE = 0;

    private Vertex(Long stationId, int extraFare) {
        this.stationId = stationId;
        this.extraFare = extraFare;
    }


    public static Vertex of(Long stationId, int extraFare) {
        return new Vertex(stationId, extraFare);
    }

    public static Vertex of(Long stationId) {
        return new Vertex(stationId, DEFAULT_EXTRA_FARE);
    }

    public Long getStationId() {
        return stationId;
    }

    public int getExtraFare() {
        return extraFare;
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
