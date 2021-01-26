package subway.path.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Lines;
import subway.line.domain.SectionsInAllLine;
import subway.path.domain.Path;
import subway.path.domain.PathGraph;

@Service
public class PathService {

    LineService lineService;

    @Autowired
    public PathService(LineService lineService) {
        this.lineService = lineService;
    }

    public Path getShortestPath(Long source, Long target) {
        SectionsInAllLine sections = new Lines(lineService.findLines()).getSectionsInAllLine();

        PathGraph pathGraph = new PathGraph(sections);
        return pathGraph.getPath(sections.findStation(source), sections.findStation(target));
    }


}
