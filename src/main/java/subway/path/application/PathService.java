package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {

    private LineService lineService;
    private StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathResponse getShortestPath(Long sourceId, Long targetId, LoginMember loginMember) {
        List<Line> lines = lineService.findLines();
        Station sourceStation = stationService.findStationById(sourceId);
        Station targetStation = stationService.findStationById(targetId);
        Path path = new Path(lines, sourceStation, targetStation, loginMember);
        return PathResponse.make(path.getStations(), path.getDistance(), path.getFare());
    }

}
