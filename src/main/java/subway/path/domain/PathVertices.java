package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
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

    public List<Line> getPassingLines(List<Line> lines) {
        List<Line> passingLine = new ArrayList<>();
        Station prevStation = null;
        Station nextStation = null;
        for(PathVertex pathVertex : pathVertices){
            prevStation = nextStation;
            nextStation = pathVertex.getStation();
            if(prevStation == null) continue;

            Station finalPrevStation = prevStation;
            Station finalNextStation = nextStation;
            lines.forEach(line -> {
                        if(line.getSections().findSectionByStations(finalPrevStation, finalNextStation) != null){
                            passingLine.add(line);
                        }
                    });
        }

        return passingLine;
    }
}
