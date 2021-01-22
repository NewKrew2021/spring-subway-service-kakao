package subway.path.domain.fare;

public interface FarePolicy {

    /**
     * For a given fare, return the fare for which the policy is applied.
     *
     * @param fare
     * @return policy-applied fare
     */
    int apply(int fare);
}
