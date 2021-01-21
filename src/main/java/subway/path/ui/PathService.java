package subway.path.ui;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;

    public PathService(SectionDao sectionDao, StationDao stationDao){
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
    }

    public PathResponse getShortestpathResponse(Long source, Long target){
        WeightedMultigraph<String, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);
        List<Section> sections = sectionDao.findAll();
        Set<Long> stationIdsSet = new HashSet<>();
        for(Section section : sections){
            stationIdsSet.add(section.getUpStation().getId());
            stationIdsSet.add(section.getDownStation().getId());
        }
        List<Long> stationIds = new ArrayList<>(stationIdsSet);

        for(Long stationId: stationIds){
            graph.addVertex(stationId.toString());
        }
        for(Section section: sections){
            graph.setEdgeWeight(graph.addEdge(
                    section.getUpStation().getId().toString(),
                    section.getDownStation().getId().toString()),
                    section.getDistance());
        }

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);

        List<String> shortestStationIds = dijkstraShortestPath
                .getPath(source.toString(), target.toString())
                .getVertexList();

        List<StationResponse> shortestStations = shortestStationIds
                .stream()
                .map((String stationId) -> stationDao.findById(Long.parseLong(stationId)))
                .map((Station station) -> new StationResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        int sumOfDistance = (int) dijkstraShortestPath.getPath(source.toString(), target.toString()).getWeight();

        return new PathResponse(shortestStations, sumOfDistance, 0);
    }
}
