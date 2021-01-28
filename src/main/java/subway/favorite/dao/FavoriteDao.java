package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    private RowMapper<Favorite> favoriteRowMapper = (result, rowNum) -> {
        Station sourceStation = new Station(
                result.getLong("source_station_id"),
                result.getString("source_station_name")
        );

        Station targetStation = new Station(
                result.getLong("target_station_id"),
                result.getString("target_station_name")
        );

        LoginMember member = new LoginMember(
                result.getLong("member_id"),
                result.getString("member_email"),
                result.getInt("member_age")
        );

        return new Favorite(
                member,
                sourceStation,
                targetStation
        );
    };

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("FAVORITE")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", favorite.getId());
        params.put("member_id", favorite.getMember().getId());
        params.put("source_station_id", favorite.getSourceStation().getId());
        params.put("target_station_id", favorite.getTargetStation().getId());

        Long favoriteId = insertAction.executeAndReturnKey(params).longValue();

        return new Favorite(
                favoriteId,
                favorite.getMember(),
                favorite.getSourceStation(),
                favorite.getTargetStation()
        );
    }

    public List<Favorite> findByMemberId(Long memberId) {
        String sql = "select F.id as favorite_id," +
                "SST.id as source_station_id, SST.name as source_station_name, " +
                "TST.id as target_station_id, TST.name as target_station_name," +
                "M.id as member_id, M.email as member_email, M.age as member_age " +
                "from FAVORITE F \n" +
                "left outer join STATION SST on F.source_station_id = SST.id " +
                "left outer join STATION TST on F.target_station_id = TST.id " +
                "left outer join MEMBER M on F.member_id = M.id " +
                "WHERE F.member_id = ?";

        return jdbcTemplate.query(sql, favoriteRowMapper, memberId);
    }


    public void deleteByIdMemberAndId(Long id, Long memberId) {
        String sql = "delete from FAVORITE where id = ? and member_id = ?";
        jdbcTemplate.update(sql, id, memberId);
    }
}
