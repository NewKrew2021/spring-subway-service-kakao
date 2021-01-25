package subway.favorite.domain;

public class Favorite {

    private Long id;
    private Long source;
    private Long target;

    public Favorite(Long source, Long target) {
        this.source = source;
        this.target = target;
    }

    public Favorite(Long id, Long source, Long target) {
        this(source,target);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }
}
