package subway.path.dto;

import java.util.List;

public class PathDto {
    private List<Long> shortestPath;
    private int distance;

    public PathDto(List<Long> shortestPath, int distance) {
        this.shortestPath = shortestPath;
        this.distance = distance;
    }

    public List<Long> getShortestPath() {
        return shortestPath;
    }

    public int getDistance() {
        return distance;
    }
}
