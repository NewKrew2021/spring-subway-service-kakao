package subway.favorite.dao;

public class FavoriteDaoQuery {
    public static final String FIND_ALL_QUERY = "select f.id, f.member_id, f.source_station_id, f.target_station_id from favorite f where member_id = ?";
    public static final String DELETE_BY_ID_QUERY = "delete from favorite where id = ?";
    public static final String FIND_BY_ID_QUERY = "select f.id, f.member_id, f.source_station_id, f.target_station_id from favorite f where id = ?";
}
