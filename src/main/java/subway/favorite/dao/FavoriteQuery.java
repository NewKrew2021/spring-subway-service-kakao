package subway.favorite.dao;

public enum FavoriteQuery {
    FIND_ALL_BY_USER_ID("select F.id as favorite_id, F.user_id as user_id, " +
            "SOURCE_STATION.id as source_id, SOURCE_STATION.name as source_name, " +
            "DESTINATION_STATION.id as target_id, DESTINATION_STATION.name as target_name " +
            "from FAVORITE F " +
            "left outer join STATION SOURCE_STATION on F.source = SOURCE_STATION.id " +
            "left outer join STATION DESTINATION_STATION on F.target = DESTINATION_STATION.id " +
            "WHERE F.user_id = ?"),
    DELETE("delete from favorite where id = ?");

    private String query;

    FavoriteQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
