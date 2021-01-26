package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareCalculator;
import subway.path.domain.path.PathType;
import subway.path.domain.path.PathValue;
import subway.path.domain.path.SubwayGraph;
import subway.path.domain.path.SubwayPath;
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

    public PathValue findPath(long source, long target, LoginMember loginMember, LocalDateTime departureTime, PathType type) {
        SubwayGraph graph = SubwayGraph.from(lineService.findLines());
        SubwayPath path = graph.getPath(stationService.findStationById(source), stationService.findStationById(target));

        int fare = fareCalculator.getFare(path.getDistance(), path.getLines(), loginMember);
//        return new PathValue(path.getStations(), path.getDistance(), fare, path.getArrivalAt());
        return new PathValue(path.getStations(), path.getDistance(), fare, LocalDateTime.of(2021, 1, 26, 6, 50));
    }
}
