package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
@RequestMapping("/paths")
public class PathController {
    PathService pathService;
    StationService stationService;

    public PathController(PathService pathService, StationService stationService) {
        this.pathService = pathService;
        this.stationService = stationService;
    }

    @GetMapping("")
    public ResponseEntity<PathResponse> getShortestPath(@AuthenticationPrincipal(required = false) LoginMember loginMember,
                                                        @RequestParam("source") Long sourceId,
                                                        @RequestParam("target") Long targetId) {
        PathResponse response = pathService.getShortestPathWithFare(
                loginMember,
                stationService.findStationById(sourceId),
                stationService.findStationById(targetId)
        );
        return ResponseEntity.ok(response);
    }

}
