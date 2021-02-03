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

    public List<Integer> getExtraFareList(List<Line> lines) {
        List<Line> passingLine = new ArrayList<>();
        Station prevStation = null;
        Station nextStation = null;
        for(PathVertex pathVertex : pathVertices){
            prevStation = nextStation;
            nextStation = pathVertex.getStation();
            if(prevStation == null) continue;

            passingLine.add(getLineBySection(lines, prevStation, nextStation));
        }
        return passingLine.stream()
                .filter(Objects::nonNull)
                .map(Line::getExtraFare)
                .collect(Collectors.toList());
    }

    private Line getLineBySection(List<Line> lines, final Station prev, final Station next){
        return lines.stream()
                .filter(line -> line.hasSectionByStations(prev, next))
                .findFirst()
                .orElse(null);
    }
}
