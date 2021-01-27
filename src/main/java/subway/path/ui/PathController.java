package subway.path.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.domain.AgeGroup;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

@RestController
public class PathController {

  private PathService pathService;

  public PathController(PathService pathService) {
    this.pathService = pathService;
  }

  @GetMapping("/paths")
  public ResponseEntity<PathResponse> findShortestPath(@RequestParam Long source, @RequestParam Long target,
                                                       @AuthenticationPrincipal(required = false) LoginMember loginMember) {
    Path shortestPath = pathService.getShortestPath(source, target);
    List<StationResponse> stationResponses = shortestPath.getStations()
                                                          .stream()
                                                          .map(StationResponse::of)
                                                          .collect(Collectors.toList());
    AgeGroup ageGroup = loginMember == null ? AgeGroup.ADULT : AgeGroup.getAgeGroup(loginMember.getAge());
    int fare = shortestPath.getFare(ageGroup);

    return ResponseEntity.ok(new PathResponse(stationResponses, shortestPath.getDistance(), fare));
  }
}
