package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Sections;
import subway.path.dto.PathResult;
import java.util.*;

public class Path {

    WeightedMultigraph<PathVertex, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);


    private PathVertices pathVertices;

    public Path(PathVertices pathVertices, Sections sections){
        this.pathVertices = pathVertices;

        pathVertices.getPathVertexList().forEach(vertex -> graph.addVertex(vertex));
        sections.getSections()
                .forEach(section ->
                        graph.setEdgeWeight(graph.addEdge(
                        pathVertices.getPathVertexByStationId(section.getUpStation().getId()),
                        pathVertices.getPathVertexByStationId(section.getDownStation().getId())),
                                section.getDistance()));
    }

    public PathResult findShortestPath(Long sourceStationId, Long targetStationId) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
        GraphPath result = dijkstraShortestPath.getPath(
                this.pathVertices.getPathVertexByStationId(sourceStationId),
                this.pathVertices.getPathVertexByStationId(targetStationId));
        List<PathVertex> vertexList = result.getVertexList();
        int distance = (int) result.getWeight();
        return new PathResult(new PathVertices(vertexList), distance);
    }

    public List<Long> findLineIdListInPath(PathVertices pathVertices){

        Set<Long> lineIdSet = new HashSet<>();
        List<Long> previousLineIdList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            Optional<Long> duplicateLineId = getDuplicateLineId(pathVertex.getLineIdList(), previousLineIdList);
            if(duplicateLineId.isPresent()){
                lineIdSet.add(duplicateLineId.get());
            }
            previousLineIdList = pathVertex.getLineIdList();
        }
        return new ArrayList<>(lineIdSet);
    }

    private Optional<Long> getDuplicateLineId(List<Long> newLineIdList, List<Long> previousLineList){
        for(Long newLineId: newLineIdList){
            if(previousLineList.contains(newLineId)){
                return Optional.of(newLineId);
            }
        }
        return Optional.empty();
    }

}