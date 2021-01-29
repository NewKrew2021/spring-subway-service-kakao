package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;

@RestController
public class PathController {

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> searchShortestPath(@AuthenticationPrincipal LoginMember loginMember, @RequestParam Long source, @RequestParam Long target) {
        Path path = pathService.searchShortestPath(source, target);
        if (loginMember == null) {
            return ResponseEntity.ok().body(PathResponse.of(path));
        }

        Path newPath = path.discountedPath(loginMember);
        return ResponseEntity.ok().body(PathResponse.of(newPath));
    }
}
