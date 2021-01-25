package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exceptions.NotExistsDataException;
import subway.line.domain.Section;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    public static final String NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE = "두 역 사이에 경로가 존재하지 않습니다.";
    private WeightedMultigraph<Station, Section> graph = new WeightedMultigraph(Section.class);
    private DijkstraShortestPath<Station,Section> dijkstraShortestPath;

    public Graph(List<Station> stations, List<Section> sections) {
        stations.forEach(station -> graph.addVertex(station));
        sections.forEach(section -> graph.addEdge(section.getUpStation(), section.getDownStation(), section));
        dijkstraShortestPath = new DijkstraShortestPath(graph);
    }

    public List<StationResponse> getPathStations(Station sourceStation, Station targetStation) {
        try {
            List<Station> stations = dijkstraShortestPath.getPath(sourceStation, targetStation).getVertexList();
            return stations.stream()
                    .map(StationResponse::of)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new NotExistsDataException(NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE);
        }
    }

    public List<Long> getPathLineIds(Station sourceStation, Station targetStation) {
        try {
            return new ArrayList<>(dijkstraShortestPath.getPath(sourceStation, targetStation)
                    .getEdgeList().stream()
                    .map(Section::getLineId)
                    .collect(Collectors.toSet()));
        } catch (NullPointerException e) {
            throw new NotExistsDataException(NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE);
        }
    }

    public int getPathDistance(Station sourceStation, Station targetStation){
        return (int) dijkstraShortestPath.getPath(sourceStation, targetStation).getWeight();
    }
}
