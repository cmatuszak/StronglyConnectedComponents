import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the stack data structure
 */
public class StackTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegative() {
        new Stack<Integer>(-1);
    }

    @Test
    public void testConstructors() {
        new Stack<Integer>();
        new Stack<Integer>(10);
    }

    @Test
    public void testPushAndPop() {
        Stack<Integer> stack = new Stack<>();
        Assert.assertNull(stack.pop());
        stack.push(1);
        Assert.assertEquals(new Integer(1), stack.pop());
        stack.push(2);
        stack.push(3);
        stack.push(4);
        Assert.assertEquals(new Integer(4), stack.pop());
        Assert.assertEquals(new Integer(3), stack.pop());
        Assert.assertEquals(new Integer(2), stack.pop());
    }
}
