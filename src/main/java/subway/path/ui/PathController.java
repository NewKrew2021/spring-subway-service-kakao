package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
@RequestMapping("/paths")
public class PathController {
    LineService lineService;
    StationService stationService;

    public PathController(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping("")
    public ResponseEntity<PathResponse> getShortestPath(@AuthenticationPrincipal(required = false) LoginMember loginMember,
                                                        @RequestParam("source") Long sourceId,
                                                        @RequestParam("target") Long targetId) {
        PathResponse response = lineService.getShortestPathWithFare(
                loginMember,
                stationService.findStationById(sourceId),
                stationService.findStationById(targetId)
        );
        return ResponseEntity.ok(response);
    }

}
