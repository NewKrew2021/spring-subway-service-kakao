package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareCalculator;
import subway.path.domain.path.SubwayPath;
import subway.path.domain.path.PathValue;
import subway.path.domain.path.SubwayGraph;
import subway.station.application.StationService;

@Service
public class PathService {

    private final LineService lineService;
    private final StationService stationService;
    private final FareCalculator fareCalculator;

    public PathService(LineService lineService, StationService stationService, FareCalculator fareCalculator) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.fareCalculator = fareCalculator;
    }

    public PathValue findPath(long source, long target, LoginMember loginMember) {
        SubwayGraph graph = SubwayGraph.from(lineService.findLines());
        SubwayPath subwayPath = graph.getPath(stationService.findStationById(source), stationService.findStationById(target));

        int fare = fareCalculator.getFare(subwayPath.getDistance(), subwayPath.getLines(), loginMember);
        return new PathValue(subwayPath.getStations(), subwayPath.getDistance(), fare);
    }
}
