package mindcar.testing;

import junit.framework.Assert;import junit.framework.TestCase;

import mindcar.testing.objects.Command;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;

/**
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/3/16.
 */
public class SmartCarTest extends TestCase {
    SmartCar car = new SmartCar();

    /**
     * Test the getter for Commands
     * @throws Exception
     */
    public void testGetCommands() throws Exception {
        Assert.assertEquals(car.getCommands(), Command.s);
    }

    /**
     * Test the getter for speed
     * @throws Exception
     */
    public void testGetSpeed() throws Exception {
        assertEquals(car.getSpeed(), 0);
    }

    /**
     * Test the setter for Commands by getting it from the car after insertion
     * @throws Exception
     */
    public void testSetCommand() throws Exception {
        EEGObject eeg = new EEGObject();
        eeg.setAttention(70);
        //car.setCommand(eeg);
        assertEquals(car.getCommands(), Command.f);
    }

    /**
     * Test the setter for speed by getting it from the car after insertion
     * @throws Exception
     */
    public void testSetSpeed() throws Exception {
        car.setSpeed(100);
        assertEquals(car.getSpeed(), 100);
    }
}