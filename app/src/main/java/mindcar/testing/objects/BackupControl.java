package mindcar.testing.objects;

/**
 * Created by Mattias on 5/16/16.
 */
public class BackupControl {
    private int blink;
    private int attention;
    private int numberOfBlinks;


//  -=BLINK STATE=-
    public static final int BLINK_HOLD = 1;
    public static final int BLINK_BASELINE = 0;
    public static final int BLINK_RELEASE = -1;




    public BackupControl() {
        this.blink = 255;
        this.attention = 0;
        this.numberOfBlinks = 0;
    }


    public int getBlink() {
        return blink;
    }

    public void setBlink(int blink) {
        this.blink = blink;
        if(this.blink <= 100){
            numberOfBlinks++;
        }
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    /**
     * @return blink state depending on blink strength
     */
    public int getBlinkState() {
        if (blink >= 200) {
            return BLINK_RELEASE;
        } else if (blink >= 100) {
            return BLINK_HOLD;
        } else {
            return BLINK_BASELINE;
        }
    }

    public int getNumberOfBlinks(){
        return numberOfBlinks;
    }

    public void setNumberOfBlinks(int numberOfBlinks){
        this.numberOfBlinks = numberOfBlinks;
    }
}

