package mindcar.testing.objects;

import junit.framework.TestCase;

/**
 * Created by sarahaldelame on 19/04/16.
 */
public class PatternTest extends TestCase {

    public void testAdd() throws Exception {
        Pattern<Integer> hi = new Pattern<>();
        hi.add(1);
        assertEquals(hi.get(0) + "", 6 + "");
    }

    public void testEquals() throws Exception {

    }
}