/**
 * Array-based list
 * Implementation similar to Java's ArrayList
 */
public class MyArrayList<T> {
    /**
     * The number of elements currently in this list
     */
    private int size = 0;

    /**
     * Size to start the array at if none is given
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * Internal array to hold the objects in this list
     */
    private Object[] elements;

    /**
     * Initializes empty array list with default size
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_SIZE];
    }

    /**
     * Initializes empty array list of size n
     * @param n initial size
     * @throws IllegalArgumentException if n is negative
     */
    public MyArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial size cannot be negative");
        }
        this.elements = new Object[n];
    }

    /**
     * Adds an element of type T to the end of the list
     * @param element element to be added to the list
     */
    public void add(T element) {
        if (size == elements.length) {
            expandCapacity();
        }
        elements[size] = element;
        size++;
    }

    /**
     * Sets an element of type T to the given index which must already exist in the list
     * @param element element to be set
     * @param index index to set the element at
     */
    public void set(T element, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + "Size: " + size);
        }
        elements[index] = element;
    }

    /**
     * Retrieves the element from the given index
     * @param i index of element to be retrieved
     * @return the element in index i
     * @throws IndexOutOfBoundsException if the given index is not valid
     */
    public T get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + "Size: " + size);
        }
        // Note we can ignore class cast warnings here as we can only add elements of type T to the list
        // and thus it is ensured to be of type T
        return (T) elements[i];
    }

    /**
     * Return the number of elements of this list
     * @return the size of this list
     */
    public int size() {
        return size;
    }

    /**
     * Determines if the given element is a member of this list
     * @param element the element you are looking for
     * @return true if the given element is in this list
     */
    public boolean contains(T element) {
        for (Object o : elements) {
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the last item of the list and returns it
     * @return the last item of the list
     */
    public T remove() {
        if (size < 1) {
            // nothing to remove
            return null;
        }
        // decreasing the size constitutes removing the element as it will be overwritten when the next thing is added
        size--;
        // this casting is safe because we are only ever adding elements of type T to the list
        return (T) this.elements[size];
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyArrayList)) {
            return false;
        }
        if (this.size() != ((MyArrayList) obj).size()) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (!this.get(i).equals(((MyArrayList) obj).get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for (int i = 0; i < this.size; i++) {
            hashcode += this.get(i).hashCode();
        }
        return super.hashCode();
    }

    /**
     * Doubles the capacity of the internal array and copies the current values over to the new expanded array
     */
    private void expandCapacity() {
        int newSize = elements.length * 2;
        Object[] newArray =  new Object[newSize];
        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }
        elements =  newArray;
    }
}
