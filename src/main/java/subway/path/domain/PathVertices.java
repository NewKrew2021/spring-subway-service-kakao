package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.*;

public class PathVertices {
    private List<PathVertex> pathVertices;

    public PathVertices(){

    }

    public PathVertices(List<PathVertex> pathVertexList){
        this.pathVertices = pathVertexList;
    }

    public void initAllVertex(List<Line> lines){
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
        this.pathVertices = new ArrayList<>();
        tmp.forEach((key, value) ->
            this.pathVertices.add(new PathVertex(key, value))
        );
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
