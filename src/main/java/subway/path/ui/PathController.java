package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.Age;
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
    public ResponseEntity<PathResponse> getShortPath(@AuthenticationPrincipal LoginMember loginMember, @RequestParam("source") Long sourceId, @RequestParam("target") Long targetId) {
        if (loginMember == null) {
            return ResponseEntity.ok().body(pathService.getShortPath(sourceId, targetId, Age.ADULT));
        }
        return ResponseEntity.ok().body(pathService.getShortPath(sourceId, targetId, Age.getAge(loginMember.getAge())));
    }

}