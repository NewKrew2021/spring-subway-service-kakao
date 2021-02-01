package subway.line.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.dto.LineRequest;
import subway.line.dto.LineResponse;
import subway.line.dto.SectionRequest;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {
    private final LineDao lineDao;
    private final SectionDao sectionDao;
    private final StationService stationService;

    public LineService(LineDao lineDao, SectionDao sectionDao, StationService stationService) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.stationService = stationService;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        Line persistLine = lineDao.insert(Line.of(request.getName(), request.getColor(), Fare.from(request.getExtraFare())));
        persistLine.addSection(addInitSection(persistLine, request));
        return LineResponse.of(persistLine);
    }

    @Transactional
    Section addInitSection(Line line, LineRequest request) {
        if (request.getUpStationId() != null && request.getDownStationId() != null) {
            Station upStation = stationService.findStationById(request.getUpStationId());
            Station downStation = stationService.findStationById(request.getDownStationId());
            Section section = Section.of(upStation, downStation, Distance.from(request.getDistance()));
            return sectionDao.insert(line, section);
        }
        return null;
    }

    public List<LineResponse> findLineResponses() {
        List<Line> persistLines = findLines();
        return persistLines.stream()
                .map(LineResponse::of)
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

    @Transactional
    public void updateLine(Long id, LineRequest request) {
        lineDao.update(Line.of(id, request.getName(), request.getColor(), Fare.from(request.getExtraFare())));
    }

    @Transactional
    public void deleteLineById(Long id) {
        lineDao.deleteById(id);
    }

    @Transactional
    public void addLineStation(Long lineId, SectionRequest request) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());
        line.addSection(upStation, downStation, Distance.from(request.getDistance()));

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
    }

    @Transactional
    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findStationById(stationId);
        line.removeSection(station);

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
    }
}
