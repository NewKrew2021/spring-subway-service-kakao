package subway.path.domain;

import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

public class PathVertices {
    private List<PathVertex> pathVertices;

    private PathVertices(List<PathVertex> pathVertexList){
        this.pathVertices = pathVertexList;
    }

    public static PathVertices of(List<PathVertex> pathVertexList){
        return new PathVertices(pathVertexList);
    }

    public static PathVertices from(List<Station> stations){
        return of(stations.stream().map(PathVertex::new).collect(Collectors.toList()));
    }

    public List<PathVertex> getPathVertexList() {
        return pathVertices;
    }

    public PathVertex getPathVertexByStation(Station station){
        return pathVertices.stream()
                .filter(vertex -> vertex.getStation().equals(station))
                .findFirst()
                .orElse(null);
    }
}
