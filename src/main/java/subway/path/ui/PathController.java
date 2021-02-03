package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
public class PathController {
    private PathService pathService;
    private LineService lineService;
    private StationService stationService;

    @Autowired
    public PathController(PathService pathService, LineService lineService, StationService stationService) {
        this.pathService = pathService;
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findPath(
            @AuthenticationPrincipal(required = false) LoginMember loginMember,
            @RequestParam(name = "source") Long sourceStationId,
            @RequestParam(name = "target") Long targetStationId) {
        PathResponse response = pathService.findPath(
                loginMember,
                sourceStationId,
                targetStationId
        );
        return ResponseEntity.ok(response);
    }
}
