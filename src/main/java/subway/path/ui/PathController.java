package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;
    private final StationService stationService;

    public PathController(PathService pathService, StationService stationService) {
        this.pathService = pathService;
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> getPath(@AuthenticationPrincipal LoginMember loginMember,
                                                @RequestParam String source, @RequestParam String target) {
        Station sourceStation = stationService.findStationById(Long.valueOf(source));
        Station targetStation = stationService.findStationById(Long.valueOf(target));
        return ResponseEntity.ok().body(
                pathService.getShortestPathOfStations(sourceStation, targetStation, loginMember.getAge()));
    }
}
