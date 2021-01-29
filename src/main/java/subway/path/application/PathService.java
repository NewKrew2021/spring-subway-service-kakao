package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareCalculator;
import subway.path.domain.path.PathType;
import subway.path.domain.path.PathValue;
import subway.path.domain.path.SubwayPath;
import subway.path.domain.path.graph.SubwayMap;
import subway.station.application.StationService;

import java.time.LocalDateTime;

@Service
public class PathService {

    private final LineService lineService;
    private final StationService stationService;
    private final FareCalculator fareCalculator;

    public PathService(LineService lineService, StationService stationService, FareCalculator fareCalculator) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.fareCalculator = fareCalculator;
    }

    public PathValue findPath(long source, long target, LoginMember loginMember, LocalDateTime departureTime, PathType pathType) {
        SubwayMap map = pathType.generateMapBy(lineService.findLines());
        SubwayPath path = map.getPath(
                stationService.findStationById(source),
                stationService.findStationById(target),
                departureTime
        );

        int fare = fareCalculator.getFare(path.getDistance(), path.getLines(), loginMember);
        return new PathValue(path.getStations(), path.getDistance(), fare, path.getArrivalTime());
    }
}
