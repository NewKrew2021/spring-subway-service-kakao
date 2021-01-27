package subway.path.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareStrategy;
import subway.path.domain.fare.FareStrategyFactory;
import subway.path.domain.path.Path;
import subway.path.dto.PathDto;
import subway.station.application.StationService;

@Service
public class PathService {
    private final LineService lineService;
    private final StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @Transactional(readOnly = true)
    public PathDto findPath(long sourceId, long destId, LoginMember loginMember) {
        Path path = new Path(stationService.findStationById(sourceId),
                stationService.findStationById(destId),
                lineService.findLines());
        int distance = path.getDistance();
        int extraFare = path.getExtraFare();


        FareStrategy fareStrategy = FareStrategyFactory.create(distance,extraFare,loginMember);


        return new PathDto(path.getStations(), path.getDistance(), fareStrategy.getFare());
    }
}
