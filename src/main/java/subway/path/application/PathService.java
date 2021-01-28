package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.SectionsInAllLine;
import subway.path.domain.Path;
import subway.path.domain.PathGraph;

@Service
public class PathService {

    private LineService lineService;

    public PathService(LineService lineService) {
        this.lineService = lineService;
    }

    public Path getShortestPath(Long source, Long target) {
        SectionsInAllLine sections = SectionsInAllLine.of(lineService.findLines());
        return new PathGraph(sections).getPath(sections.findStation(source), sections.findStation(target));
    }

}
