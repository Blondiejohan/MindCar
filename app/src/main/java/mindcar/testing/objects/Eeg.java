package mindcar.testing.objects;

/**
 * Object storing values from a Neurosky Mindwave Mobile
 * Contributors: Mattias Landkvist & Nikolaos-Machairiotis Sasopoulos.
 */
public class Eeg {
    private int delta;
    private int theta;
    private int lowAlpha;
    private int highAlpha;
    private int lowBeta;
    private int highBeta;
    private int lowGamma;
    private int highGamma;

    /**
     * Constructs a basic Eeg
     */
    public Eeg() {}


    /**
     * Setter for delta.
     * Range 1-3
     * @param delta
     */
    public void setDelta(int delta) {
        this.delta = delta;
    }

    /**
     * Setter for theta
     * Range 4-7
     * @param theta
     */
    public void setTheta(int theta) {
        this.theta = theta;
    }

    /**
     * Setter for low alpha values
     * Range 8-9
     * @param lowAlpha
     */
    public void setLowAlpha(int lowAlpha) {
        this.lowAlpha = lowAlpha;
    }

    /**
     * Setter for high alpha values
     * Range 10-12
     * @param highAlpha
     */
    public void setHighAlpha(int highAlpha) {
        this.highAlpha = highAlpha;
    }

    /**
     * Setter for low beta values
     * Range 13-17
     * @param lowBeta
     */
    public void setLowBeta(int lowBeta) {
        this.lowBeta = lowBeta;
    }

    /**
     * Setter for high beta values
     * Range 18-30
     * @param highBeta
     */
    public void setHighBeta(int highBeta) { this.highBeta = highBeta; }

    /**
     * Setter for low gamma values
     * Range 31-40
     * @param lowGamma
     */
    public void setLowGamma(int lowGamma) {
        this.lowGamma = lowGamma;
    }

    /**
     * Setter for high gamma values
     * Range 41-50
     * @param highGamma
     */
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

    /**
     * @return this object as an array of doubles
     */
    public double[] toDoubleArray(){
        return new double[]{Double.valueOf(delta), Double.valueOf(theta), Double.valueOf(lowAlpha),
                    Double.valueOf(highAlpha), Double.valueOf(lowBeta), Double.valueOf(highBeta),
                    Double.valueOf(lowGamma), Double.valueOf(highGamma)};
    }

    /**
     * Populates this Eeg object with another Eeg in places where this Eeg is empty
     * @param eeg
     */
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

    /**
     * @return delta
     */
    public int getDelta() {
        return delta;
    }

    /**
     * @return theta
     */
    public int getTheta() {
        return theta;
    }

    /**
     * @return lowAlpha
     */
    public int getLowAlpha() {
        return lowAlpha;
    }

    /**
     * @return highAlpha
     */
    public int getHighAlpha() {
        return highAlpha;
    }

    /**
     * @return lowBeta
     */
    public int getLowBeta() {
        return lowBeta;
    }

    /**
     * @return highBeta
     */
    public int getHighBeta() {
        return highBeta;
    }

    /**
     * @return lowGamma
     */
    public int getLowGamma() {
        return lowGamma;
    }

    /**
     * @return highGamma
     */
    public int getHighGamma() {
        return highGamma;
    }
}