package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
public class PathController {
    private final PathService pathService;

    @Autowired
    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    // TODO: 경로조회 기능 구현하기
    @GetMapping("/paths")
    public ResponseEntity<PathResponse> getShortPath(@RequestParam("source") Long sourceId, @RequestParam("target") Long targetId) {
        return ResponseEntity.ok().body(pathService.getShortPath(sourceId, targetId));
    }


}