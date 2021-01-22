package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareCalculator;
import subway.path.domain.path.Path;
import subway.path.domain.path.PathValue;
import subway.station.application.StationService;

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

    public PathValue findPaths(long source, long target, LoginMember loginMember) {
        Path path = Path.from(
                lineService.findLines(),
                stationService.findStationById(source),
                stationService.findStationById(target)
        );
        int distance = path.getDistance();
        return new PathValue(path.getStations(), distance, fareCalculator.getFare(distance, path.getLines(), loginMember));
    }
}
