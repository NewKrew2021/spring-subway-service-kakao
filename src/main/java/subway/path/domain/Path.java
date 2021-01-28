package subway.path.domain;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Sections;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

public class Path {

    WeightedMultigraph<PathVertex, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    private PathVertices pathVertices;

    public Path(PathVertices pathVertices){
        this.pathVertices = pathVertices;

        pathVertices.getPathVertexList().forEach(vertex -> graph.addVertex(vertex));
    }

    public void addSections(Sections sections){
        sections.getSections()
                .forEach(section ->
                        graph.setEdgeWeight(graph.addEdge(
                                pathVertices.getPathVertexByStation(section.getUpStation()),
                                pathVertices.getPathVertexByStation(section.getDownStation())),
                                section.getDistance()));
    }

    public PathResult findShortestPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
        GraphPath result = dijkstraShortestPath.getPath(
                this.pathVertices.getPathVertexByStation(sourceStation),
                this.pathVertices.getPathVertexByStation(targetStation));
        return new PathResult(PathVertices.of(result.getVertexList()), (int)result.getWeight());
    }

    public List<Long> findLineIdListInPath(PathVertices pathVertices){

        Set<Long> lineIdSet = new HashSet<>();
        List<Long> previousLineIdList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            Optional<Long> duplicateLineId = getDuplicateLineId(pathVertex.getLineList(), previousLineIdList);
            addLineIdIfExist(duplicateLineId, lineIdSet);
            previousLineIdList = pathVertex.getLineList();
        }
        return new ArrayList<>(lineIdSet);
    }

    private void addLineIdIfExist(Optional<Long> dup, Set<Long> lineIdSet){
        if(dup.isPresent())
            lineIdSet.add(dup.get());
    }

    private Optional<Long> getDuplicateLineId(List<Long> newLineIdList, List<Long> previousLineList){

        return newLineIdList
                .stream()
                .filter(newLineId -> previousLineList.contains(newLineId))
                .map(id -> Optional.of(id))
                .findFirst()
                .orElse(Optional.empty());
    }
}