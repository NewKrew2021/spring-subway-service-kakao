package subway.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exceptions.NotExistsDataException;
import subway.member.domain.Member;
import subway.member.exceptions.InvalidUserInfoException;

import javax.sql.DataSource;

@Repository
public class MemberDao {
    public static final String NOT_EXISTS_USER_INFO_ERROR_MESSAGE = "회원 정보가 존재하지 않습니다.";
    public static final String INVALID_USER_INFO_ERROR_MESSAGE = "회원 정보가 올바르지 않습니다.";

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
        String sql = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
        int updatedRow = jdbcTemplate.update(sql, new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
        if(updatedRow != 1) {
            throw new NotExistsDataException(NOT_EXISTS_USER_INFO_ERROR_MESSAGE);
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from MEMBER where id = ?";
        int deletedRow = jdbcTemplate.update(sql, id);
        if(deletedRow != 1) {
            throw new NotExistsDataException(NOT_EXISTS_USER_INFO_ERROR_MESSAGE);
        }
    }

    public Member findById(Long id) {
        String sql = "select * from MEMBER where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Member findByEmail(String email) {
        String sql = "select * from MEMBER where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidUserInfoException(INVALID_USER_INFO_ERROR_MESSAGE);
        }
    }
}
