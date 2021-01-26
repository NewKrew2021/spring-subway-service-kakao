package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import subway.line.domain.SectionWithFare;
import subway.line.domain.SectionsInAllLine;
import subway.station.domain.Station;

public class PathGraph {
    private SubwayGraph<Station, SubwayEdge> subwayGraph;

    public PathGraph(SectionsInAllLine sections) {
        subwayGraph = new SubwayGraph<>(SubwayEdge.class);
        setSubwayGraph(sections);
    }

    private void setSubwayGraph(SectionsInAllLine sections) {
        sections.getSections()
                .forEach(this::addSectionToSubwayGraph);
    }

    private void addSectionToSubwayGraph(SectionWithFare section) {
        subwayGraph.addVertex(section.getUpStation());
        subwayGraph.addVertex(section.getDownStation());

        subwayGraph.setEdgeWeight((SubwayEdge) subwayGraph.addEdge(
                section.getUpStation(),
                section.getDownStation()),
                new SubwayWeight(section.getExtraFare(), section.getDistance()));
    }

    public Path getPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(subwayGraph);
        return new Path(dijkstraShortestPath.getPath(sourceStation, targetStation));
    }

}
