package subway.favorite.domain;

public class Favorite {

    private Long id;
    private long userId;
    private long sourceId;
    private long targetId;

    public Favorite(Long id, long userId, long sourceId, long targetId) {
        this.id = id;
        this.userId = userId;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public Favorite(long userId, long sourceId, long targetId) {
        this(null, userId, sourceId, targetId);
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getTargetId() {
        return targetId;
    }
}
