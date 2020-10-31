import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Thomas Crawford
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        size = 0;
        backingArray  = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * size + 1];
        int count = 0;
        for (T s: data) {
            if (s == null) {
                throw new IllegalArgumentException("Element in data is null");
            } else if (count == 0) {
                backingArray[0] = null;
                count++;
            }
            backingArray[count] = s;
            count++;
        }
        for (int k = size / 2; k > 0; k--) {
            buildHeap(k);
        }
    }



    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == backingArray.length - 1) {
            T[] temp = backingArray;
            backingArray = (T[]) new Comparable[temp.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = tmp;
        }
        size++;
        backingArray[size] = data;
        int curr = size;
        if (backingArray[parent(curr)] != null) {
            while (backingArray[parent(curr)] != null
                    && backingArray[curr].compareTo(
                            backingArray[parent(curr)]) < 0) {
                swap(curr, parent(curr));
                curr = parent(curr);
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        T root = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        buildHeap(1);
        return root;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     *
     * @param k Current index
     */
    private void buildHeap(int k) {
        if (k < size && backingArray[leftChild(k)]
                != null && backingArray[rightChild(k)] != null) {
            if (backingArray[k].compareTo(backingArray[leftChild(k)]) > 0
                    || backingArray[k].compareTo(
                    backingArray[rightChild(k)]) > 0) {
                if (backingArray[leftChild(k)].compareTo(
                        backingArray[rightChild(k)]) < 0) {
                    swap(k, leftChild(k));
                    buildHeap(leftChild(k));
                } else {
                    swap(k, rightChild(k));
                    buildHeap(rightChild(k));
                }
            }
        } else if (k < size && backingArray[leftChild(k)] != null) {
            if (backingArray[k].compareTo(backingArray[leftChild(k)]) > 0) {
                swap(k, leftChild(k));
                buildHeap(leftChild(k));
            }
        } else if (k < size && backingArray[rightChild(k)] != null) {
            if (backingArray[k].compareTo(backingArray[rightChild(k)]) > 0) {
                swap(k, rightChild(k));
                buildHeap(rightChild(k));
            }
        }
    }

    /**
     *
     * @param index Current index
     * @return Parent index
     */
    private int parent(int index) {
        return index / 2;
    }

    /**
     *
     * @param index Current index
     * @return Left Child index
     */
    private int leftChild(int index) {
        return (2 * index);
    }

    /**
     *
     * @param index Current index
     * @return Right Child index
     */
    private int rightChild(int index) {
        return (2 * index) + 1;
    }

    /**
     *
     * @param first First index
     * @param second Second index
     */
    private void swap(int first, int second) {
        T tmp;
        tmp = backingArray[first];
        backingArray[first] = backingArray[second];
        backingArray[second] = tmp;
    }
}
