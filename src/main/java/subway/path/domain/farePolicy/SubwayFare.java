package subway.path.domain.farePolicy;

public class SubwayFare extends BasicFare {

    public static final int INITIAL_SUBWAY_PAYMENT = 1250;

    @Override
    public int getFare() {
        return INITIAL_SUBWAY_PAYMENT;
    }

}
