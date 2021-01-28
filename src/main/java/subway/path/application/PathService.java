package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Lines;
import subway.member.domain.Age;
import subway.path.domain.Vertex;
import subway.path.domain.strategy.AgeFare;
import subway.path.domain.strategy.DistanceFare;
import subway.path.domain.strategy.FareStrategy;
import subway.path.domain.strategy.LineFare;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    public static final int DEFAULT_FARE = 0;
    private final StationService stationService;
    private final LineService lineService;

    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getShortPath(Long sourceId, Long targetId, Age age) {
        Lines lines = Lines.of(lineService.findLines());
        List<Vertex> vertexs = lines.getVertexs(sourceId, targetId);

        int distance = lines.getDistance(sourceId, targetId);

        FareStrategy fareStrategy = new DistanceFare(distance);
        int fare = fareStrategy.apply(DEFAULT_FARE);

        fareStrategy = new LineFare(vertexs);
        fare = fareStrategy.apply(fare);

        fareStrategy = new AgeFare(age);
        fare = fareStrategy.apply(fare);

        return PathResponse.of(getStationResponses(vertexs), distance, fare);
    }

    private List<StationResponse> getStationResponses(List<Vertex> vertexs) {
        return vertexs.stream()
                .map(vertex -> StationResponse.of(stationService.findStationById(vertex.getStationId())))
                .collect(Collectors.toList());
    }
}
