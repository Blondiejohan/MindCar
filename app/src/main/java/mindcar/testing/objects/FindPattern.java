package mindcar.testing.objects;

/**
 * Created by Johan
 */


/**
 * This class takes information from the Raw data and creates a pattern with low and high
 * thresholds of each wave.
 */
public class FindPattern {

    int delta;
    int theta;
    int lowAlpha;
    int highAlpha;
    int lowBeta;
    int highBeta;
    int lowGamma;
    int midGamma;



    /**
     * Constructor for the FindPattern Class
     * sets both high and low values to the constuctor value
     *
     * @param wave
     */
    public FindPattern(Eeg wave) {
        this.delta = wave.delta;
        this.theta = wave.theta;
        this.lowAlpha = wave.lowAlpha;
        this.highAlpha = wave.highAlpha;
        this.lowBeta = wave.lowBeta;
        this.highBeta = wave.highBeta;
        this.lowGamma = wave.lowGamma;
        this.midGamma = wave.highGamma;
    }


    /**
     * This method updates a value, high or low. Depending if the new value is higher or lower.
     * This creates a span resulting in a pattern.
     *
     * @param eeg
     */
    public void updateProfile(Eeg eeg) {
        //delta
        this.delta = this.delta+eeg.delta;

        //theta
        this.theta = this.theta+eeg.theta;

        //lowAlpha
        this.lowAlpha = this.lowAlpha+eeg.lowAlpha;

        //highAlpha
        this.highAlpha = this.highAlpha+eeg.highAlpha;

        //lowBeta
        this.lowBeta = this.lowBeta+eeg.lowBeta;

        //highBeta
        this.highBeta = this.highBeta+eeg.highBeta;

        //lowGamma
        this.lowGamma = this.lowGamma+eeg.lowGamma;

        //midgamma
        this.midGamma = this.midGamma+eeg.highGamma;
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
    public String getPattern(int amount) {

        String result = "Delta:" + this.delta/amount + " Theta:" + this.theta/amount + " LowAlpha:" + this.lowAlpha/amount + " HighAlpha" +
                this.highAlpha/amount + " LowBeta:" + this.lowBeta/amount + " HighBeta:" + this.highBeta/amount + " lowGamma:" +
                this.lowGamma/amount + " MidGamma:" + this.midGamma/amount + " End";
        return result;
    }
}
