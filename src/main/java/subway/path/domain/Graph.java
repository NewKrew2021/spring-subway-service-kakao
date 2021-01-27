package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exceptions.NotExistsDataException;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private final WeightedMultigraph<Station, Section> graph = new WeightedMultigraph<>(Section.class);
    private final DijkstraShortestPath<Station,Section> dijkstraShortestPath;
    public static final String NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE = "두 역 사이에 경로가 존재하지 않습니다.";

    public Graph(List<Station> stations, List<Section> sections) {
        stations.forEach(graph::addVertex);
        sections.forEach(section -> graph.addEdge(section.getUpStation(), section.getDownStation(), section));
        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    public List<Station> getPathStations(Station sourceStation, Station targetStation) {
        try {
            return dijkstraShortestPath.getPath(sourceStation, targetStation).getVertexList();
        } catch (NullPointerException e) {
            throw new NotExistsDataException(NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE);
        }
    }

    public List<Long> getPathLineIds(Station sourceStation, Station targetStation) {
        try {
            return dijkstraShortestPath.getPath(sourceStation, targetStation)
                    .getEdgeList().stream()
                    .map(Section::getLineId).distinct().collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new NotExistsDataException(NO_MATCHING_PATH_EXCEPTION_ERROR_MESSAGE);
        }
    }

    public int getPathDistance(Station sourceStation, Station targetStation){
        return (int) dijkstraShortestPath.getPath(sourceStation, targetStation).getWeight();
    }
}
