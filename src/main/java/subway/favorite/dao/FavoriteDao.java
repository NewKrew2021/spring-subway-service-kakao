package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.Member;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteDao {
    public static final String FIND_FAVORITE_BY_MEMBER_ID = "select F.id as id, " +
            "S.id as source_id, S.name as source_name, " +
            "T.id as target_id, T.name as target_name, " +
            "M.id as member_id, M.email as member_email, M.password as member_password, M.age as member_age " +
            "from FAVORITE F " +
            "left outer join STATION S on F.source_station_id = S.id " +
            "left outer join STATION T on F.target_station_id = T.id " +
            "left outer join MEMBER M on F.member_id = M.id " +
            "where F.member_id = ?";

    public static final String DELETE_FAVORITE = "delete from favorite where id = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("FAVORITE")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Favorite> favoriteRowMapper = (rs, rowNum) -> {
        Favorite favorite = new Favorite(rs.getLong("id"),
                new Member(rs.getLong("member_id"),
                        rs.getString("member_email"),
                        rs.getString("member_password"),
                        rs.getInt("member_age")),
                new Station(rs.getLong("source_id"), rs.getString("source_name")),
                new Station(rs.getLong("target_id"), rs.getString("target_name")));
        return favorite;
    };

    public void insert(Long memberId, Long sourceId, Long targetId){
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("source_station_id", sourceId);
        params.put("target_station_id", targetId);

        insertAction.execute(params);
    }

    public List<Favorite> findByUser(Long memberId){
        return jdbcTemplate.query(FIND_FAVORITE_BY_MEMBER_ID, favoriteRowMapper, memberId);
    }

    public void deleteFavorite(Long id) {
        jdbcTemplate.update(DELETE_FAVORITE, id);
    }
}
