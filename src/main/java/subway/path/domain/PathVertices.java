package subway.path.domain;

import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.*;

public class PathVertices {
    private List<PathVertex> pathVertices;

    private PathVertices(List<PathVertex> pathVertexList){
        this.pathVertices = pathVertexList;
    }

    public static PathVertices of(List<PathVertex> pathVertexList){
        return new PathVertices(pathVertexList);
    }

    public static PathVertices from(List<Line> lines){
        List<PathVertex> pathVertices = new ArrayList<>();
        Map<Station, List<Long>> tmp = new HashMap<>();
        lines.forEach(line -> {
            List<Station> stations = line.getStations();
            stations.forEach(station -> {
                if (tmp.containsKey(station)) {
                    tmp.get(station).add(line.getId());
                    return;
                }
                tmp.put(station, new ArrayList<>(Arrays.asList(line.getId())));
            });
        });
        tmp.forEach((key, value) ->
                pathVertices.add(new PathVertex(key, value))
        );

        return new PathVertices(pathVertices);
    }


    public List<PathVertex> getPathVertexList() {
        return pathVertices;
    }

    public PathVertex getPathVertexByStationId(Long stationId){
        return pathVertices.stream()
                .filter(vertex -> vertex.getStation().getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }
}
