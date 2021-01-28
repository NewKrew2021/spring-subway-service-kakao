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

    private final int BASIC_FARE = 1250;
    private final int FREE_DISTANCE_BOUND = 10;
    private final int FARE_DISCOUNT_DISTANCE_BOUND = 50;

    private final int KID_LOWER_BOUND = 6;
    private final int KID_UPPER_BOUND = 13;
    private final int TEEN_UPPER_BOUND = 19;

    public Path(PathVertices pathVertices, Sections sections){
        this.pathVertices = pathVertices;

        pathVertices.getPathVertexList().forEach(vertex -> graph.addVertex(vertex));
        sections.getSections()
                .forEach(section ->
                        graph.setEdgeWeight(graph.addEdge(
                        pathVertices.getPathVertexByStation(section.getUpStation()),
                        pathVertices.getPathVertexByStation(section.getDownStation())),
                                section.getDistance()));
    }

    public GraphPath findShortestPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
        GraphPath result = dijkstraShortestPath.getPath(
                this.pathVertices.getPathVertexByStation(sourceStation),
                this.pathVertices.getPathVertexByStation(targetStation));
        return result;
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

    public int calculateFare(int distance, List<Integer> extraFareList){
        int basicFare = getFareByDistance(distance);
        int extraFare = getExtraFareByLines(extraFareList);

        return basicFare + extraFare;
    }

    private int getFareByDistance(int distance) {
        int result = BASIC_FARE;

        if (distance > FREE_DISTANCE_BOUND && distance <= FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }

        if (distance > FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 50) / 8) * 100 + 800;
        }
        return result;
    }

    private int getExtraFareByLines(List<Integer> extraFareList){
        return extraFareList.stream().max(Integer::compare).orElse(0);
    }

    public int discount(int age, int fare){
        if(age < KID_LOWER_BOUND){
            return 0;
        }

        if(age >=KID_LOWER_BOUND && age < KID_UPPER_BOUND){
            return (int) ((fare - 350) * 0.5) + 350;
        }

        if(age >= KID_UPPER_BOUND && age < TEEN_UPPER_BOUND){
            return (int) ((fare - 350) * 0.8) + 350;
        }

        return fare;
    }
}