package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", favorite.getId());
        params.put("member_id", favorite.getMember().getId());
        params.put("source_station_id", favorite.getSourceStation().getId());
        params.put("target_station_id", favorite.getTargetStation().getId());

        Long favoriteId =insertAction.executeAndReturnKey(params).longValue();
        return new Favorite(favoriteId, favorite.getMember(), favorite.getSourceStation(), favorite.getTargetStation());
    }

    public List<Favorite> findByMemberId(Long memberId) {
        String sql = "select F.id as favorite_id, " +
                "M.id as member_id, M.email as member_email, M.age as member_age, " +
                "SST.id as source_station_id, SST.name as source_station_name, " +
                "TST.id as target_station_id, TST.name as target_station_name " +
                "from FAVORITE F \n" +
                "left outer join MEMBER M on F.member_id = M.id " +
                "left outer join STATION SST on F.source_station_id = SST.id " +
                "left outer join STATION TST on F.target_station_id = TST.id " +
                "WHERE F.member_id = ?";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, memberId);

        return result.stream()
                .map(it -> mapFavorite(it))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from FAVORITE where id = ?", id);
    }

    private Favorite mapFavorite(Map<String, Object> result) {
        Long id = (Long) result.get("favorite_id");
        LoginMember member = extractLoginMember(result);
        Station sourceStation = extractSourceStation(result);
        Station targetStation = extractTargetStation(result);

        return new Favorite(id, member, sourceStation, targetStation);
    }

    private LoginMember extractLoginMember(Map<String, Object> result) {
        return new LoginMember(
                (Long) result.get("member_id"),
                (String) result.get("member_email"),
                (Integer) result.get("member_age"));
    }

    private Station extractSourceStation(Map<String, Object> result) {
        return new Station(
                (Long) result.get("source_station_id"),
                (String) result.get("source_station_name")
        );
    }

    private Station extractTargetStation(Map<String, Object> result) {
        return new Station(
                (Long) result.get("target_station_id"),
                (String) result.get("target_station_name")
        );
    }
}
