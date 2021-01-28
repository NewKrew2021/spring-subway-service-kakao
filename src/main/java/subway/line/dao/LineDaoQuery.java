package subway.line.dao;

public class LineDaoQuery {
    public static final String FIND_BY_ID_QUERY = "select L.id as line_id, L.name as line_name, L.color as line_color, L.extra_fare as line_extra_fare," +
            "S.id as section_id, S.distance as section_distance, " +
            "UST.id as up_station_id, UST.name as up_station_name, " +
            "DST.id as down_station_id, DST.name as down_station_name " +
            "from LINE L \n" +
            "left outer join SECTION S on L.id = S.line_id " +
            "left outer join STATION UST on S.up_station_id = UST.id " +
            "left outer join STATION DST on S.down_station_id = DST.id " +
            "WHERE L.id = ?";

    public static final String UPDATE_QUERY = "update LINE set name = ?, color = ? where id = ?";

    public static final String FIND_ALL_QUERY = "select L.id as line_id, L.name as line_name, L.color as line_color, L.extra_fare as line_extra_fare, " +
            "S.id as section_id, S.distance as section_distance, " +
            "UST.id as up_station_id, UST.name as up_station_name, " +
            "DST.id as down_station_id, DST.name as down_station_name " +
            "from LINE L \n" +
            "left outer join SECTION S on L.id = S.line_id " +
            "left outer join STATION UST on S.up_station_id = UST.id " +
            "left outer join STATION DST on S.down_station_id = DST.id ";

    public static final String DELETE_BY_ID_QUERY = "delete from Line where id = ?";
}
