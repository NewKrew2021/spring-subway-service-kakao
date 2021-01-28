package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Lines;
import subway.member.domain.LoginMember;
import subway.path.domain.Vertices;
import subway.path.domain.fare.Fare;
import subway.path.domain.fare.FareParam;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationService stationService;
    private final LineService lineService;

    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getShortestPath(Long sourceId, Long targetId, LoginMember loginMember) {
        Lines lines = Lines.of(lineService.findLines());
        Vertices vertices = Vertices.of(lines.getVertices(sourceId, targetId));

        int distance = lines.getDistance(sourceId, targetId);
        FareParam fareParam = FareParam.of(vertices.getMaxLineExtraFare(), distance);
        Fare fare = Fare.of(loginMember, fareParam);

        return PathResponse.of(getStationResponses(vertices.getStationIds()), distance, fare.getFare());
    }

    private List<StationResponse> getStationResponses(List<Long> stationIds) {
        return stationIds.stream()
                .map(stationId -> StationResponse.of(stationService.findStationById(stationId)))
                .collect(Collectors.toList());
    }
}
