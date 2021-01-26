package subway.path.ui;

import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.path.domain.DistanceExtraFare;
import subway.path.domain.SubwayPathGraph;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;

    public PathService(SectionDao sectionDao, StationDao stationDao) {
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
    }

    public PathResponse getShortestpathResponse(Long source, Long target) {
        SubwayPathGraph subwayPathGraph = new SubwayPathGraph(
                new Sections(sectionDao.findAll()), source, target);

        List<StationResponse> shortestStations = subwayPathGraph.getVertexList()
                .stream()
                .map((String stationId) -> stationDao.findById(Long.parseLong(stationId)))
                .map((Station station) -> new StationResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        return new PathResponse(shortestStations,
                subwayPathGraph.getTotalDistance(), DistanceExtraFare.getTotalFare(subwayPathGraph.getTotalDistance()));
    }
}
