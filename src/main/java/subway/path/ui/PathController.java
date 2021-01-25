package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.line.application.LineService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
@RequestMapping("/paths")
public class PathController {
    LineService lineService;
    StationService stationService;

    public PathController(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping("")
    public ResponseEntity<PathResponse> getShortestPath(@RequestParam("source") Long sourceId,
                                                        @RequestParam("target") Long targetId) {
        PathResponse response = lineService.getShortestPath(
                stationService.findStationById(sourceId),
                stationService.findStationById(targetId)
        );
        return ResponseEntity.ok(response);
    }
}
