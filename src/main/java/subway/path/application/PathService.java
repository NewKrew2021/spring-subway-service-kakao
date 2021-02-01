package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.member.domain.LoginMember;
import subway.path.domain.Fare;
import subway.path.domain.Graph;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.domain.Station;

import java.util.Optional;

@Service
public class PathService {
    final LineDao lineDao;

    public PathService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public PathResponse getShortestPathOfStations(Station source, Station target, Optional<LoginMember> loginMemberOptional) {
        Graph graph = new Graph(lineDao.findAll());
        Path path = graph.getPath(source, target);

        int fare = Fare.calculate(path.getDistance(), path.getExtraFaresInEdges());
        if(loginMemberOptional.isPresent()) {
            fare = Fare.discountByAge(fare, loginMemberOptional.get().getAge());
        }

        return PathResponse.of(path, fare);
    }
}

