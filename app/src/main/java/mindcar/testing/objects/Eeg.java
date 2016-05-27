package mindcar.testing.objects;

import mindcar.testing.util.DatabaseAccess;

/**
 * Object storing values from a Neurosky Mindwave Mobile
 * Created by Mattias Landkvist & Nikolaos-Machairiotis Sasopoulos on 3/2/16.
 */
public class Eeg {
    private int attention;
    private int meditation;
    private int blink;
    private int delta;
    private int theta;
    private int lowAlpha;
    private int highAlpha;
    private int lowBeta;
    private int highBeta;
    private int lowGamma;
    private int highGamma;
    private double[] arr;
    //TODO - integrate patterns?

    /**
     * Constructs a basic Eeg with all of the values set to zero (0)
     */
    public Eeg() {
        attention = 0;
        meditation = 0;
        blink = 0;
//        delta = 0;
//        theta = 0;
//        lowAlpha = 0;
//        highAlpha = 0;
//        lowBeta = 0;
//        highBeta = 0;
//        lowGamma = 0;
//        highGamma = 0;
    }

    /**
     * @return the attention value
     */
    public int getAttention() {
        return attention;
    }

    public double[] getArray() {
        return arr;
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

    public void setHighBeta(int highBeta) { this.highBeta = highBeta; }

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
        if (delta >= 1 && delta <= 3 && theta >= 4 && theta <= 7 && lowAlpha >= 8 && lowAlpha <= 9
                && highAlpha >= 10 && highAlpha <= 12 && lowBeta >= 13 && lowBeta <= 17
                && highBeta >= 18 && highBeta <= 30 && lowGamma >= 31 && lowGamma <= 40
                && highGamma >= 41 && highGamma <= 50) {
            return true;
        } else {
            return false;
        }
    }


    public double[] toDoubleArray(){
        return new double[]{Double.valueOf(delta), Double.valueOf(theta), Double.valueOf(lowAlpha),
                    Double.valueOf(highAlpha), Double.valueOf(lowBeta), Double.valueOf(highBeta),
                    Double.valueOf(lowGamma), Double.valueOf(highGamma)};
    }

    public void populate(Eeg eeg){
        if (delta == 0){
            this.delta = eeg.getDelta();
        }
        if (theta == 0){
            this.theta = eeg.getTheta();
        }
        if (lowAlpha == 0){
            this.lowAlpha = eeg.getLowAlpha();
        }
        if (highAlpha == 0){
            this.highAlpha = eeg.getHighAlpha();
        }
        if (lowBeta == 0){
            this.lowBeta = eeg.getLowBeta();
        }
        if (highBeta == 0){
            this.highBeta = eeg.getHighBeta();
        }
        if(lowGamma == 0){
            this.lowGamma = eeg.getLowGamma();
        }
        if (highGamma == 0) {
            this.highGamma = eeg.getHighGamma();
        }
    }

    public int getDelta() {
        return delta;
    }

    public int getTheta() {
        return theta;
    }

    public int getLowAlpha() {
        return lowAlpha;
    }

    public int getHighAlpha() {
        return highAlpha;
    }

    public int getLowBeta() {
        return lowBeta;
    }

    public int getHighBeta() {
        return highBeta;
    }

    public int getLowGamma() {
        return lowGamma;
    }

    public int getHighGamma() {
        return highGamma;
    }
}