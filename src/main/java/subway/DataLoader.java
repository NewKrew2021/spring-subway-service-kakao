package subway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.dao.MemberDao;
import subway.common.domain.Age;
import subway.member.domain.Member;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {
    private final StationDao stationDao;
    private final LineDao lineDao;
    private final SectionDao sectionDao;
    private final MemberDao memberDao;

    public DataLoader(StationDao stationDao, LineDao lineDao, SectionDao sectionDao, MemberDao memberDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.memberDao = memberDao;
    }

    @Override
    public void run(String... args) throws Exception {
        Station 강남역 = stationDao.insert(Station.from("강남역"));
        Station 판교역 = stationDao.insert(Station.from("판교역"));
        Station 정자역 = stationDao.insert(Station.from("정자역"));
        Station 역삼역 = stationDao.insert(Station.from("역삼역"));
        Station 잠실역 = stationDao.insert(Station.from("잠실역"));

        Line 신분당선 = lineDao.insert(Line.of("신분당선", "red lighten-1", Fare.from(0)));
        신분당선.addSection(Section.of(강남역, 판교역, Distance.from(10)));
        신분당선.addSection(Section.of(판교역, 정자역, Distance.from(10)));
        sectionDao.insertSections(신분당선);

        Line 이호선 = lineDao.insert(Line.of("2호선", "green lighten-1", Fare.from(0)));
        이호선.addSection(Section.of(강남역, 역삼역, Distance.from(10)));
        이호선.addSection(Section.of(역삼역, 잠실역, Distance.from(10)));
        sectionDao.insertSections(이호선);

        Member member = Member.of("email@email.com", "password", Age.from(10));
        memberDao.insert(member);
    }
}

