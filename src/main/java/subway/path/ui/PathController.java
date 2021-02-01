package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@RestController
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> showPath(@AuthenticationPrincipal(required = false) LoginMember loginMember, @RequestParam("source") long sourceId, @RequestParam("target") long targetId) {
        PathResponse response = pathService.findPath(sourceId, targetId, loginMember);
        return ResponseEntity.ok(response);
    }
}

