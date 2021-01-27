package subway.favorite.domain;

public class Favorite {

    private long id;
    private long memberId;
    private long source;
    private long target;

    public Favorite() {
    }

    public Favorite(long id, long memberId, long source, long target) {
        this(memberId, source, target);
        this.id = id;
    }

    public Favorite(long memberId, long source, long target) {
        this.memberId = memberId;
        this.source = source;
        this.target = target;
    }

    public long getId() {
        return id;
    }

    public long getSource() {
        return source;
    }

    public long getTarget() {
        return target;
    }

    public long getMemberId() {
        return memberId;
    }
}
