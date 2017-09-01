/**
 * Implementation of the stack data structure
 * First in, last out
 */
public class Stack<T> {

    // Internal array lsit to store elements
    // the implementation of array list makes implementing stack methods trivial
    private MyArrayList<T> elements;

    /**
     * Construct a stack of the given initial size
     * @param size the initial size of the stack
     * @throws IllegalArgumentException if the size is negative
     */
    public Stack(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Stack size cannot be negative");
        }
        this.elements = new MyArrayList<>(size);
    }

    /**
     * Construct a stack with no initial size
     */
    public Stack() {
        this.elements = new MyArrayList<>();
    }

    /**
     * Add an element to the top of the stack
     * @param element the element to be added to the stack
     */
    public void push(T element) {
        // here the "top" of the stack is really the end of the arraylist
        this.elements.add(element);
    }

    /**
     * Remove the top element from the stack and return it
     * @return the top element from the stack
     */
    public T pop() {
        // removes the last item of the list which is really the top of the stack
        return this.elements.remove();
    }
}
