Caitlin Matuszak
8/12/17
CS5800 Algorithms
Professor Or Beit Aharon
Programming Assignment

FINDING STRONGLY CONNECTED COMPONENTS
I. Running the program 
- on the command line run "java -jar path/to/CMatuszakProgrammingAssignment.jar input.txt"
- the input file must be in the same directory that you are running this from
- the program will accept two additional optional arguments, both booleans. You can pass in either true or false as the second argument to enable (or not) some output in the console regarding the timing of the different steps of the algorithm. You can also pass in either true or false as the third argument to enable (or not) running the recursive versions of DFS and MergeSort (see below for explanation). 
- by default the timing output is turned off and the iterative versions of the algorithms will be run
- the output file components.txt will be created/overwritten in the current directory

For an example of the timing output and the performance of my program:
When run on a randomly generated input file with 2 million vertices and 10 million edges:
java -jar path/to/CMatuszakProgrammingAssignment.jar 2million10million.txt true false
Finished constructing graph: 15558
Finished first DFS: 889
Finished constructing Gt: 5026
Finished merge sort: 13411
Finished finding strongly connected components.
Total time: 29783
(All times are in milliseconds)

II. Data structures implemented

1. MyArrayList
- similar to java's ArrayList
- list that uses internal array to store the elements
- the size of the array starts either as a value passed into the constructor or a 
default value
- when the array runs out of space, it's size is doubled and the items are copied 
over to the new expanded array
- same efficiency as an array if you know the maximum size it will be and pass that 
value into the constructor
- if you don't pass in the maximum size, you will have to expand a maximum of log(n) 
times. Overall, the complexity of adding n elements is the same as that of Java's 
ArrayList: O(n). (The math is similar to that of the proof of MakeHeap's complexity 
in the slides).

2. Stack
- trivial to implement with the given MyArrayList implementation
- first in, last out model	
- adds no complexity on top of the MyArrayList it uses internally

3. Node
- represents a single node/vertex in a graph
- data:
- an adjacency list of nodes that this node is connected to by an edge. For an edge 
(u,v) in the graph, node u will have node v in its adjacency list. Note that this 
represents directed edges. Stored in a MyArrayList for convenience.
- name - in this case we always represent our nodes by having integer names
- d - discovery time of the node in the DFS algorithm
- f - finishing time of the node in the DFS algorithm
- Color - enumeration: either WHITE, GRAY, or BLACK - represents the colors in the 
DFS algorithm
- Parent - this node's parent node in the DFS algorithm
- The only piece of data that are required to construct a node is its name. The 
adjacency list is initialized to an empty list and all other data can be filled 
in as needed in the running of the DFS algorithm.  
- We define the compareTo(Node o) on a node based on the node's finishing times.
 If neither of them have a finishing time (both are 0) or they have the same 
finishing time (not possible in actual running of DFS), then they are considered 
comparatively equivalent. If this node's finishing time is greater than that of 
o's, then this node is considered comparatively smaller than o, and vice versa. 
This defines the sort order of nodes from greatest finishing time to smallest 
finishing time.
- Equality: two nodes are equal if they have the same name. The graph cannot have
 two different nodes of the same name (will be enforced in the graph constructor).
 Not including the adjacency list in equality also allows for self-references 
without introducing circular equality problems.

4. Graph
- represents a graph G = (V, E) as a MyArrayList of nodes 
- the edges are taken care of by the node's adjacency lists
- when constructing a graph, we pass in the number of nodes it will have and then
 initialize nodes with names 1...n.
- edges can be added to the graph in constant time (really amortized constant 
time due to the array list implementation - adding m edges from a node should t
ake O(m))
- Thus, constructing a graph G = (V, E) should take O(|V| + |E|) operations

III. Explanation of algorithms used

- Note: for both of these algorithms I implemented two versions, because I started
 by implementing the recursive version as detailed by the pseudocode in the slides,
 but upon stress testing my program with large graphs, it was clear the recursive
 version quickly exceeded the Java heap space with the call stack. An iterative
 version does not run into this problem. I have left both implementations there
 since I had already implemented the recursive version. 

1. DFS

i. Iterative DFS
- we will keep track of the DFS components as we go and then return the DFS 
components of this graph
- initialize all nodes to WHITE, d=0, f=0 and parent=null as normal
- iterate over all the nodes (in the order of the nodes in the graph's list) If we
 find a white one, start a new component list, call dfsVisit on this node, and then
 add the component to the final components list. Note that we know when we find a 
white vertex in this outer loop that we are starting a new DFS component. 
- DFS visit:
- stores the list of nodes to be processed in a stack, starting with the node that
 was passed in. 
- while there are still nodes in the stack to be processed
- pop the top one off (v)
- if v's color is white, then it hasn't been discovered yet, and we give it a 
discovery time and turn it gray. We also add it to the current component at this
 time.
- push v onto the stack again - do to the nature of the stack we know that when 
we return to this v, all of its children will have been finished
- for each node in v's adjacency list, set its parent to be v and push it onto 
the stack to be processed
- if v is gray, then we have come to a vertex that has already been discovered 
and we know all of its children must also be finished, so we can give it a 
finishing time and turn it black. 
- Note that DFS visit takes in a node and will visit and finish all nodes in that
 DFS component. We call DFS visit once per component.
- Runtime analysis:
- Initialization: O(|V|)
- DFS visit: each node in the entire graph gets added to the stack exactly twice:
 O(|V|), and we iterate through all of the edges in the nodes in the stack: 
(O(2|E|): O(|V| + |E)
- Total: O(|V| + |E|)

ii. Recursive DFS
- modeled off of the pseudocode in the slides
- O(|V| + |E|) as shown in the slides

2. MergeSort

i. Iterative MergeSort
- Loop over the possible sizes of the subarrays that need to be merged. We start with 
subarrays of size 1 and double each time until we eventually merge the last two subarrays 
of approximately size n/2.
- For each possible size, loop over all of the pairs subarrays of that size in the overall 
area and merge them together
- For merging without recursion, we create two temporary arrays to hold the subarrays we 
are trying to merge, and while there are still elements left to be merged in both the left
 and right halves, we compare the first element of each and place the smaller one in the 
next unsorted place in the original array
- When there are no elements left to be merged in one of the arrays, we finish by copying 
over any remaining elements, if any, in the other array.
- Runtime analysis:
- this should have the same runtime as the recursive version (O(nlogn)), as we are merging
 the same number of subarrays, starting from size 1 and doubling all the way up, instead of
 starting from size n and halving until we reach 1. 
- merge has a runtime of O(n) as we make a maximum of one comparison every time we place an
 element in a sorted position in the array.
- O(nlog(n))

ii. Recursive MergeSort
- modeled off of the pseudocode in the slides 
- O(nlog(n)) as shown in the slides

IV. Explanation of the overall Strongly Connected Components algorithm
1. Construct a graph G corresponding to the given input file as described above.
 The program will throw an exception if the input file is not formatted according
 to the assignment instructions.
2. Run DFS on G.
3. Construct a new graph G^t: the reversed graph as described in the slides.
 For each edge (u,v) in G, there will be an edge (v,u) in G^t.
4. Apply MergeSort to the nodes in G^t to sort them by the finishing times in the
 DFS on G (this is done by initially setting the finishing times from the nodes in
 G upon constructing the new nodes in G^t and then sorting)
5. Run DFS on G^t, taking the vertices in descending order of finishing time when 
starting a new component. (This condition is ensured because the DFS algorithm follows
 node order when selecting the root of a new component and we just sorted them by 
finishing time. Also note that we only need to take the vertices in finishing time order
 when choosing a new component, since if we have a choice of 2 edges from vertex u,
 it doesn't matter which order we take them in, since they will be in the same DFS 
component regardless.)

V. Overall Runtime Analysis
1. O(|V| + |E|) 
2. O(|V| + |E|)
3. O(|V| + |E|)
4. O(|V|log|V|)
5. O(|V| + |E|)
Total: O(|V| + |E|) + O(|V|log|V|) = O(|V| + |E|)
(this last equality follows from the fact that |E| < |V|^2, so O(|V| + |E|) < O(|V| + |V|^2) 
= O(|V|^2) and since O(|V|log|V|) grows asymptotically slower that O(|V|^2) adding it in will
 not affect the asymptotic growth of the function)



