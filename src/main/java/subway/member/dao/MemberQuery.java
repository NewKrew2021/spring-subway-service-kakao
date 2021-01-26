package subway.member.dao;

public enum MemberQuery {
    FIND_BY_EMAIL("select * from MEMBER where email = ?"),
    FIND_BY_ID("select * from MEMBER where id = ?"),
    DELETE_BY_ID("delete from MEMBER where id = ?"),
    UPDATE("update MEMBER set email = ?, password = ?, age = ? where id = ?");

    private String query;

    MemberQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
