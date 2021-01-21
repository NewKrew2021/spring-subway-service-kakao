package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;

@Service
public class PathService {
    private SectionDao sectionDao;

    public PathService(SectionDao sectionDao) {
        this.sectionDao = sectionDao;
    }

    public PathResponse findShortestPathResponse(Long source, Long target) {
        Sections sections = sectionDao.findAll();
        Path shortestPath = sections.getShortestPath(source, target);
        return PathResponse.of(shortestPath);
    }
}