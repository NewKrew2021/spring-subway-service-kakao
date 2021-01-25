package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        Map<String, Object> params = new HashMap();
        params.put("id", favorite.getId());
        params.put("source", favorite.getSource().getId());
        params.put("target", favorite.getTarget().getId());
        params.put("user_id", favorite.getUserId());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Favorite(id, favorite.getSource(), favorite.getTarget(),favorite.getUserId());
    }

    public void delete(Long id){
        String sql = "delete from favorite where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Favorite> findAllByUserId(Long userId){
        String sql = "select F.id as favorite_id, F.user_id as user_id, " +
                "SOURCE_STATION.id as source_id, SOURCE_STATION.name as source_name, " +
                "DESTINATION_STATION.id as target_id, DESTINATION_STATION.name as target_name " +
                "from FAVORITE F \n" +
                "left outer join STATION SOURCE_STATION on F.source = SOURCE_STATION.id " +
                "left outer join STATION DESTINATION_STATION on F.target = DESTINATION_STATION.id " +
                "WHERE F.user_id = ?";

        return jdbcTemplate.queryForList(sql, new Object[]{userId})
                .stream()
                .collect(Collectors.groupingBy(it -> it.get("favorite_id")))
                .entrySet()
                .stream()
                .map(it -> {
                    Favorite favorite = new Favorite(
                            (Long) it.getValue().get(0).get("favorite_id"),
                            new Station((Long)it.getValue().get(0).get("source_id"), (String) it.getValue().get(0).get("source_name")),
                            new Station((Long)it.getValue().get(0).get("target_id"), (String) it.getValue().get(0).get("target_name")),
                            (Long) it.getValue().get(0).get("user_id")
                    );
                    return favorite;
                })
                .collect(Collectors.toList());
    }
}
