package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.domain.Path;
import subway.path.domain.Person;
import subway.path.dto.PathResponse;

@RestController
public class PathController {
    private final PathService pathService;

    @Autowired
    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findShortestPath(@AuthenticationPrincipal(required = false) LoginMember loginMember,
                                                         @RequestParam("source") long sourceId,
                                                         @RequestParam("target") long targetId) {
        Path path = pathService.find(sourceId, targetId);
        int fare = pathService.calculateFare(path, Person.of(loginMember.getAge()));
        return ResponseEntity.ok(PathResponse.of(path, fare));
    }
}
