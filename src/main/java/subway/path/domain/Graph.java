package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private WeightedMultigraph<Station, Section> graph = new WeightedMultigraph(Section.class);
    private DijkstraShortestPath dijkstraShortestPath;

    public Graph(List<Station> stations, List<Section> sections) {
        for (Station station : stations) {
            graph.addVertex(station);
        }
        for (Section section : sections) {
            graph.addEdge(section.getUpStation(), section.getDownStation(), section);
        }
        dijkstraShortestPath = new DijkstraShortestPath(graph);
    }

    public List<StationResponse> getPathStations(Station sourceStation, Station targetStation) {
        try {
            List<Station> stations = dijkstraShortestPath.getPath(sourceStation, targetStation).getVertexList();
            return stations.stream()
                    .map(StationResponse::of)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("두 역 사이에 경로가 존재하지 않습니다.");
        }
    }

    public int getPathDistance(Station sourceStation, Station targetStation){
        return (int) dijkstraShortestPath.getPath(sourceStation, targetStation).getWeight();
    }
}
