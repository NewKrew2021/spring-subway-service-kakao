package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteDao {

    private final static String NAMING_FAVORITE_ID = "favorite_id";
    private final static String NAMING_SOURCE_STATION_ID = "ss_id";
    private final static String NAMING_TARGET_STATION_ID = "ts_id";
    private final static String NAMING_SOURCE_STATION_NAME = "ss_name";
    private final static String NAMING_TARGET_STATION_NAME = "ts_name";
    private final static String SAVE_SQL = "insert into favorite (member_id, source_station_id, target_station_id) values (?, ?, ?)";
    private final static String DELETE_BY_ID_SQL = "delete from FAVORITE where id = ? and member_id = ?";
    private final static String SHOW_BY_MEMBER_ID_SQL = "select F.id as " + NAMING_FAVORITE_ID +", " +
            "F.source_station_id as " + NAMING_SOURCE_STATION_ID + ", SS.name as " + NAMING_SOURCE_STATION_NAME +", " +
            "F.target_station_id as " + NAMING_TARGET_STATION_ID +", TS.name as " + NAMING_TARGET_STATION_NAME +" " +
            "from FAVORITE F \n " +
            "left outer join STATION SS on F.source_station_id = SS.id " +
            "left outer join STATION TS on F.target_station_id = TS.id " +
            "where F.member_id = ?";


    private final JdbcTemplate jdbcTemplate;

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(Long source, Long target, Long memberId) {
        jdbcTemplate.update(SAVE_SQL, memberId, source, target);
    }

    public Favorite showByMemberId(Long id) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(SHOW_BY_MEMBER_ID_SQL, new Object[]{id});
        return result.stream()
                .map(it -> new Favorite(
                        (Long) it.get(NAMING_FAVORITE_ID),
                        new Station((Long) it.get(NAMING_SOURCE_STATION_ID), (String) it.get(NAMING_SOURCE_STATION_NAME)),
                        new Station((Long) it.get(NAMING_TARGET_STATION_ID), (String) it.get(NAMING_TARGET_STATION_NAME))
                )).collect(Collectors.toList()).get(0);
    }

    public void deleteById(Long favoriteId, Long memberId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, favoriteId, memberId);
    }
}
