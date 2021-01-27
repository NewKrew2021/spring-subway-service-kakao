package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.SectionWithFare;
import subway.line.domain.SectionsInAllLine;
import subway.station.domain.Station;

public class PathGraph {
    private final WeightedMultigraph<Station, SubwayEdge> subwayGraph;

    public PathGraph(SectionsInAllLine sections) {
        subwayGraph = new WeightedMultigraph<>(SubwayEdge.class);
        setSubwayGraph(sections);
    }

    private void setSubwayGraph(SectionsInAllLine sections) {
        sections.getSections()
                .forEach(this::addSectionToSubwayGraph);
    }

    private void addSectionToSubwayGraph(SectionWithFare section) {
        subwayGraph.addVertex(section.getUpStation());
        subwayGraph.addVertex(section.getDownStation());
        subwayGraph.addEdge(section.getUpStation(), section.getDownStation(), new SubwayEdge(section.getDistance(), section.getExtraFare()));
    }

    public Path getPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath<Station, SubwayEdge> dijkstraShortestPath = new DijkstraShortestPath<>(subwayGraph);
        return new Path(dijkstraShortestPath.getPath(sourceStation, targetStation));
    }

}
