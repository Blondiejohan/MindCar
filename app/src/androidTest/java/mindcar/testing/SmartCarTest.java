package mindcar.testing;

import com.example.sid.mindcontrollablecar.Command;import com.example.sid.mindcontrollablecar.EEGObject;import com.example.sid.mindcontrollablecar.SmartCar;import junit.framework.Assert;import junit.framework.TestCase;

import mindcar.testing.objects.Command;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;

/**
 * Created by sid on 3/3/16.
 */
public class SmartCarTest extends TestCase {
    SmartCar car = new SmartCar();

    public void testGetCommands() throws Exception {
        Assert.assertEquals(car.getCommands(), Command.STOP);
    }

    public void testGetSpeed() throws Exception {
        assertEquals(car.getSpeed(), 0);
    }

    public void testSetCommand() throws Exception {
        EEGObject eeg = new EEGObject();
        eeg.setAttention(70);
        car.setCommand(eeg);
        assertEquals(car.getCommands(), Command.FORWARD);
    }

    public void testSetSpeed() throws Exception {
        car.setSpeed(100);
        assertEquals(car.getSpeed(), 100);
    }
}