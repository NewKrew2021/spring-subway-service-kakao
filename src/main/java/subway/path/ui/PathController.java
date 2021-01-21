package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
public class PathController {
    private PathService pathService;

    public PathController(PathService pathService){
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findShortestPath(@RequestParam("source") long sourceId, @RequestParam("target") long targetId) {
        return ResponseEntity.ok(pathService.find(sourceId, targetId).toResponse());
    }
}
