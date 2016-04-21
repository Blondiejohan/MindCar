package mindcar.testing;

import junit.framework.Assert;import junit.framework.TestCase;

import mindcar.testing.objects.Command;
import mindcar.testing.util.CommandUtils;

/**
 * Tests for the CommandUtils class
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/3/16.
 */
public class CommandUtilsTest extends TestCase {

    /**
     * Tests if toByteArray() generates the right byte[]
     * @throws Exception
     */
    public void testToByteArray() throws Exception {
        Assert.assertEquals(CommandUtils.toByteArray(Command.f), Command.f.name().getBytes());
    }
}