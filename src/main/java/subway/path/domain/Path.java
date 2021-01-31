package subway.path.domain;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

public class Path {

    WeightedMultigraph<PathVertex, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    private PathVertices pathVertices;

    public Path(List<Line> lines){
        this(PathVertices.from(lines));
        lines.forEach(line -> addSections(line.getSections()));
    }

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

    public List<Line> findLineListInPath(PathVertices pathVertices){

        Set<Line> lineSet = new HashSet<>();
        List<Line> previousLineList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            Line duplicateLine = getDuplicateLineId(pathVertex.getLineList(), previousLineList);
            addLineIdIfExist(duplicateLine, lineSet);
            previousLineList = pathVertex.getLineList();
        }
        return new ArrayList<>(lineSet);
    }

    private void addLineIdIfExist(Line dup, Set<Line> lineSet){
        if(dup != null)
            lineSet.add(dup);
    }

    private Line getDuplicateLineId(List<Line> newLineList, List<Line> previousLineList){

        return newLineList
                .stream()
                .filter(newLine -> previousLineList.contains(newLine))
                .findFirst()
                .orElse(null);
    }
}