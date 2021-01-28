package subway.member.domain;

public enum AGE {
    BABY(0, 6, 0),
    KID(0.5, 13, 350),
    ADOLESCENT(0.8, 19, 350),
    ADULT(1.0, Integer.MAX_VALUE, 0);

    private final double saleRate;
    private final int thresholdAge;
    private final int deduction;

    AGE(double saleRate, int thresholdAge, int deduction){
        this.saleRate = saleRate;
        this.thresholdAge = thresholdAge;
        this.deduction = deduction;
    }

    public static AGE getAgeStatus(int age){
        if(age < BABY.thresholdAge){
            return BABY;
        }
        if(age < KID.thresholdAge){
            return KID;
        }
        if(age < ADOLESCENT.thresholdAge){
            return ADOLESCENT;
        }
        return ADULT;
    }

    public double getSaleRate() {
        return saleRate;
    }

    public int getDeduction() {
        return deduction;
    }
}
