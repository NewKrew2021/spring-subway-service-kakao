package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.dto.TokenRequest;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidTokenException;
import subway.member.domain.LoginMember;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import org.jgrapht.*;
import subway.path.dto.PathResult;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;

@Controller
public class PathController {
    // TODO: 경로조회 기능 구현하기

    PathService pathService;
    JwtTokenProvider jwtTokenProvider;
    public PathController(PathService pathService, JwtTokenProvider jwtTokenProvider){
        this.pathService = pathService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = "/paths", params = {"source", "target"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathResponse> getPaths(@AuthenticationPrincipal LoginMember loginMember, @RequestParam Long source, @RequestParam Long target){
        String email = (loginMember == null) ? null : loginMember.getEmail();
        PathResponse pathResponse = pathService.findShortestPath(source, target, email);
        return ResponseEntity.ok().body(pathResponse);
    }
}

