package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.DirectedSections;
import subway.path.domain.SubwayMap;
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

    public PathResponse getShortestPath(LoginMember loginMember, Long sourceStationId, Long targetStationId) {
        DirectedSections directedSections = getDirectedSections(sourceStationId, targetStationId);
        int finalPrice = loginMember.equals(LoginMember.NO_ONE) ? directedSections.getPrice() : directedSections.getDiscountPrice(loginMember.getAge());

        return new PathResponse(
                StationResponse.listOf(directedSections.getStations()),
                directedSections.getDistance(),
                finalPrice
        );
    }

    private DirectedSections getDirectedSections(Long sourceStationId, Long targetStationId) {
        SubwayMap subwayMap = new SubwayMap(lineService.findLines());

        return subwayMap.getShortestPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId));
    }
}
