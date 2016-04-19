package mindcar.testing.objects;

/**
 * Object storing values from a Neurosky Mindwave Mobile
 * Created by Mattias Landkvist & Nikos Sasopoulos & sarah on 3/2/16.
 */
public class Eeg {
    private int attention;
    private int alpha;
    private int gamma;
    private int delta;
    private int beta;
    private int theta;

    /**
     * Constructs a basic EEGObject with all of the values set to zero (0)
     */
    public Eeg(){
        attention = 0;
        alpha = 0;
        delta = 0;
        theta = 0;
        gamma = 0;
        beta = 0;
    }

    /**
     * @return the attention value
     */
    public int getAttention(){
        return attention;
    }


    /**
     * Set the attention value of the instance
     * @param attention
     */
    public void setAttention(int attention){
        this.attention = attention;
    }



    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public void setGamma(int gamma) {
        this.gamma = gamma;
    }
    public int getAlpha(){
        return this.alpha;
    }
    public int getGamma() {
        return this.gamma;
    }
    public int getBeta() {
        return this.beta;
    }
    public int getTheta() {
        return this.theta;
    }
    public int getDelta() {
        return this.delta;
    }

    /**
     * return true if this object equals the values of the incoming object
     * @param e
     * @return
     */
    public boolean equals(Eeg e) {
        if (e.getAlpha() == this.getAlpha() && e.getBeta() == this.getBeta() && e.getDelta() == this.getDelta() &&
                e.getTheta() == this.getTheta() && e.getGamma() == this.getGamma()) {
            return true;
        }
        return false;

    }
    }
