package subway.favorite.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.favorite.exception.FavoriteNotFoundException;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    private final RowMapper<Favorite> rowMapper = (resultSet, rowNum) ->
            Favorite.of(
                    resultSet.getLong("id"),
                    resultSet.getLong("member_id"),
                    new Station(resultSet.getLong("source_id"), resultSet.getString("source_name")),
                    new Station(resultSet.getLong("target_id"), resultSet.getString("target_name"))
            );

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        Map<String, Object> params = new HashMap();
        params.put("member_id", favorite.getMemberId());
        params.put("source_station_id", favorite.getSource().getId());
        params.put("target_station_id", favorite.getTarget().getId());
        Long favoriteId = insertAction.executeAndReturnKey(params).longValue();
        return Favorite.of(favoriteId, favorite.getMemberId(), favorite.getSource(), favorite.getTarget());
    }

    public List<Favorite> getFavorite(Long memberId) {
        return jdbcTemplate.query("select F.id as id, F.member_id as member_id," +
                " S.id as source_id, S.name as source_name," +
                " T.id as target_id, T.name as target_name " +
                "from Favorite F \n" +
                "left outer join STATION S on F.source_station_id = S.id " +
                "left outer join STATION T on F.target_station_id = T.id " +
                "where F.member_id= ?;", rowMapper, memberId);
    }

    public Optional<Favorite> getFavoriteById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select F.id as id, F.member_id as member_id," +
                    " S.id as source_id, S.name as source_name," +
                    " T.id as target_id, T.name as target_name " +
                    "from Favorite F " +
                    "left outer join STATION S on F.source_station_id = S.id " +
                    "left outer join STATION T on F.target_station_id = T.id " +
                    "where F.id= ?;", rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            throw new FavoriteNotFoundException();
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from FAVORITE where id=?", id);
    }
}
