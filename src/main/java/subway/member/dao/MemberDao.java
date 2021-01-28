package subway.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exception.InvalidMemberException;
import subway.member.domain.Member;

import javax.sql.DataSource;

@Repository
public class MemberDao {
    private static final String UPDATE_MEMBER_SET_EMAIL_PASSWORD_AGE_WHERE_ID = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    private static final String DELETE_FROM_MEMBER_WHERE_ID = "delete from MEMBER where id = ?";
    private static final String SELECT_FROM_MEMBER_WHERE_ID = "select * from MEMBER where id = ?";
    private static final String SELECT_FROM_MEMBER_WHERE_EMAIL = "select * from MEMBER where email = ?";

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
        jdbcTemplate.update(UPDATE_MEMBER_SET_EMAIL_PASSWORD_AGE_WHERE_ID, new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_FROM_MEMBER_WHERE_ID, id);
    }

    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_MEMBER_WHERE_ID, rowMapper, id);
    }

    public Member findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_MEMBER_WHERE_EMAIL, rowMapper, email);
        } catch (EmptyResultDataAccessException e){
            throw new InvalidMemberException();
        }
    }

}
