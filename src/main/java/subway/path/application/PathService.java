package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.domain.PathValue;
import subway.station.application.StationService;

import java.util.List;

@Service
public class PathService {

    private final LineService lineService;
    private final StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathValue findPaths(long source, long target, LoginMember loginMember) {

        List<Line> lines = lineService.findLines();

        Path path = Path.from(lines, stationService.findStationById(source), stationService.findStationById(target));

        return new PathValue(path.getStations(), path.getDistance(), path.getFare(loginMember));
    }
}
