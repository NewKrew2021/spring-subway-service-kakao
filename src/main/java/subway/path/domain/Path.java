package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public Path() {
        this.graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    }

    public void addStations(List<Station> stations) {
        stations.forEach(graph::addVertex);
    }

    public void addEdges(List<Section> sections) {
        for( Section section: sections ) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();

            DefaultWeightedEdge edge = graph.addEdge(upStation, downStation);

            graph.setEdgeWeight(edge, section.getDistance());
        }
    }

    public boolean containStation(Station station) {
        return graph.containsVertex(station);
    }

    public boolean containEdge(Station source, Station target) {
        return graph.containsEdge(source, target);
    }



}
