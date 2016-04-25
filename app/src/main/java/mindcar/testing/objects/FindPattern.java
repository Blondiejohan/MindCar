package mindcar.testing.objects;

import com.neurosky.thinkgear.TGEegPower;

/**
 * Created by Johan
 */


/**
 * This class takes information from the Raw data and creates a pattern with low and high
 * thresholds of each wave.
 */
public class FindPattern {

    int bottomDelta;
    int bottomTheta;
    int bottomLowAlpha;
    int bottomHighAlpha;
    int bottomLowBeta;
    int bottomHighBeta;
    int bottomLowGamma;
    int bottomMidGamma;
    int topDelta;
    int topTheta;
    int topLowAlpha;
    int topHighAlpha;
    int topLowBeta;
    int topHighBeta;
    int topLowGamma;
    int topMidGamma;


    /**
     * Constructor for the FindPattern Class
     * sets both high and low values to the constuctor value
     *
     * @param wave
     */
    public FindPattern(TGEegPower wave) {
        this.bottomDelta = wave.delta;
        this.topDelta = wave.delta;

        this.bottomTheta = wave.theta;
        this.topTheta = wave.theta;

        this.bottomLowAlpha = wave.lowAlpha;
        this.topLowAlpha = wave.lowAlpha;

        this.bottomHighAlpha = wave.highAlpha;
        this.topHighAlpha = wave.highAlpha;

        this.bottomLowBeta = wave.lowBeta;
        this.topLowBeta = wave.lowBeta;

        this.bottomHighBeta = wave.highBeta;
        this.topHighBeta = wave.highBeta;

        this.bottomLowGamma = wave.lowGamma;
        this.topLowGamma = wave.lowGamma;

        this.bottomMidGamma = wave.midGamma;
        this.topMidGamma = wave.midGamma;
    }


    /**
     * This method updates a value, high or low. Depending if the new value is higher or lower.
     * This creates a span resulting in a pattern.
     *
     * @param wave
     */
    public void updateProfile(TGEegPower wave) {
        //delta
        if (this.bottomDelta > wave.delta) {
            this.bottomDelta = wave.delta;
        }
        if (this.topDelta < wave.delta) {
            this.topDelta = wave.delta;
        }

        //theta
        if (this.bottomTheta > wave.theta) {
            this.bottomTheta = wave.theta;
        }
        if (this.topTheta < wave.theta) {
            this.topTheta = wave.theta;
        }

        //lowAlpha
        if (this.bottomLowAlpha > wave.lowAlpha) {
            this.bottomLowAlpha = wave.lowAlpha;
        }
        if (this.topLowAlpha < wave.lowAlpha) {
            this.topLowAlpha = wave.lowAlpha;
        }

        //highAlpha
        if (this.bottomHighAlpha > wave.highAlpha) {
            this.bottomHighAlpha = wave.highAlpha;
        }
        if (this.topHighAlpha < wave.highAlpha) {
            this.topHighAlpha = wave.highAlpha;
        }

        //lowBeta
        if (this.bottomLowBeta > wave.lowBeta) {
            this.bottomLowBeta = wave.lowBeta;
        }
        if (this.topLowBeta < wave.lowBeta) {
            this.topLowBeta = wave.lowBeta;
        }

        //highBeta
        if (this.bottomHighBeta > wave.highBeta) {
            this.bottomHighBeta = wave.highBeta;
        }
        if (this.topHighBeta < wave.highBeta) {
            this.topHighBeta = wave.highBeta;
        }

        //lowGamma
        if (this.bottomLowGamma > wave.lowGamma) {
            this.bottomLowGamma = wave.lowGamma;
        }
        if (this.topLowGamma < wave.lowGamma) {
            this.topLowGamma = wave.lowGamma;
        }

        //midgamma
        if (this.bottomMidGamma > wave.midGamma) {
            this.bottomMidGamma = wave.midGamma;
        }
        if (this.topMidGamma < wave.midGamma) {
            this.topMidGamma = wave.midGamma;
        }
    }


    /**
     * This method returns a string with all the values with a char in front of it.
     * This so it can fit easily inside a database.
     * When getting string from the database follow theese instruction:
     * Numbers following after a character is the number its attached to.
     * <p>
     * a = low Delsta
     * b = high Delta
     * c = low Theta
     * d = high Theta
     * e = low lowAlpha
     * f = high lowAlpha
     * g = low highAlpha
     * h = high highAlpha
     * i = low lowBeta
     * j = high lowBeta
     * k = low highBeta
     * l = high highBeta
     * m = low lowGamma
     * n = high lowGamma
     * o = low midGamma
     * p = high midGamma
     * q = timestamp of recieving the waves
     *
     * @return
     */
    public String getPattern() {

        String result = "a" + this.bottomDelta + "b" + this.topDelta + "c" + this.bottomTheta + "d" +
                this.topTheta + "e" + this.bottomLowAlpha + "f" + this.topLowAlpha + "g" +
                this.bottomHighAlpha + "h" + this.topHighAlpha + "i" +
                this.bottomLowBeta + "j" + this.topLowBeta + "k" + this.bottomHighBeta + "l" +
                this.topHighBeta + "m" + this.bottomLowGamma + "n" + this.topLowGamma + "o" +
                this.bottomMidGamma + "p" + this.topMidGamma + "q";
        return result;
    }
}
