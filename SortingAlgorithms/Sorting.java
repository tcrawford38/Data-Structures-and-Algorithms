package homework08;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Thomas Crawford
 * @version 1.0
 * @userid tcrawford38
 * @GTID 903398010
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            T temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or Comparator is null");
        }
        boolean swapsMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        int swapCounter = 0;
        while (swapsMade) {
            swapsMade = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    swapCounter = i;
                }
            }
            endInd = swapCounter;
            if (swapsMade) {
                swapsMade = false;
                for (int i = endInd; i > startInd; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapsMade = true;
                        swapCounter = i;
                    }
                }
            }
            startInd = swapCounter;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        if (arr.length > 1) {
            int length = arr.length;
            int midIndex = length / 2;
            T[] left = (T[]) new Object[midIndex];
            T[] right = (T[]) new Object[length - midIndex];
            for (int i = 0; i < midIndex; i++) {
                left[i] = arr[i];
            }
            for (int i = midIndex; i < length; i++) {
                right[i - midIndex] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < midIndex && j < length - midIndex) {
                if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
                k++;
            }
            while (i < midIndex) {
                arr[k] = left[i];
                i++;
                k++;
            }
            while (j < length - midIndex) {
                arr[i + j] = right[j];
                j++;
                k++;
            }
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                    "Array or comparator or rand is null");
        }
        quickSorthelp(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     *
     * @param arr Array to be sorted
     * @param comparator Comparator used to compare data
     * @param rand Random object used to select pivots
     * @param lower Lower index
     * @param higher Higher index
     * @param <T> Data typee to sort
     */
    private static <T> void quickSorthelp(T[] arr, Comparator<T> comparator,
                                          Random rand, int lower, int higher) {
        if (lower < higher) {
            int pivotIdx = rand.nextInt(higher - lower + 1) + lower;
            int i = lower + 1;
            int j = higher;
            if (higher - lower != 1) {
                T temp = arr[lower];
                arr[lower] = arr[pivotIdx];
                arr[pivotIdx] = temp;
                while (i <= j) {
                    while (i <= j
                            && comparator.compare(arr[i], arr[lower]) <= 0) {
                        i++;
                    }
                    while (i <= j
                            && comparator.compare(arr[j], arr[lower]) >= 0) {
                        j--;
                    }
                    if (i < j) {
                        T temp2 = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp2;
                        i++;
                        j--;
                    }
                }
                T temp3 = arr[lower];
                arr[lower] = arr[j];
                arr[j] = temp3;
            } else {
                if (comparator.compare(arr[lower], arr[higher]) > 0) {
                    T temp4 = arr[higher];
                    arr[higher] = arr[lower];
                    arr[lower] = temp4;
                }
            }
            quickSorthelp(arr, comparator, rand, lower, j - 1);
            quickSorthelp(arr, comparator, rand, j + 1, higher);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        LinkedList<Integer>[] buckets = (
                LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        long m = Math.abs((long) arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs((long) arr[i]) > m) {
                m = Math.abs((long) arr[i]);
            }
        }
        int idx = 0;
        while (m != 0) {
            idx++;
            m = m / 10;
        }
        int div = 1;
        for (int i = 0; i < idx; i++) {
            for (int j = 0; j < arr.length; j++) {
                int n = arr[j] / div;
                int dig = n - 10 * (n / 10) + 9;
                buckets[dig].add(arr[j]);
            }
            div *= 10;
            int numBuckets = 0;
            for (int j = 0; j < arr.length; j++) {
                if (!buckets[numBuckets].isEmpty()) {
                    arr[j] = buckets[numBuckets].remove();
                } else {
                    numBuckets++;
                    j--;
                }
            }
        }
    }
}