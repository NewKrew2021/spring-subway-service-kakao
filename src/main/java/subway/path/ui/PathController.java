package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.line.application.LineService;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
public class PathController {
    // TODO: 경로조회 기능 구현하기

    private PathService pathService;
    private LineService lineService;
    private StationService stationService;

    @Autowired
    public PathController(PathService pathService, LineService lineService, StationService stationService) {
        this.pathService = pathService;
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findPath(
            @RequestParam(name = "source") Long sourceStationId,
            @RequestParam(name = "target") Long targetStationId) {
        PathResponse response = pathService.getPath(sourceStationId, targetStationId);
        return ResponseEntity.ok(response);
    }
}
