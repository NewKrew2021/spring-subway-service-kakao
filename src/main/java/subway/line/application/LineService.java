package subway.line.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.*;
import subway.line.dto.LineRequest;
import subway.line.dto.LineResponse;
import subway.line.dto.SectionRequest;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {
    private final LineDao lineDao;
    private final SectionDao sectionDao;
    private final StationService stationService;
    @Autowired
    private SubwayMap map;

    public LineService(LineDao lineDao, SectionDao sectionDao, StationService stationService) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.stationService = stationService;
    }

    public LineResponse saveLine(LineRequest request) {
        Line persistLine = lineDao.insert(new Line(request.getName(), request.getColor(),request.getExtraFare()));
        persistLine.addSection(addInitSection(persistLine, request));
        map.refresh(findLines());
        return LineResponse.of(persistLine);
    }

    private Section addInitSection(Line line, LineRequest request) {
        if (request.getUpStationId() != null && request.getDownStationId() != null) {
            Station upStation = stationService.findStationById(request.getUpStationId());
            Station downStation = stationService.findStationById(request.getDownStationId());
            Section section = new Section(upStation, downStation, request.getDistance());
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

    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        lineDao.update(new Line(id, lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
        map.refresh(findLines());
    }

    public void deleteLineById(Long id) {
        lineDao.deleteById(id);
        map.refresh(findLines());
    }

    public void addLineStation(Long lineId, SectionRequest request) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());
        line.addSection(upStation, downStation, request.getDistance());

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
        map.refresh(findLines());
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findStationById(stationId);
        line.removeSection(station);

        sectionDao.deleteByLineId(lineId);
        sectionDao.insertSections(line);
        map.refresh(findLines());
    }

    public PathResponse getShortestPathWithFare(LoginMember loginMember,
                                                Station sourceStation,
                                                Station targetStation) {
        ResultPath directedSections = map.calculateShortestPath(sourceStation, targetStation);
        return new PathResponse(
                StationResponse.listOf(directedSections.getStations()),
                directedSections.getDistance(),
                PriceCalculator.calculateResult(loginMember, directedSections)
        );
    }
}
