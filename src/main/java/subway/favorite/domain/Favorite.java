package subway.favorite.domain;

public class Favorite {
    private final long id;
    private final long memberId;
    private final long sourceStationId;
    private final long targetStationId;

    public Favorite(long memberId, long sourceStationId, long targetStationId) {
        this.id = 0L;
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getSourceStationId() {
        return sourceStationId;
    }

    public long getTargetStationId() {
        return targetStationId;
    }
}
