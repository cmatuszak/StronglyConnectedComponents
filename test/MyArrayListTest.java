import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for MyArrayList
 */
public class MyArrayListTest {

    @Test
    public void testConstructors() {
        new MyArrayList<Integer>();
        new MyArrayList<Integer>(12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2Negative() {
        new MyArrayList<Integer>(-1);
    }

    @Test
    public void testAddAndGet() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(123);

        Assert.assertEquals(new Integer(1), list.get(0));
        Assert.assertEquals(new Integer(123), list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.get(0);
    }

    @Test
    public void testSize() {
        MyArrayList<Integer> list = new MyArrayList<>();
        Assert.assertEquals(0, list.size());
        list.add(12);
        Assert.assertEquals(1, list.size());
        list.add(34);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testContains() {
        MyArrayList<Integer> list = new MyArrayList<>();
        Assert.assertFalse(list.contains(1));
        list.add(1);
        Assert.assertTrue(list.contains(1));
        list.add(2);
        list.add(3);
        list.add(45);
        list.add(56);
        Assert.assertFalse(list.contains(5));
        Assert.assertTrue(list.contains(56));
    }

    @Test
    public void testForceAnExpansion() {
        MyArrayList<Integer> list = new MyArrayList<>(1);
        list.add(1);
        list.add(2);
        list.add(3);
        Assert.assertEquals(new Integer(1), list.get(0));
        Assert.assertEquals(new Integer(2), list.get(1));
        Assert.assertEquals(new Integer(3), list.get(2));
    }

    @Test
    public void testSet() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Assert.assertEquals(new Integer(1), list.get(0));
        Assert.assertEquals(new Integer(2), list.get(1));
        Assert.assertEquals(new Integer(3), list.get(2));
        list.set(4, 0);
        list.set(5, 2);
        Assert.assertEquals(new Integer(4), list.get(0));
        Assert.assertEquals(new Integer(2), list.get(1));
        Assert.assertEquals(new Integer(5), list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetInvalidIndex() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(4, 4);
    }

    @Test
    public void testEquals() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        MyArrayList<Integer> list3 = new MyArrayList<>();
        list3.add(1);
        list3.add(2);
        list3.add(4);
        MyArrayList<Integer> list4 = new MyArrayList<>();
        list4.add(1);
        list4.add(2);
        Assert.assertTrue(list1.equals(list2));
        Assert.assertNotEquals(list1, list3);
        Assert.assertNotEquals(list1, list4);
        Assert.assertNotEquals(list1, "123");
    }

    @Test
    public void testRemove() {
        MyArrayList<Integer> list = new MyArrayList<>();
        Assert.assertNull(list.remove());
        list.add(1);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(new Integer(1), list.remove());
        Assert.assertEquals(0, list.size());

        list.add(12);
        list.add(23);
        list.add(34);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(new Integer(34), list.remove());
        Assert.assertEquals(2, list.size());
    }
}
