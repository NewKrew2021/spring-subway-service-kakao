package subway.path.domain.fare.strategy;

public class LineFare implements FareStrategy {
    private final int lineExtraFare;

    public LineFare(int lineExtraFare) {
        this.lineExtraFare = lineExtraFare;
    }

    @Override
    public int getExtraFare() {
        return lineExtraFare;
    }
}
