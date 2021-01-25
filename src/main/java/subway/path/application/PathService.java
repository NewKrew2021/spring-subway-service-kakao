package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.path.domain.DirectedSections;
import subway.path.domain.SubwayNavigator;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

@Service
public class PathService {
    LineService lineService;
    StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathResponse getShortestPath(Long sourceStationId, Long targetStationId) {
        SubwayNavigator subwayNavigator = new SubwayNavigator(lineService.findLines());

        DirectedSections directedSections = subwayNavigator.getShortestPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId));

        return new PathResponse(
                StationResponse.listOf(directedSections.getStations()),
                directedSections.getDistance(),
                0);
    }

}
