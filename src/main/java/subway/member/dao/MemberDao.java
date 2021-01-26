package subway.member.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.member.domain.Member;

import javax.sql.DataSource;

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
        jdbcTemplate.update(MemberQuery.UPDATE.getQuery(),
                new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(MemberQuery.DELETE_BY_ID.getQuery(), id);
    }

    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(MemberQuery.FIND_BY_ID.getQuery(), rowMapper, id);
    }

    public Member findByEmail(String email) {
        return jdbcTemplate.queryForObject(MemberQuery.FIND_BY_EMAIL.getQuery(), rowMapper, email);
    }
}
