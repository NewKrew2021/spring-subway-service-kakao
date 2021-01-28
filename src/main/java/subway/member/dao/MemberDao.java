package subway.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.member.domain.Member;

import javax.sql.DataSource;
import java.lang.annotation.Native;
import java.util.Optional;

@Repository
public class MemberDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Member> rowMapper = (rs, rowNum) ->
            new Member(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("age")
            );


    public MemberDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member insert(Member member) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Member(id, member.getEmail(), member.getPassword(), member.getAge());
    }

    public void update(Member member) {
        String sql = MemberDaoQuery.UPDATE_QUERY;
        jdbcTemplate.update(sql, new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
    }

    public void deleteById(Long id) {
        String sql = MemberDaoQuery.DELETE_BY_ID_QUERY;
        jdbcTemplate.update(sql, id);
    }

    public Member findById(Long id) {
        String sql = MemberDaoQuery.FIND_BY_ID_QUERY;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Member> findByEmail(String email) {
        String sql = MemberDaoQuery.FIND_BY_EMAIL_QUERY;
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, email));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }



}
