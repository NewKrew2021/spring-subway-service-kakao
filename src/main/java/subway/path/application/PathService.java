package subway.path.application;

import org.springframework.stereotype.Service;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.*;
import subway.path.domain.path.Path;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

@Service
public class PathService {
    private final LineService lineService;
    private final StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathResponse findPath(long sourceId, long destId, LoginMember loginMember) {
        Path path = new Path(stationService.findStationById(sourceId),
                stationService.findStationById(destId),
                lineService.findLines());
        Distance distance = path.getDistance();
        Fare extraFare = path.getExtraFare();

        FarePolicy farePolicy = new FarePolicy();
        farePolicy = new ExtraFarePolicyByDistance(farePolicy, distance);
        farePolicy = new ExtraFarePolicyByExtraFare(farePolicy, extraFare);
        if (loginMember != null) {
            farePolicy = new DiscountFarePolicyByAge(farePolicy, loginMember.getAge());
        }

        return PathResponse.of(
                StationResponse.listOf(path.getStations()),
                distance,
                farePolicy.getFare());
    }
}
