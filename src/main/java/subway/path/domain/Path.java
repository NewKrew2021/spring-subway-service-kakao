package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Path {

    WeightedMultigraph<PathVertex, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    private PathVertices pathVertices;

    public void initPath(List<Line> lines){
        List<Station> stations = new ArrayList<>();
        lines.stream().map(Line::getStations).forEach(sts -> stations.addAll(sts));
        this.pathVertices = PathVertices.from(stations.stream().distinct().collect(Collectors.toList()));

        pathVertices.getPathVertexList().forEach(vertex -> graph.addVertex(vertex));
        lines.stream().map(Line::getSections).forEach(this::addSections);
    }

    public void addSections(Sections sections){
        sections.getSections().forEach(section -> graph.setEdgeWeight(graph.addEdge(
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
}