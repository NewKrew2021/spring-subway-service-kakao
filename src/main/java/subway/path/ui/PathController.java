package subway.path.ui;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.path.application.PathService;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

@RestController
public class PathController {

  PathService pathService;

  @Autowired
  public PathController(PathService pathService) {
    this.pathService = pathService;
  }

  @GetMapping("/paths")
  public ResponseEntity<PathResponse> findShortestPath(@RequestParam Long source,
      @RequestParam Long target) {
    Path shortestPath = pathService.getShortestPath(source, target);

    PathResponse pathResponse = new PathResponse(
        shortestPath.getStations()
            .stream()
            .map(StationResponse::of)
            .collect(Collectors.toList()), shortestPath.getDistance());

    return ResponseEntity.ok(pathResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity exceptionHandle(Exception e) {
    e.printStackTrace();

    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
