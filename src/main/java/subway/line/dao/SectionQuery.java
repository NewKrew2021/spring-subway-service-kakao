package subway.line.dao;

public enum SectionQuery {
    FIND_ALL("select S.id as section_id, S.distance as section_distance, " +
            "UST.id as up_station_id, UST.name as up_station_name, " +
            "DST.id as down_station_id, DST.name as down_station_name " +
            "from SECTION S " +
            "left outer join STATION UST on S.up_station_id = UST.id " +
            "left outer join STATION DST on S.down_station_id = DST.id "),
    DELETE_BY_LINE_ID("delete from SECTION where line_id = ?");

    private String query;

    SectionQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
