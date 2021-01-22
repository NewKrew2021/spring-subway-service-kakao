package subway.path.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import subway.path.application.PathService;
import subway.path.dto.PathResponse;
import org.jgrapht.*;
import subway.path.dto.PathResult;

import java.nio.file.Path;

@Controller
public class PathController {
    // TODO: 경로조회 기능 구현하기

    PathService pathService;

    public PathController(PathService pathService){
        this.pathService = pathService;
    }

    @GetMapping(value = "/paths", params = {"source", "target"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathResponse> getPaths(@RequestParam Long source, @RequestParam Long target){
        PathResult result = pathService.findShortestPath(source, target);
        PathResponse pathResponse = new PathResponse(result);
        return ResponseEntity.ok().body(pathResponse);
    }
}

