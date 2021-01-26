package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import subway.auth.dto.TokenRequest;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
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
    public ResponseEntity<PathResponse> getPaths(HttpServletRequest request, @RequestParam Long source, @RequestParam Long target){
        String token = AuthorizationExtractor.extract(request);
        String email = null;
        if(token != null && jwtTokenProvider.validateToken(token)){
            email = jwtTokenProvider.getPayload(token);
        }
        PathResult result = pathService.findShortestPath(source, target, email);
        PathResponse pathResponse = new PathResponse(result);
        return ResponseEntity.ok().body(pathResponse);
    }
}

