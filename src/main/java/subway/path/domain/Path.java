package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Path {
    private final long sourceId;
    private final long destId;
    private final List<Section> sections;
    private final DijkstraShortestPath dijkstraShortestPath;

    public Path(long sourceId, long destId, List<Section> sections) {
        this.sourceId = sourceId;
        this.destId = destId;
        this.sections = sections;
        this.dijkstraShortestPath = initDijkstraShortestPath();
    }

    private DijkstraShortestPath initDijkstraShortestPath() {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        sections.stream()
                .forEach(section -> {
                    long upStationId = section.getUpStation().getId();
                    long downStationId = section.getDownStation().getId();
                    graph.addVertex(upStationId);
                    graph.addVertex(downStationId);
                    graph.setEdgeWeight(
                            graph.addEdge(upStationId, downStationId),
                            section.getDistance()
                    );
                });

        return new DijkstraShortestPath(graph);
    }

    public List<Station> getStations() {
        Map<Long, Station> cache = sections.stream()
                .flatMap(section -> section.getStations().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Station::getId, Function.identity()
                ));

        List<Long> stationIds = getStationIds();
        List<Station> stations = new ArrayList<>();

        for (Long stationId : stationIds) {
            stations.add(cache.get(stationId));
        }
        return stations;
    }

    public int getFare() {
        return 0;
    }

    private List<Long> getStationIds() {
        return dijkstraShortestPath.getPath(sourceId, destId).getVertexList();
    }

    public int getDistance() {
        return (int) dijkstraShortestPath.getPathWeight(sourceId, destId);
    }
}