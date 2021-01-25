package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathService {

    private StationService stationService;
    private LineService lineService;

    @Autowired
    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getPath(Long sourceStationId, Long targetStationId) {

        WeightedMultigraph<Station, DefaultWeightedEdge> graph = makeGraph();

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);


        GraphPath graphPath = dijkstraShortestPath.getPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId)
        );

        List<Station> stations = graphPath.getVertexList();

        int fare = 1250;

        fare += findMaxLineFare(stations);

        Path path = new Path(graphPath.getVertexList(), (int) graphPath.getWeight(), fare);

        return PathResponse.of(path);
    }

    private int findMaxLineFare(List<Station> stations) {

        int maxLineFare = 0;

        for (int i = 0; i < stations.size() - 1; i++) {
            int lineFare = findLineFare(stations.get(i), stations.get(i + 1));

            if (maxLineFare < lineFare) {
                maxLineFare = lineFare;
            }
        }

        return maxLineFare;

    }

    private int findLineFare(Station source, Station target) {
        List<Line> lines = lineService.findLines();

        int minimumDistance = Integer.MAX_VALUE;
        int lineFare = Integer.MAX_VALUE;

        for (Line line : lines) {
            Sections sections = line.getSections();
            for (Section section : sections.getSections()) {
                if (
                        ( target.equals(section.getDownStation())
                                && source.equals(section.getUpStation()) )
                        || ( source.equals(section.getDownStation())
                                && target.equals(section.getUpStation()) ) )  {
                    if (section.getDistance() <= minimumDistance) {

                        lineFare = Integer.min(lineFare, line.getExtraFare());
                    }
                }
            }
        }
        return lineFare;
    }

    private WeightedMultigraph<Station, DefaultWeightedEdge> makeGraph() {

        Sections sections = lineService.findAllSections();

        List<Station> stations = stationService.findAll();

        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

        stations.forEach(graph::addVertex);

        for (Section section : sections.getSections()) {
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
        }

        return graph;
    }
}
