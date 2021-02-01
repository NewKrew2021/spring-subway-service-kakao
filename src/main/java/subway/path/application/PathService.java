package subway.path.application;

import org.springframework.stereotype.Service;
import subway.path.domain.PriceCalculator;
import subway.path.domain.ResultPath;
import subway.line.domain.SubwayMap;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

@Service
public class PathService {
    private final SubwayMap map;

    public PathService(SubwayMap map) {
        this.map = map;
    }

    public PathResponse getShortestPathWithFare(LoginMember loginMember,
                                                Station sourceStation,
                                                Station targetStation) {
        ResultPath directedSections = map.calculateShortestPath(sourceStation, targetStation);
        return new PathResponse(
                StationResponse.listOf(directedSections.getStations()),
                directedSections.getDistance(),
                PriceCalculator.calculateResult(loginMember, directedSections)
        );
    }
}
