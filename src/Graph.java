/**
 * Represents a Graph as an array of Nodes with adjacency lists
 */
public class Graph {
    /**
     * The array of nodes in this graph
     */
    private MyArrayList<Node> nodes;

    /**
     * The time the DFS algorithm is currently on
     */
    private int t;

    /**
     * Constructs a graph with the given number of nodes (and no edges)
     * @param n the number of nodes in the graph
     * @throws IllegalArgumentException if nodes is <= 0
     */
    public Graph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("There must be at least 1 node in the graph");
        }
        nodes = new MyArrayList<>(n);
        // adds nodes 1-n *in indices 0-(n-1)*
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i + 1));
        }
    }

    /**
     * Adds a directed edge from node u to node v
     * @param u the name of the "from" node
     * @param v the name of the "to" node
     * @throws IllegalArgumentException If invalid node values are given for u and v
     */
    public void addEdge(int u, int v) {
        if (u > nodes.size() || v > nodes.size() || u <= 0 || v <= 0) {
            throw new IllegalArgumentException("An invalid node was given");
        }
        // note that we have to subtract 1 from each since node 1 is located in index 0
        nodes.get(u - 1).addEdge(nodes.get(v - 1));
    }

    /**
     * Gets the nodes of this graph
     * @return the array of nodes of this graph
     */
    public MyArrayList<Node> getNodes() {
        return this.nodes;
    }

    /**
     * Determines the strongly connected components of this graph
     * @return an array of the strongly connected components represented as arrays of nodes
     */
    public MyArrayList<MyArrayList<Node>> findStronglyConnectedComponents(boolean recursive, boolean timing) {
        long startTime = System.currentTimeMillis();
        //(1) Run DFS on the graph G
        if (recursive) {
            this.DFSRecursive();
        } else {
            this.DFS();
        }
        if (timing) {
            System.out.println("Finished first DFS: " + (System.currentTimeMillis() - startTime));
        }
        //(2) Construct G^t - graph with reversed edges
        Graph Gt = this.constructReversedGraph();
        if (timing) {
            System.out.println("Finished constructing Gt: " + (System.currentTimeMillis() - startTime));
        }
        //(3) Sort the nodes in G^t by finishing times in the first DFS
        if (recursive) {
            SortingUtils.mergeSortRecursive(Gt.nodes);
        } else {
            SortingUtils.mergeSort(Gt.nodes);
        }
        if (timing) {
            System.out.println("Finished merge sort: " + (System.currentTimeMillis() - startTime));
        }
        //(4) Run DFS on graph G^t taking vertices in descending order of finishing times (since we just sorted them)
        if (recursive) {
            return Gt.DFSRecursive();
        } else {
            return Gt.DFS();
        }
    }

    /**
     * Runs DFS on this graph (iteratively)
     * @return the connected components of the graph in the form of a list of components (a list of nodes)
     */
    public MyArrayList<MyArrayList<Node>> DFS() {
        t = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            // reset all nodes in the graph to the initial DFS state
            node.setColor(Color.WHITE);
            node.setD(0);
            node.setF(0);
            node.setParent(null);
        }
        MyArrayList<MyArrayList<Node>> components = new MyArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.getColor() == Color.WHITE) {
                // each time we start from a vertex here we are starting a new DFS component
                MyArrayList<Node> component = new MyArrayList<>();
                dfsVisit(node, component);
                components.add(component);
            }
        }
        return components;
    }
    /**
     * Helper for DFS as detailed in the class slides
     * Visits the given node as per the DFS algorithm
     * @param node the node we are starting on
     * @param component the current DFS component that we are on
     */
    private void dfsVisit(Node node, MyArrayList<Node> component) {
        Stack<Node> stack = new Stack<Node>();
        Node v = node;
        // while there are still nodes in the stack to be processed
        while (v != null) {
            if (v.getColor() == Color.WHITE) {
                t++;
                v.setD(t);
                v.setColor(Color.GRAY);
                // when we turn a vertex gray, we add it to the current component
                component.add(v);
                // we push this to the stack again and when we reach a gray vertex in the stack,
                // we know that all of its children have been processed and we can assign a finishing time
                stack.push(v);
                for (int i = 0; i < v.getAdjacencyList().size(); i++) {
                    Node u = v.getAdjacencyList().get(i);
                    if (u.getColor() == Color.WHITE) {
                        u.setParent(v);
                        stack.push(u);
                    }
                }
            } else if (v.getColor() == Color.GRAY){
                // if the node in the stack is already gray, then we know we have finished all of its children
                // and it can be turned black
                t++;
                v.setF(t);
                v.setColor(Color.BLACK);
            }
            v = stack.pop();
        }
    }

    /**
     * Runs DFS on this graph - recursive version
     */
    public MyArrayList<MyArrayList<Node>> DFSRecursive() {
        t = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            // reset all nodes in the graph to the initial DFS state
            node.setColor(Color.WHITE);
            node.setD(0);
            node.setParent(null);
        }
        MyArrayList<MyArrayList<Node>> components = new MyArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.getColor() == Color.WHITE) {
                MyArrayList<Node> component = new MyArrayList<>();
                this.dfsVisitRecursive(node, component);
                components.add(component);
            }
        }
        return components;
    }

    /**
     * Helper for DFS as detailed in the class slides
     * Visits the given node as per the DFS algorithm
     * @param node the node we are starting on
     */
    private void dfsVisitRecursive(Node node, MyArrayList<Node> component) {
        t++;
        node.setD(t);
        node.setColor(Color.GRAY);
        component.add(node);
        for (int i = 0; i < node.getAdjacencyList().size(); i++) {
            Node u = node.getAdjacencyList().get(i);
            if (u.getColor() == Color.WHITE) {
                u.setParent(node);
                this.dfsVisitRecursive(u, component);
            }
        }
        node.setColor(Color.BLACK);
        t++;
        node.setF(t);
    }

    /**
     * Construct the reversed graph of this graph,
     * where each edge is reversed in direction in the new graph
     * @return the reversed graph
     */
    private Graph constructReversedGraph() {
        Graph Gt = new Graph(this.nodes.size());
        for (int i = 0; i < this.nodes.size(); i++) {
            // the from node in the original graph
            Node from = this.nodes.get(i);
            // set the finishing time from the original graph to the same node in G^t so we can sort by finishing times
            Gt.getNodes().get(i).setF(from.getF());
            for (int j = 0; j < from.getAdjacencyList().size(); j++) {
                Node to = from.getAdjacencyList().get(j);
                Gt.addEdge(to.getName(), from.getName());
            }
        }
        return Gt;
    }
}
