package subway.path.domain;

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
//    기본운임(10㎞ 이내) : 기본운임 1,250원
//    이용 거리초과 시 추가운임 부과
//    10km 초과 ∼ 50km 까지(5km 마다 100원)
//    50km 초과 시 (8km 마다 100원)
    public int getBasicFare(int distance) {
        int result = 1250;

        if (distance > 10 && distance <= 50) {
            result += (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }

        if (distance > 50) {
            result += (int) Math.ceil((double)(distance - 50) / 8) * 100 + 800;
        }
        return result;
    }

    public List<Long> findLineIdListInPath(PathVertices pathVertices){

        Set<Long> lineIdSet = new HashSet<>();
        List<Long> previousLineIdList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            Optional<Long> duplicateLineId = getDuplicateLineId(pathVertex.getLineList(), previousLineIdList);
            if(duplicateLineId.isPresent()){
                lineIdSet.add(duplicateLineId.get());
            }
            previousLineIdList = pathVertex.getLineList();
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