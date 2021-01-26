package subway.line.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.dto.LineResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {
    private LineDao lineDao;
    private SectionDao sectionDao;
    private StationService stationService;

    public LineService(LineDao lineDao, SectionDao sectionDao, StationService stationService) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.stationService = stationService;
    }

    public LineResponse saveLine(String name, String color, int extraFare, Long upStationId, Long downStationId, int distance) {
        Line persistLine = lineDao.insert(new Line(name, color, extraFare));
        persistLine.addSection(addInitSection(persistLine, upStationId, downStationId, distance));
        return LineResponse.of(persistLine);
    }

    private Section addInitSection(Line line, Long upStationId, Long downStationId, int distance) {
        if (upStationId != null && downStationId != null) {
            Station upStation = stationService.findStationById(upStationId);
            Station downStation = stationService.findStationById(downStationId);
            Section section = new Section(upStation, downStation, distance);
            return sectionDao.insert(line, section);
        }
        return null;
    }

    public List<LineResponse> findLineResponses() {
        List<Line> persistLines = findLines();
        return persistLines.stream()
                .map(line -> LineResponse.of(line))
                .collect(Collectors.toList());
    }

    public List<Line> findLines() {
        return lineDao.findAll();
    }

    public LineResponse findLineResponseById(Long id) {
        Line persistLine = findLineById(id);
        return LineResponse.of(persistLine);
    }

    public Line findLineById(Long id) {
        return lineDao.findById(id);
    }

    public void updateLine(Long id, String name, String color) {
        lineDao.update(new Line(id, name, color));
    }

    public void deleteLineById(Long id) {
        lineDao.deleteById(id);
    }

    public void addLineStation(Long lineId, Long upStationId, Long downStationId, int distance) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(upStationId);
        Station downStation = stationService.findStationById(downStationId);
        line.addSection(upStation, downStation, distance);

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findStationById(stationId);
        line.removeSection(station);

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
    }

}
