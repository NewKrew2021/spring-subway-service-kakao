package subway.path.ui;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

@Controller
@RequestMapping("/paths")
public class PathController {

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findPath(@AuthenticationPrincipal(required = false) LoginMember loginMember, @RequestParam("source") Long sourceId, @RequestParam("target") Long targetId) {
        return ResponseEntity.ok().body(pathService.findPath(loginMember, sourceId, targetId));
    }
}
