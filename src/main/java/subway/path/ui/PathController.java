package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
@RequestMapping("/paths")
public class PathController {
    PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("")
    public ResponseEntity<PathResponse> getShortestPath(@RequestParam("source") Long sourceId,
                                                        @RequestParam("target") Long targetId) {
        PathResponse response = pathService.getShortestPath(sourceId, targetId);
        return ResponseEntity.ok(response);
    }
}
