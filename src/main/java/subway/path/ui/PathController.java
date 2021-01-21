package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

import java.nio.file.Path;

@RestController
@RequestMapping("/paths")
public class PathController {
    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    // TODO: 경로조회 기능 구현하기
    @GetMapping
    public ResponseEntity<PathResponse> findMinDistance(@RequestParam Long source, @RequestParam Long target) {
        return ResponseEntity.ok(pathService.findMinDistance(source, target));
    }
}
