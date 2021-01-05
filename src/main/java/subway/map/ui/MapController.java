package subway.map.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.map.application.MapService;
import subway.map.dto.MapResponse;

@RestController
public class MapController {
    private MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/maps")
    public ResponseEntity getMaps() {
        MapResponse response = mapService.retrieveMap();
        return ResponseEntity.ok().body(response);
    }
}
