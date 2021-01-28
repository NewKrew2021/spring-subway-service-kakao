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

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping(value = "/paths")
    public ResponseEntity<PathResponse> getShortestPath(
                                @AuthenticationPrincipal(required = false) LoginMember loginMember,
                                @RequestParam("source") Long sourceId,
                                @RequestParam("target") Long targetId) {
        PathResponse pathResponse = pathService.getShortestPath(sourceId, targetId, loginMember);
        return ResponseEntity.ok().body(pathResponse);
    }

}
