package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Lines;
import subway.path.domain.DistanceExtraFare;
import subway.path.domain.SubwayPathGraph;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse getShortestpathResponse(Long source, Long target, int age) {
        SubwayPathGraph subwayShortestPathGraph = new SubwayPathGraph(new Lines(lineDao.findAll()), source, target);
        List<StationResponse> shortestPathStations = getShortestPathStationResponses(subwayShortestPathGraph);
        int totalDistance = subwayShortestPathGraph.getTotalDistance();
        int totalFare = getPathTotalFare(subwayShortestPathGraph, age);

        return new PathResponse(shortestPathStations, totalDistance, totalFare);
    }

    private List<StationResponse> getShortestPathStationResponses(SubwayPathGraph subwayPathGraph){
        return subwayPathGraph.getShortestPathStationIds().stream()
                .map(stationDao::findById)
                .map(station -> StationResponse.of(station))
                .collect(Collectors.toList());
    }

    private int getPathTotalFare(SubwayPathGraph subwayPathGraph, int age){
        int totalFare = DistanceExtraFare.getTotalFare(subwayPathGraph.getTotalDistance())
                + getExtraFareByLines(subwayPathGraph.getLineIdsInShortestPath());
        totalFare = getAgeDiscountTotalFare(totalFare, age);
        return totalFare;
    }


    private int getExtraFareByLines(List<Long> lineIds) {
        int maxExtratotalExtra = 0;

        for (Long lineId : lineIds) {
            maxExtratotalExtra = Math.max(lineDao.findById(lineId).getExtraFare(), maxExtratotalExtra);
        }

        return maxExtratotalExtra;
    }

    private int getAgeDiscountTotalFare(int totalFare, int age) {
        if (13 <= age && age <= 18) {
            int discountAmount = (totalFare - 350) / 100 * 20;
            totalFare -= discountAmount;
        }
        if (6 <= age && age <= 12) {
            int discountAmount = (totalFare - 350) / 100 * 50;
            totalFare -= discountAmount;
        }
        return totalFare;
    }
}
