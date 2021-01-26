package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Lines;
import subway.path.domain.DiscountAge;
import subway.path.domain.DistanceExtraFare;
import subway.path.domain.SubwayPathGraph;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final int EXTRA_FARE_DEFAULT = 0;
    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse getShortestPathResponse(Long source, Long target, int age) {
        SubwayPathGraph subwayPathGraph = new SubwayPathGraph(
                new Lines(lineDao.findAll()), source, target);

        List<StationResponse> shortestStations = subwayPathGraph.getVertexList()
                .stream()
                .map((String stationId) -> stationDao.findById(Long.parseLong(stationId)))
                .map((Station station) -> StationResponse.of(station))
                .collect(Collectors.toList());

        int totalFare = DistanceExtraFare.getTotalFare(subwayPathGraph.getTotalDistance())
                + addExtraFareByLines(subwayPathGraph.getLineIdsInShortestPath());

        totalFare = DiscountAge.getTotalFareToDiscountAge(totalFare,age);

        return new PathResponse(shortestStations,
                subwayPathGraph.getTotalDistance(), totalFare);
    }

    private int addExtraFareByLines(List<Long> lineIds){
        int maxLineExtraFare = EXTRA_FARE_DEFAULT;

        for(Long lineId : lineIds){
            maxLineExtraFare = Math.max(lineDao.findById(lineId).getExtraFare(), maxLineExtraFare);
        }

        return maxLineExtraFare;
    }

}
