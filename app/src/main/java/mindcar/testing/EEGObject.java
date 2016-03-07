package mindcar.testing;

/**
 * Object storing values from a Neurosky Mindwave Mobile
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/2/16.
 */
public class EEGObject {
    private int attention;
    private int meditation;
    private int blink;
    //TODO - integrate patterns?

    /**
     * Constructs a basic EEGObject with all of the values set to zero (0)
     */
    public EEGObject(){
        attention = 0;
        meditation = 0;
        blink = 0;
    }

    /**
     * @return the attention value
     */
    public int getAttention(){
        return attention;
    }

    /**
     * @return the meditation value
     */
    public int getMeditation(){
        return meditation;
    }

    /**
     * @return the blink value
     */
    public int getBlink(){
        return blink;
    }

    /**
     * Set the attention value of the instance
     * @param attention
     */
    public void setAttention(int attention){
        this.attention = attention;
    }

    /**
     * Set the meditation value of the instance
     * @param meditation
     */
    public void setMeditation(int meditation){
        this.meditation = meditation;
    }

    /**
     * Set the blink value of the instance
     * @param blink
     */
    public void setBlink(int blink){
        this.blink = blink;
    }
}
