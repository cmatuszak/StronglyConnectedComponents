import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Node
 */
public class NodeTest {

    @Test
    public void testConstructor() {
        Node n = new Node(1);
        Assert.assertEquals(1, n.getName());
        Assert.assertEquals(0, n.getAdjacencyList().size());
    }

    @Test
    public void testAddEdge() {
        Node n = new Node(1);
        Node n2 = new Node(2);
        n.addEdge(n2);
        Assert.assertEquals(1, n.getAdjacencyList().size());
        Assert.assertEquals(n2, n.getAdjacencyList().get(0));
    }

    @Test
    public void testAddSelfEdge() {
        Node n = new Node(1);
        n.addEdge(n);
        Assert.assertEquals(1, n.getAdjacencyList().size());
        Assert.assertEquals(n, n.getAdjacencyList().get(0));
    }

    @Test
    public void testGetAdjacencyList() {
        Node n = new Node(1);
        Assert.assertEquals(0, n.getAdjacencyList().size());
        n.addEdge(n);
        Assert.assertEquals(1, n.getAdjacencyList().size());
        Assert.assertEquals(n, n.getAdjacencyList().get(0));
        Node n2 = new Node(2);
        n.addEdge(n2);
        Assert.assertEquals(2, n.getAdjacencyList().size());
        Assert.assertEquals(n, n.getAdjacencyList().get(0));
        Assert.assertEquals(n2, n.getAdjacencyList().get(1));
    }

    @Test
    public void testGetName() {
        Node n = new Node(1);
        Assert.assertEquals(1, n.getName());
        Node n2 = new Node(12);
        Assert.assertEquals(12, n2.getName());
    }

    @Test
    public void testGetAndSetColor() {
        Node n = new Node(1);
        Assert.assertNull(n.getColor());
        n.setColor(Color.WHITE);
        Assert.assertEquals(Color.WHITE, n.getColor());
        n.setColor(Color.GRAY);
        Assert.assertEquals(Color.GRAY, n.getColor());
    }

    @Test
    public void testGetAndSetD() {
        Node n = new Node(1);
        Assert.assertEquals(0, n.getD());
        n.setD(1);
        Assert.assertEquals(1, n.getD());
        n.setD(2);
        Assert.assertEquals(2, n.getD());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDNegative() {
        Node n = new Node(1);
        n.setD(-1);
    }

    @Test
    public void testGetAndSetF() {
        Node n = new Node(1);
        Assert.assertEquals(0, n.getF());
        n.setF(1);
        Assert.assertEquals(1, n.getF());
        n.setF(2);
        Assert.assertEquals(2, n.getF());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFNegative() {
        Node n = new Node(1);
        n.setF(-1);
    }

    @Test
    public void testGetAndSetParent() {
        Node n = new Node(1);
        Node n2 = new Node(2);
        Assert.assertNull(n.getParent());
        n.setParent(n2);
        Assert.assertEquals(n2, n.getParent());
    }

    @Test
    public void testCompareTo() {
        Node n = new Node(1);
        Node n2 = new Node(2);
        Assert.assertEquals(0, n.compareTo(n2));
        n.setF(1);
        Assert.assertTrue(n.compareTo(n2) < 0);
        n2.setF(4);
        Assert.assertTrue(n.compareTo(n2) > 0);
    }

    @Test
    public void testEquals() {
        Node n = new Node(1);
        Assert.assertFalse(n.equals("abc"));
        Node n2 = new Node(2);
        Node n3 = new Node(1);
        Assert.assertFalse(n.equals(n2));
        Assert.assertTrue(n.equals(n3));
    }
}
