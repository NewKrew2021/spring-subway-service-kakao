package subway.line.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.dto.LineRequest;
import subway.line.dto.LineResponse;
import subway.line.dto.SectionRequest;
import subway.path.service.PathService;
import subway.station.domain.Station;
import subway.station.service.StationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {
    private LineDao lineDao;
    private SectionDao sectionDao;
    private StationService stationService;

    @Autowired
    public LineService(LineDao lineDao, SectionDao sectionDao, StationService stationService) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.stationService = stationService;
    }

    public LineResponse saveLine(LineRequest request) {
        Line persistLine = insertAndSet(request);
        persistLine.addSection(addInitSection(persistLine, request));
        return LineResponse.of(persistLine);
    }

    private Section addInitSection(Line line, LineRequest request) {
        if (request.getUpStationId() != null && request.getDownStationId() != null) {
            Station upStation = stationService.findStationById(request.getUpStationId());
            Station downStation = stationService.findStationById(request.getDownStationId());
            Section section = new Section(upStation, downStation, request.getDistance());
            return insertSectionAndSet(line, section);
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

    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        lineDao.update(new Line(id, lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    public void deleteLineById(Long id) {
        deleteLineAndSet(id);
    }

    public void addLineStation(Long lineId, SectionRequest request) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());
        line.addSection(upStation, downStation, request.getDistance());

        deleteSectionsAndSet(lineId);
        insertSectionsAndSet(line);
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findStationById(stationId);
        line.removeSection(station);

        deleteSectionsAndSet(lineId);
        insertSectionsAndSet(line);
    }

    private void deleteLineAndSet(Long id) {
        lineDao.deleteById(id);
        PathService.newlyUpdated();
    }

    private Line insertAndSet(LineRequest request) {
        Line newLine = lineDao.insert(new Line(request.getName(), request.getColor(), request.getExtraFare()));
        PathService.newlyUpdated();
        return newLine;
    }

    private Section insertSectionAndSet(Line line, Section section) {
        Section newSection = sectionDao.insert(line, section);
        PathService.newlyUpdated();
        return newSection;
    }

    private void deleteSectionsAndSet(Long lineId) {
        sectionDao.deleteByLineId(lineId);
        PathService.newlyUpdated();
    }

    private void insertSectionsAndSet(Line line) {
        sectionDao.insertSections(line);
        PathService.newlyUpdated();
    }
}
