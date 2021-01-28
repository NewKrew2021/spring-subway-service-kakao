package subway.line.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.line.dto.LineRequest;
import subway.line.dto.LineResponse;
import subway.line.dto.SectionRequest;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {

    private LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping
    public ResponseEntity createLine(@RequestBody LineRequest lineRequest) {
        Line line = lineService.saveLine(lineRequest);
        return ResponseEntity
                .created(URI.create("/lines/" + line.getId()))
                .body(LineResponse.of(line));
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> findAllLines() {
        return ResponseEntity.ok(LineResponse.listOf(lineService.findLines()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineResponse> findLineById(@PathVariable Long id) {
        return ResponseEntity.ok(LineResponse.of(lineService.findLineById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateLine(@PathVariable Long id,
                                     @RequestBody LineRequest lineUpdateRequest) {
        lineService.updateLine(id,
                lineUpdateRequest.getName(),
                lineUpdateRequest.getColor(),
                lineUpdateRequest.getExtraFare());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLine(@PathVariable Long id) {
        lineService.deleteLineById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lineId}/sections")
    public ResponseEntity addLineStation(@PathVariable Long lineId,
                                         @RequestBody SectionRequest sectionRequest) {
        lineService.addLineStation(lineId,
                sectionRequest.getUpStationId(),
                sectionRequest.getDownStationId(),
                sectionRequest.getDistance());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lineId}/sections")
    public ResponseEntity removeLineStation(@PathVariable Long lineId, @RequestParam Long stationId) {
        lineService.removeLineStation(lineId, stationId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLException() {
        return ResponseEntity.badRequest().build();
    }
}
