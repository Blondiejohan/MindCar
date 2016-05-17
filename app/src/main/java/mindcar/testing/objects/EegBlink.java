package mindcar.testing.objects;

import com.neurosky.thinkgear.TGDevice;

/**
 * Created by Nikolaos-Machairiotis Sasopoulos on 13/05/2016.
 */
public class EegBlink {

    private int blink;
    private int attention;

    public EegBlink(int blink, int attention) {
        this.blink = blink;
        this.attention = attention;
    }

    public int getBlink() {
        return blink;
    }

    public void setBlink(int blink) {
        this.blink = blink;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public EegBlink(){
        blink = 0;
        attention = 0;
    }

    public boolean leftBlink(){
        if (blink < 70 && blink > 40){
            return true;
        }
        else
        {return false;}
    }

    public boolean rightBlink() {
        if (blink <40 && blink > 30){
            return true;}
        else
        {return false;}
    }
}
