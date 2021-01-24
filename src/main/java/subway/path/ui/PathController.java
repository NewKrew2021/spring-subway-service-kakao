package subway.path.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//.when().get(String.format("/paths?source=%d&target=%d", sourceStation.getId(), targetStation.getId()))
@RestController
public class PathController {
    // TODO: 경로조회 기능 구현하기

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping(value = "/paths")
    public ResponseEntity<PathResponse> getShortestPath(@RequestParam("source") Long sourceId , @RequestParam("target") Long targetId) {
        PathResponse pathResponse = pathService.getShortestPath(sourceId,targetId);
        return ResponseEntity.ok().body(pathResponse);
    }
}
