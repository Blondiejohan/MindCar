package mindcar.testing.objects;

/**
 * Created by Johan
 */


/**
 * This class takes information from the Raw data and creates a pattern with low and high
 * thresholds of each wave.
 */
public class FindPattern {

    public double[] pattern;



    /**
     * Constructor for the FindPattern Class
     * sets both high and low values to the constuctor value
     *
     * @param wave
     */
    public FindPattern(Eeg wave) {
        this.pattern[0] = wave.delta;
        this.pattern[1] = wave.theta;
        this.pattern[2] = wave.lowAlpha;
        this.pattern[3] = wave.highAlpha;
        this.pattern[4] = wave.lowBeta;
        this.pattern[5] = wave.highBeta;
        this.pattern[6] = wave.lowGamma;
        this.pattern[7] = wave.highGamma;
    }


    /**
     * This method updates a value, high or low. Depending if the new value is higher or lower.
     * This creates a span resulting in a pattern.
     *
     * @param eeg
     */
    public void updateProfile(Eeg eeg) {
        //delta
        this.pattern[0] = this.pattern[0]+eeg.delta;

        //theta
        this.pattern[1] = this.pattern[1]+eeg.theta;

        //lowAlpha
        this.pattern[2] = this.pattern[2]+eeg.lowAlpha;

        //highAlpha
        this.pattern[3] = this.pattern[3]+eeg.highAlpha;

        //lowBeta
        this.pattern[4] = this.pattern[4]+eeg.lowBeta;

        //highBeta
        this.pattern[5] = this.pattern[5]+eeg.highBeta;

        //lowGamma
        this.pattern[6] = this.pattern[6]+eeg.lowGamma;

        //midgamma
        this.pattern[7] = this.pattern[7]+eeg.highGamma;
    }


    /**
     * This method returns a string with all the values with a char in front of it.
     * This so it can fit easily inside a database.
     * When getting string from the database follow theese instruction:
     * Numbers following after a character is the number its attached to.
     * <p>
     * a = Delta
     * b = Theta
     * e = lowAlpha
     * g = highAlpha
     * i = lowBeta
     * k = highBeta
     * m = lowGamma
     * o = midGamma
     *
     * @return
     */
    public double[] getPattern(int amount) {
        //delta
        this.pattern[0] = this.pattern[0]/amount;

        //theta
        this.pattern[1] = this.pattern[1]/amount;

        //lowAlpha
        this.pattern[2] = this.pattern[2]/amount;

        //highAlpha
        this.pattern[3] = this.pattern[3]/amount;

        //lowBeta
        this.pattern[4] = this.pattern[4]/amount;

        //highBeta
        this.pattern[5] = this.pattern[5]/amount;

        //lowGamma
        this.pattern[6] = this.pattern[6]/amount;

        //midgamma
        this.pattern[7] = this.pattern[7]/amount;

        return pattern;
    }

    public String getString(int amount) {
        return "a"+pattern[0]/amount+"b"+pattern[1]/amount+"c"+pattern[2]/amount+"d"+pattern[3]/amount
                +"e"+pattern[4]/amount+"f"+pattern[5]/amount+"g"+pattern[5]/amount
                +"h"+pattern[6]/amount+"i"+pattern[7]/amount+"j";
    }
}
