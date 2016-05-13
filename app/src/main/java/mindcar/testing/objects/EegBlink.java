package mindcar.testing.objects;

import com.neurosky.thinkgear.TGDevice;

/**
 * Created by Nikolaos-Machairiotis Sasopoulos on 13/05/2016.
 */
public class EegBlink {

    public int blink;
    public int attention;

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

    public boolean test1(){
        if (blink == 2 && attention>10){
            return true;
        }
        else
        {return false;}
    }

    public boolean test2() {
        if (blink == 3 && attention > 10){
            return true;}
        else
        {return false;}
    }
}
