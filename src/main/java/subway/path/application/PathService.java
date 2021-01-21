package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.path.domain.Path;
import subway.path.dto.PathDto;

@Service
public class PathService {

    private LineService lineService;

    public PathService(LineService lineService) {
        this.lineService = lineService;
    }

    public PathDto findPath(long sourceId, long destId) {
        Path path = new Path(sourceId, destId, lineService.findAllSections());
        return new PathDto(path.getStations(), path.getDistance(), path.getFare());
    }
}
