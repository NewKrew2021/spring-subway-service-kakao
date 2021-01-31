package subway.path.domain;

public class FareByAge extends Fare{
    private static final int KID_LOWER_BOUND = 6;
    private static final int KID_UPPER_BOUND = 13;
    private static final int TEEN_UPPER_BOUND = 19;

    public FareByAge(int fare, Integer age) {
        super(fare);
        discount(age);
    }

    private void discount(Integer age){
        if(age == null)
            return ;

        if(age < KID_LOWER_BOUND){
            fare = 0;
        }

        if(age >=KID_LOWER_BOUND && age < KID_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.5) + 350;
        }

        if(age >= KID_UPPER_BOUND && age < TEEN_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.8) + 350;
        }
    }
}
