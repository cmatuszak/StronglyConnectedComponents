/**
 * Represents a node or vertex in a graph
 * Each node has an adjacency list of nodes it is connected to by an edge
 * Each node also optionally has the information necessary for DFS
 */
public class Node implements Comparable<Node> {

    /**
     * The adjacency list of edges for this node
     * Each entry in this node's adjacency list means that there is a directed edge from this node to that node
     */
    private MyArrayList<Node> adjacencyList;

    /**
     * The "name" of this node
     * In this case we will name all of our nodes as integers
     */
    private int name;

    /**
     * The discovery time of this node
     */
    private int d;

    /**
     * The finishing time of this node
     */
    private int f;

    /**
     * The color of this node
     */
    private Color color;

    /**
     * The parent of this node in the DFS forest
     */
    private Node parent;

    /**
     * Constructs a new node with the given name
     * @param name the integer identifier for this node
     */
    public Node(int name) {
        this.name = name;
        this.adjacencyList = new MyArrayList<>();
    }

    /**
     * Adds an edge from this node to the given node
     * @param v the node that we need to add an edge "to"
     */
    public void addEdge(Node v) {
        this.adjacencyList.add(v);
    }

    /**
     * Gets the adjacency list for this node
     * @return the adjacency list for this node
     */
    public MyArrayList<Node> getAdjacencyList() {
        return this.adjacencyList;
    }

    /**
     * Gets the name of this node
     * @return the integer identifier of this node
     */
    public int getName() {
        return this.name;
    }

    /**
     * Get the color of this node
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Set the color of this node to the given color
     * @param color color to be set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the discovery time of this node
     * @return the discovery time of this node
     */
    public int getD() {
        return d;
    }

    /**
     * Set the discovery time of this node
     * @param d the time this node was discovered
     * @throws IllegalArgumentException if d is negative
     */
    public void setD(int d) {
        if (d < 0) {
            throw new IllegalArgumentException("Discovery time cannot be negative");
        }
        this.d = d;
    }

    /**
     * Get the finishing time of this node
     * @return the time this node was finished
     */
    public int getF() {
        return f;
    }

    /**
     * Set the finishing time of this onde
     * @param f the time this node was finished
     * @throws IllegalArgumentException if f is negative
     */
    public void setF(int f) {
        if (f < 0) {
            throw new IllegalArgumentException("Finishing time cannot be negative");
        }
        this.f = f;
    }

    /**
     * Get the parent of this node
     * @return the parent node of this node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Set the parent of this node
     * @param parent the parent node of this node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    // For our purposes, we want nodes with larger finishing times to be "smaller"
    public int compareTo(Node o) {
        return o.getF() - this.getF();
    }

    @Override
    // Nodes are equal if they have the same names
    // This is necessary to allow self-loops
    // An invariant in the graph implementation is that all node names must be unique
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        } else {
            return ((Node) obj).getName() == this.getName();
        }
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.getName());
    }
}
