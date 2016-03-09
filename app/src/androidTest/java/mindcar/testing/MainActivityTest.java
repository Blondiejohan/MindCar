package mindcar.testing;

import com.example.sid.mindcontrollablecar.CommandUtils;import com.example.sid.mindcontrollablecar.SmartCar;import junit.framework.Assert;import junit.framework.TestCase;import java.lang.Exception;

import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;

/**
 * Created by sid on 3/4/16.
 */
public class MainActivityTest extends TestCase {
    SmartCar car = new SmartCar();
    public void testCheckCommand() throws Exception {
        Assert.assertEquals(CommandUtils.toByteArray(car.getCommands()).toString(), car.getCommands().name());
    }
}