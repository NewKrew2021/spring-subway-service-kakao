package subway.path.domain.fare;

import subway.path.domain.Vertices;

public class FareParam {
    private final Vertices vertices;
    private final int distance;

    public FareParam(Vertices vertices, int distance) {
        this.vertices = vertices;
        this.distance = distance;
    }

    public static FareParam of(Vertices vertices, int distance) {
        return new FareParam(vertices, distance);
    }

    public Vertices getVertices() {
        return vertices;
    }

    public int getDistance() {
        return distance;
    }
}
