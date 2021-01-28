package subway.station.dao;

public class StationDaoQuery {
    public static final String FIND_ALL_QUERY = "select s.id, s.name from STATION s";
    public static final String DELETE_BY_ID_QUERY= "delete from STATION where id = ?";
    public static final String FIND_BY_ID_QUERY = "select s.id, s.name from STATION s where id = ?";
}
