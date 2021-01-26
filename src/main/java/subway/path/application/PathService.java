package subway.path.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.member.domain.AGE;
import subway.path.domain.Dijksatra;
import subway.path.domain.DistanceFare;
import subway.path.domain.Vertices;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {

    private final StationService stationService;
    private final LineService lineService;

    @Autowired
    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getShortPath(Long sourceId, Long targetId, AGE age) {
        List<Line> lines = lineService.findLines();
        Dijksatra dijksatra = new Dijksatra(lines);
        Vertices vertices = new Vertices(dijksatra.getPathVertices(sourceId, targetId));
        int distance = dijksatra.getPathWeight(sourceId, targetId);
        int distanceFare = DistanceFare.getDistanceFare(distance);
        int lineExtraFare = vertices.getExtraFare();
        return PathResponse.of(vertices.getVertices().stream()
                .map(vertex -> StationResponse.of(stationService.findStationById(vertex.getStationId())))
                .collect(Collectors.toList()), distance, calculateFare(age, distanceFare, lineExtraFare));
    }

    private int calculateFare(AGE age, int distanceFare, int lineExtraFare) {
        return (int) ((distanceFare + lineExtraFare) * age.getSale() + age.getDeduction());
    }


}
