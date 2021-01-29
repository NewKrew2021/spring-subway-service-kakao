package subway.path.ui;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.domain.path.PathType;
import subway.path.dto.PathResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/paths")
public class PathController {

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findPath(
            @AuthenticationPrincipal(required = false) LoginMember loginMember,
            @RequestParam long source,
            @RequestParam long target,
            @RequestParam("time") @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime departureAt,
            @RequestParam(defaultValue = "DISTANCE") PathType type
    ) {
        return ResponseEntity.ok(PathResponse.from(pathService.findPath(source, target, loginMember, departureAt, type)));
    }
}
