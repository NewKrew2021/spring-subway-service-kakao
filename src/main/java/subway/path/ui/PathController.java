package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
public class PathController {
    // TODO: 경로조회 기능 구현하기

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> searchShortestPath(@RequestParam Long source, @RequestParam Long target) {
        return ResponseEntity.ok().body(pathService.searchShortestPath(source, target));
    }
}
