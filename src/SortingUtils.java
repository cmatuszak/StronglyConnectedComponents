/**
 * Utility methods useful for sorting arrays of type T
 */
public class SortingUtils {
    /**
     * Implementation of mergeSort to sort a MyArrayList
     * @param list the list to be sorted
     * @return the sorted list
     */
    public static <T extends Comparable> void mergeSort(MyArrayList<T> list) {
        // loop over the sizes of the subarrays to merge
        // starting with size 1 and doubling until we reach the last iteration
        // which should merge two lists of size n / 2
        for (int size = 1; size < list.size(); size = 2 * size) {
            // loop over the subarrays of the current size and merge pairs of two together
            // we increase by double the size each iteration because each iteration merges two
            // subarrays of the current size together
            for (int subarrayStart = 0; subarrayStart < list.size(); subarrayStart += 2 * size) {
                // the end of the first subarray, exclusive
                int middle = Math.min(subarrayStart + size, list.size());
                // the end of the second subarray, exclusive
                // if we overshoot the end of of the list, the end of the second subarray is just the size of the list
                int end = Math.min(subarrayStart + 2 * size, list.size());
                // merge the two subarrays together
                merge(list, subarrayStart, middle, end);
            }
        }
    }

    private static <T extends Comparable> MyArrayList<T> merge(MyArrayList<T> list, int start, int middle, int end) {
        // nothing to merge
        // or everything is already in the right half which is already sorted
        // or everything is already in the left half which is already sorted
        if (end - start == 0 || middle - start == -1 || end - middle == 0) {
            return list;
        }

        // left half to merge
        MyArrayList<T> left = copySubList(list, start, middle);
        // right half to merge
        MyArrayList<T> right = copySubList(list, middle, end);

        // number of elements we have merged from the left so far
        int l = 0;
        // number of elements we have merged from the right so far
        int r = 0;
        // the position that the next sorted element should go in the original list
        int p = start;

        // while there are still elements left in both halves
        while (l < left.size() && r < right.size()) {
            if (left.get(l).compareTo(right.get(r)) <= 0) {
                list.set(left.get(l), p);
                l++;
            } else {
                list.set(right.get(r), p);
                r++;
            }
            p++;
        }

        // finish any remaining elements in left
        while (l < left.size()) {
            list.set(left.get(l), p);
            l++;
            p++;
        }

        // finish any remaining elements in right
        while (r < right.size()) {
            list.set(right.get(r), p);
            r++;
            p++;
        }
        return list;
    }

    /**
     * Implementation of mergeSort to sort a MyArrayList
     * Modeled off of the pseudocode in the slides
     * @param list the list to be sorted
     * @return the sorted list
     */
    public static <T extends Comparable> MyArrayList<T> mergeSortRecursive(MyArrayList<T> list) {
        if (list.size() <= 1) {
            return list;
        }
        MyArrayList<T> b = copySubList(list, 0, list.size() / 2);
        MyArrayList<T> c = copySubList(list, list.size() / 2, list.size());
        mergeSortRecursive(b);
        mergeSortRecursive(c);
        return mergeRecursive(b, c, list.size() / 2, (int) Math.ceil((double) list.size() / 2), list);
    }

    /**
     * Merge two sorted lists into a sorted list
     * Modeled off of the pseudocode in the slides
     * @param a the first sorted list to be merged
     * @param b the second sorted list to be merged
     * @param k the length of a to be considered
     * @param l the length of b to be considered
     * @param c the result list to place the final sorted list in
     * @return the sorted list of all elements in a and b
     */
    private static <T extends Comparable> MyArrayList<T> mergeRecursive(MyArrayList<T> a, MyArrayList<T> b, int k, int l, MyArrayList<T> c) {
        if (k == 0 && l == 0) {
            return c;
        }
        if (k == 0) {
            for(int j = 0; j < l; j++) {
                c.set(b.get(j), j);
            }
            return c;
        }
        if (l == 0) {
            for (int j = 0; j < k; j++) {
                c.set(a.get(j), j);
            }
            return c;
        }
        // Note that my lists are 0-indexed, so we need to subtract 1 from k and l
        // to get the last elements in the lists
        if (a.get(k - 1).compareTo(b.get(l - 1)) <= 0) {
            // the last element in a is less than the last element in b
            // so set the last element in b to the last unsorted place in c
            c.set(b.get(l - 1), k + l - 1);
            return mergeRecursive(a, b, k, l - 1, c);
        } else {
            // the last element in a is greater than the last element in b
            // so set the last element in a to the last unsorted place in c
            c.set(a.get(k - 1), k + l - 1);
            return mergeRecursive(a, b, k - 1, l ,c);
        }
    }

    /**
     * Copies a subarray of the given array from the given start (inclusive) to the
     * given end (exclusive)
     * @param list the list to be copied
     * @param start the index to start copying (inclusive)
     * @param end the index to stop copying (exclusive)
     * @throws IllegalArgumentException if invalid start and end are given
     * @return the copied sublist
     */
    private static <T extends Comparable> MyArrayList<T> copySubList(MyArrayList<T> list, int start, int end) {
        if (end < start || end < 0 || start < 0 || start >= list.size() || end > list.size()) {
            throw new IllegalArgumentException("Invalid start and end values given. Start: " + start + "End: + " + end);
        }
        MyArrayList<T> newList = new MyArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }
}
