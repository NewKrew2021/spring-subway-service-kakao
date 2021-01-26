package subway.path.ui;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.DistanceExtraFare;
import subway.path.domain.SubwayPathGraph;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(SectionDao sectionDao, StationDao stationDao, LineDao lineDao) {
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse getShortestpathResponse(Long source, Long target, int age) {
        SubwayPathGraph subwayPathGraph = new SubwayPathGraph(
                new Lines(lineDao.findAll()), source, target);

        List<StationResponse> shortestStations = subwayPathGraph.getVertexList()
                .stream()
                .map((String stationId) -> stationDao.findById(Long.parseLong(stationId)))
                .map((Station station) -> new StationResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        int totalFare = DistanceExtraFare.getTotalFare(subwayPathGraph.getTotalDistance())
                + getExtraFareByLines(subwayPathGraph.getLineIdsInShortestPath());
        totalFare = getAgeDiscountTotalFare(totalFare, age);

        return new PathResponse(shortestStations,
                subwayPathGraph.getTotalDistance(), totalFare);
    }


    private int getExtraFareByLines(List<Long> lineIds){
        int maxExtratotalExtra = 0;

        for(Long lineId : lineIds){
            maxExtratotalExtra = Math.max(lineDao.findById(lineId).getExtraFare(), maxExtratotalExtra);
        }

        return maxExtratotalExtra;
    }

    private int getAgeDiscountTotalFare(int totalFare, int age){
        if(13 <= age && age <= 18){
            int discountAmount = (totalFare - 350) / 100 * 20;
            totalFare -= discountAmount;
        }
        if(6 <= age && age <= 12){
            int discountAmount = (totalFare - 350) / 100 * 50;
            totalFare -= discountAmount;
        }
        return totalFare;
    }
}
