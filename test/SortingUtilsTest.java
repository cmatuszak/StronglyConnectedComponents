import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the sorting utils
 */
public class SortingUtilsTest {

    @Test
    public void testMergeSortEmpty() {
        MyArrayList<Integer> list = new MyArrayList<>(0);
        SortingUtils.mergeSort(list);
        Assert.assertEquals(new MyArrayList<>(0), list);
    }

    @Test
    public void testMergeSortOneElement() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        SortingUtils.mergeSort(list);
        MyArrayList<Integer> listSorted = new MyArrayList<>();
        listSorted.add(1);
        Assert.assertEquals(listSorted, list);
    }

    @Test
    public void testMergeSortTwoElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        SortingUtils.mergeSort(list);
        MyArrayList<Integer> listSorted = new MyArrayList<>();
        listSorted.add(1);
        listSorted.add(2);
        Assert.assertEquals(listSorted, list);
    }

    @Test
    public void testMergeSortTwoElementsReversed() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        MyArrayList<Integer> listInput = new MyArrayList<>();
        listInput.add(2);
        listInput.add(1);
        SortingUtils.mergeSort(listInput);
        Assert.assertEquals(list, listInput);
    }

    @Test
    public void testMergeSortThreeElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(2);
        list.add(5);
        list.add(12);
        MyArrayList<Integer> listInput = new MyArrayList<>();
        listInput.add(12);
        listInput.add(2);
        listInput.add(5);
        SortingUtils.mergeSort(listInput);
        Assert.assertEquals(list, listInput);
    }

    @Test
    public void testMergeSortOddElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(2);
        list.add(5);
        list.add(12);
        list.add(14);
        list.add(17);
        list.add(43);
        list.add(54);
        MyArrayList<Integer> listInput = new MyArrayList<>();
        listInput.add(17);
        listInput.add(5);
        listInput.add(14);
        listInput.add(43);
        listInput.add(54);
        listInput.add(2);
        listInput.add(12);
        SortingUtils.mergeSort(listInput);
        Assert.assertEquals(list, listInput);
    }

    @Test
    public void testMergeSortEvenElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(2);
        list.add(5);
        list.add(14);
        list.add(17);
        list.add(43);
        list.add(54);
        MyArrayList<Integer> listInput = new MyArrayList<>();
        listInput.add(17);
        listInput.add(5);
        listInput.add(14);
        listInput.add(43);
        listInput.add(54);
        listInput.add(2);
        SortingUtils.mergeSort(listInput);
        Assert.assertEquals(list, listInput);
    }

}
