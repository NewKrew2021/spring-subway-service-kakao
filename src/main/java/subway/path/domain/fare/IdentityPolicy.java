package subway.path.domain.fare;

public class IdentityPolicy implements FarePolicy {

    @Override
    public int apply(int fare) {
        return fare;
    }
}
