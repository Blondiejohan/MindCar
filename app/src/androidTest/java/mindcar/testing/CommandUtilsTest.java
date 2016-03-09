package mindcar.testing;

import com.example.sid.mindcontrollablecar.Command;import com.example.sid.mindcontrollablecar.CommandUtils;import junit.framework.Assert;import junit.framework.TestCase;

import mindcar.testing.objects.Command;
import mindcar.testing.util.CommandUtils;

/**
 * Created by sid on 3/3/16.
 */
public class CommandUtilsTest extends TestCase {

    public void testToByteArray() throws Exception {
        Assert.assertEquals(CommandUtils.toByteArray(Command.FORWARD), Command.FORWARD.name().getBytes());
    }
}