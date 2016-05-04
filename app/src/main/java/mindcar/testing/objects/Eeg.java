package mindcar.testing.objects;

/**
 * Object storing values from a Neurosky Mindwave Mobile
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/2/16.
 */
public class Eeg {
    private int attention;
    private int meditation;
    private int blink;
    public int delta;
    public int theta;
    public int lowAlpha;
    public int highAlpha;
    public int lowBeta;
    public int highBeta;
    public int lowGamma;
    public int highGamma;
    //TODO - integrate patterns?

    /**
     * Constructs a basic Eeg with all of the values set to zero (0)
     */
    public Eeg() {
        attention = 0;
        meditation = 0;
        blink = 0;
    }

    /**
     * @return the attention value
     */
    public int getAttention() {
        return attention;
    }

    /**
     * @return the meditation value
     */
    public int getMeditation() {
        return meditation;
    }

    /**
     * @return the blink value
     */
    public int getBlink() {
        return blink;
    }

    /**
     * Set the attention value of the instance
     *
     * @param attention
     */
    public void setAttention(int attention) {
        this.attention = attention;
    }

    /**
     * Set the meditation value of the instance
     *
     * @param meditation
     */
    public void setMeditation(int meditation) {
        this.meditation = meditation;
    }

    /**
     * Set the blink value of the instance
     *
     * @param blink
     */
    public void setBlink(int blink) {
        this.blink = blink;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }

    public void setLowAlpha(int lowAlpha) {
        this.lowAlpha = lowAlpha;
    }

    public void setHighAlpha(int highAlpha) {
        this.highAlpha = highAlpha;
    }

    public void setLowBeta(int lowBeta) {
        this.lowBeta = lowBeta;
    }

    public void setHighBeta(int highBeta) {
        this.highBeta = highBeta;
    }

    public void setLowGamma(int lowGamma) {
        this.lowGamma = lowGamma;
    }

    public void setHighGamma(int highGamma) {
        this.highGamma = highGamma;
    }


    /**
     * @return true if every eeg value is assignd its correct frequenzy span
     */
    public boolean isFull() {
        if (delta >= 0 && delta <= 3 && theta >= 4 && theta <= 7 && lowAlpha >= 8 && lowAlpha <= 9
                && highAlpha >= 10 && highAlpha <= 12 && lowBeta >= 13 && lowBeta <= 17
                && highBeta >= 18 && highBeta <= 30 && lowGamma >= 31 && lowGamma <= 40
                && highGamma >= 41 && highGamma <= 50) {
            return true;
        } else {
            return false;
        }
    }
}