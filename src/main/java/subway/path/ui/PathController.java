package subway.path.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathDto;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

@RestController
public class PathController {
    private final PathService pathService;
    
    public PathController(PathService pathService) {
        this.pathService = pathService;
    }
    
    @GetMapping("/paths")
    public ResponseEntity<PathResponse> showPath(@AuthenticationPrincipal(required = false) LoginMember loginMember, @RequestParam("source") long sourceId, @RequestParam("target") long targetId) {
        PathDto pathDto = pathService.findPath(sourceId, targetId, loginMember);
        PathResponse response = new PathResponse(
                StationResponse.listOf(pathDto.getStations()),
                pathDto.getDistance(),
                pathDto.getFare()
        );
        return ResponseEntity.ok(response);
    }
}

