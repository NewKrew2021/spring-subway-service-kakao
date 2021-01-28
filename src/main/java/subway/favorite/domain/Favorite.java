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

    public Favorite(long id, long memberId, long sourceStationId, long targetStationId) {
        this.id = id;
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public long getId() {
        return id;
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
