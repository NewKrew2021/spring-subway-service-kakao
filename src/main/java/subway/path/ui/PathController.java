package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.dto.TokenRequest;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
@RequestMapping("/paths")
public class PathController {
    // TODO: 경로조회 기능 구현하기

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findShortestPath(@RequestBody TokenRequest tokenRequest, @RequestParam long source, @RequestParam long target) {
        return ResponseEntity.ok(pathService.findPathResponse(source, target, loginMember.getAge()));
    }
//
//    @GetMapping
//    public ResponseEntity<PathResponse> findShortestPath(@RequestParam long source, @RequestParam long target) {
//        return ResponseEntity.ok(pathService.findPathResponse(source, target, 0));
//    }
}
