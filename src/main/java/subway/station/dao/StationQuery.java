package subway.station.dao;

public enum StationQuery {
    FIND_ALL("select * from STATION"),
    FIND_BY_ID("select * from STATION where id = ?"),
    DELETE("delete from STATION where id = ?");

    private String query;

    StationQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
