package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    @Autowired
    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PathResponse> getShortestPath(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam Long source, @RequestParam Long target) {

        return ResponseEntity
                .ok()
                .body(pathService.getShortestPathResponse(source, target, pathService.getLoginMemberAge(loginMember)));
    }
}
