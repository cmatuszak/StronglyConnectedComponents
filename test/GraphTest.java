import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the graph implementation and its methods
 */
public class GraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void testGraphConstructorZero() {
        new Graph(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphConstructorNegative() {
        new Graph(-1);
    }

    @Test
    public void testGraphConstructorAndGetNodes() {
        Graph g = new Graph(3);
        Assert.assertEquals(3, g.getNodes().size());
        Assert.assertEquals(new Node(1), g.getNodes().get(0));
        Assert.assertEquals(new Node(2), g.getNodes().get(1));
        Assert.assertEquals(new Node(3), g.getNodes().get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeInvalidU() {
        Graph g = new Graph(3);
        g.addEdge(4, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeInvalidV() {
        Graph g = new Graph(3);
        g.addEdge(2, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNegativeU() {
        Graph g = new Graph(3);
        g.addEdge(0, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNegativeV() {
        Graph g = new Graph(3);
        g.addEdge(3, 0);
    }

    @Test
    public void testAddEdge() {
        Graph g = new Graph(3);
        g.addEdge(1, 3);
        Assert.assertTrue(g.getNodes().get(0).getAdjacencyList().contains(new Node(3)));
    }

    @Test
    public void testDFSOnGraphWithOneNode() {
        Graph g = new Graph(1);
        g.DFS();
        Node n = g.getNodes().get(0);
        Assert.assertEquals(Color.BLACK, n.getColor());
        Assert.assertEquals(1, n.getD());
        Assert.assertEquals(2, n.getF());
    }

    @Test
    public void testDFSOnGraphWithTwoNodes() {
        Graph g = new Graph(2);
        g.addEdge(1, 2);
        g.DFS();
        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Assert.assertEquals(Color.BLACK, n1.getColor());
        Assert.assertEquals(Color.BLACK, n2.getColor());
        Assert.assertEquals(1, n1.getD());
        Assert.assertEquals(2, n2.getD());
        Assert.assertEquals(3, n2.getF());
        Assert.assertEquals(4, n1.getF());
    }

    @Test
    public void testDFSOnGraphWithThreeNodes() {
        Graph g = new Graph(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(1, 3);
        g.addEdge(3, 1);
        g.DFS();
        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Assert.assertEquals(Color.BLACK, n1.getColor());
        Assert.assertEquals(Color.BLACK, n2.getColor());
        Assert.assertEquals(Color.BLACK, n3.getColor());
        Assert.assertEquals(1, n1.getD());
        Assert.assertEquals(2, n3.getD());
        Assert.assertEquals(3, n3.getF());
        Assert.assertEquals(4, n2.getD());
        Assert.assertEquals(5, n2.getF());
        Assert.assertEquals(6, n1.getF());
    }

    @Test
    public void testDFSOnDifferentGraphWithThreeNodes() {
        Graph g = new Graph(3);
        g.addEdge(1, 3);
        g.addEdge(1, 2);
        g.addEdge(3, 2);
        g.DFS();
        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Assert.assertEquals(Color.BLACK, n1.getColor());
        Assert.assertEquals(Color.BLACK, n2.getColor());
        Assert.assertEquals(Color.BLACK, n3.getColor());
        Assert.assertEquals(1, n1.getD());
        Assert.assertEquals(2, n2.getD());
        Assert.assertEquals(3, n2.getF());
        Assert.assertEquals(4, n3.getD());
        Assert.assertEquals(5, n3.getF());
        Assert.assertEquals(6, n1.getF());
    }

    @Test
    public void testDFSOnExampleGraph() {
        Graph g = new Graph(5);
        g.addEdge(1, 2);
        g.addEdge(2, 2);
        g.addEdge(2, 5);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
        g.addEdge(3, 4);
        g.addEdge(4, 3);
        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Node n4 = g.getNodes().get(3);
        Node n5 = g.getNodes().get(4);

        g.DFS();

        Assert.assertEquals(Color.BLACK, n1.getColor());
        Assert.assertEquals(Color.BLACK, n2.getColor());
        Assert.assertEquals(Color.BLACK, n3.getColor());
        Assert.assertEquals(Color.BLACK, n4.getColor());
        Assert.assertEquals(Color.BLACK, n5.getColor());
        Assert.assertEquals(1, n1.getD());
        Assert.assertEquals(2, n2.getD());
        Assert.assertEquals(3, n3.getD());
        Assert.assertEquals(4, n4.getD());
        Assert.assertEquals(5, n4.getF());
        Assert.assertEquals(6, n3.getF());
        Assert.assertEquals(7, n5.getD());
        Assert.assertEquals(8, n5.getF());
        Assert.assertEquals(9, n2.getF());
        Assert.assertEquals(10, n1.getF());
    }

    @Test
    public void testFindSCCGraphWith1Node() {
        Graph g = new Graph(1);
        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(1);
        MyArrayList<Node> component = new MyArrayList<>(1);
        component.add(g.getNodes().get(0));
        expected.add(component);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(expected, scc);
    }

    @Test
    public void testFindSCCGraphWith2Nodes2Components() {
        Graph g = new Graph(2);
        g.addEdge(1, 2);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(1);
        MyArrayList<Node> component1 = new MyArrayList<>(1);
        component1.add(g.getNodes().get(0));
        MyArrayList<Node> component2 = new MyArrayList<>(1);
        component2.add(g.getNodes().get(1));
        expected.add(component1);
        expected.add(component2);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(2, scc.size());
        Assert.assertTrue(scc.contains(component1));
        Assert.assertTrue(scc.contains(component2));
    }

    @Test
    public void testFindSCCGraphWith2Nodes1Component() {
        Graph g = new Graph(2);
        g.addEdge(1, 2);
        g.addEdge(2, 1);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(1);
        MyArrayList<Node> component1 = new MyArrayList<>(2);
        component1.add(g.getNodes().get(0));
        component1.add(g.getNodes().get(1));
        expected.add(component1);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(1, scc.size());
        Assert.assertTrue(scc.contains(component1));
    }

    @Test
    public void testFindSCCGraphWith3Nodes3Components() {
        Graph g = new Graph(3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 2);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(3);
        MyArrayList<Node> component1 = new MyArrayList<>(1);
        component1.add(g.getNodes().get(0));
        MyArrayList<Node> component2 = new MyArrayList<>(1);
        component2.add(g.getNodes().get(1));
        MyArrayList<Node> component3 = new MyArrayList<>(1);
        component3.add(g.getNodes().get(2));
        expected.add(component1);
        expected.add(component2);
        expected.add(component3);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(3, scc.size());
        Assert.assertTrue(scc.contains(component1));
        Assert.assertTrue(scc.contains(component2));
        Assert.assertTrue(scc.contains(component3));
    }

    @Test
    public void testFindSCCGraphWith3Nodes2Components() {
        Graph g = new Graph(3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 2);
        g.addEdge(3, 1);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(2);
        MyArrayList<Node> component1 = new MyArrayList<>(2);
        component1.add(g.getNodes().get(0));
        component1.add(g.getNodes().get(2));
        MyArrayList<Node> component2 = new MyArrayList<>(1);
        component2.add(g.getNodes().get(1));
        expected.add(component1);
        expected.add(component2);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(2, scc.size());
        Assert.assertTrue(scc.contains(component1));
        Assert.assertTrue(scc.contains(component2));
    }

    @Test
    public void testFindSCCGraphWith3Nodes1Component() {
        Graph g = new Graph(3);
        g.addEdge(1, 3);
        g.addEdge(3, 1);
        g.addEdge(3, 2);
        g.addEdge(2, 3);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(1);
        MyArrayList<Node> component1 = new MyArrayList<>(3);
        component1.add(g.getNodes().get(0));
        component1.add(g.getNodes().get(2));
        component1.add(g.getNodes().get(1));
        expected.add(component1);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(1, scc.size());
        Assert.assertTrue(scc.contains(component1));
    }

    @Test
    public void testFindSCCExampleGraph() {
        Graph g = new Graph(5);
        g.addEdge(1, 2);
        g.addEdge(2, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(3, 1);
        g.addEdge(3, 4);
        g.addEdge(4, 3);
        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Node n4 = g.getNodes().get(3);
        Node n5 = g.getNodes().get(4);

        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(2);
        MyArrayList<Node> component1 = new MyArrayList<>(4);
        component1.add(g.getNodes().get(0));
        component1.add(g.getNodes().get(2));
        component1.add(g.getNodes().get(3));
        component1.add(g.getNodes().get(1));
        MyArrayList<Node> component2 = new MyArrayList<>(4);
        component2.add(g.getNodes().get(4));
        expected.add(component1);
        expected.add(component2);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(2, scc.size());
        Assert.assertEquals(1, scc.get(1).size());
        Assert.assertEquals(4, scc.get(0).size());
        Assert.assertTrue(scc.get(0).contains(g.getNodes().get(0)));
        Assert.assertTrue(scc.get(0).contains(g.getNodes().get(1)));
        Assert.assertTrue(scc.get(0).contains(g.getNodes().get(2)));
        Assert.assertTrue(scc.get(0).contains(g.getNodes().get(3)));
        Assert.assertTrue(scc.get(1).contains(g.getNodes().get(4)));
    }

    @Test
    public void testFindSCCExampleFromSlides() {
        Graph g = new Graph(8);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(2, 6);
        g.addEdge(3, 4);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 8);
        g.addEdge(5, 1);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 6);
        g.addEdge(7, 8);
        g.addEdge(8, 8);

        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Node n4 = g.getNodes().get(3);
        Node n5 = g.getNodes().get(4);
        Node n6 = g.getNodes().get(5);
        Node n7 = g.getNodes().get(6);
        Node n8 = g.getNodes().get(7);


        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(4);
        MyArrayList<Node> component1 = new MyArrayList<>(3);
        component1.add(n1);
        component1.add(n5);
        component1.add(n2);
        MyArrayList<Node> component2 = new MyArrayList<>(2);
        component2.add(n3);
        component2.add(n4);
        MyArrayList<Node> component3 = new MyArrayList<>(2);
        component3.add(n6);
        component3.add(n7);
        MyArrayList<Node> component4 = new MyArrayList<>(2);
        component4.add(n8);
        expected.add(component1);
        expected.add(component2);
        expected.add(component3);
        expected.add(component4);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(4, scc.size());
        Assert.assertTrue(scc.contains(component1));
        Assert.assertTrue(scc.contains(component2));
        Assert.assertTrue(scc.contains(component3));
        Assert.assertTrue(scc.contains(component4));
    }

    @Test
    public void testFindSCCExampleFromHomework() {
        Graph g = new Graph(7);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 1);
        g.addEdge(4, 3);
        g.addEdge(4, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(5, 7);
        g.addEdge(6, 3);

        Node n1 = g.getNodes().get(0);
        Node n2 = g.getNodes().get(1);
        Node n3 = g.getNodes().get(2);
        Node n4 = g.getNodes().get(3);
        Node n5 = g.getNodes().get(4);
        Node n6 = g.getNodes().get(5);
        Node n7 = g.getNodes().get(6);


        MyArrayList<MyArrayList<Node>> expected = new MyArrayList<>(2);
        MyArrayList<Node> component1 = new MyArrayList<>(6);
        component1.add(n1);
        component1.add(n3);
        component1.add(n6);
        component1.add(n4);
        component1.add(n2);
        MyArrayList<Node> component2 = new MyArrayList<>(1);
        component2.add(n7);
        MyArrayList<Node> component3 = new MyArrayList<>(1);
        component3.add(n5);
        expected.add(component1);
        expected.add(component2);
        expected.add(component3);

        MyArrayList<MyArrayList<Node>> scc = g.findStronglyConnectedComponents(false, false);

        Assert.assertEquals(3, scc.size());
        Assert.assertTrue(scc.contains(component1));
        Assert.assertTrue(scc.contains(component2));
        Assert.assertTrue(scc.contains(component3));
    }
}
