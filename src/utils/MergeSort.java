package utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * The MergeSort class provides static methods for performing merge sort on various data types.
 * It includes methods for sorting arrays of objects, strings by length, and extracting sorted keys from a map.
 */
public class MergeSort {

    /**
     * Sorts an ArrayList of Object arrays based on the distance (second element) using merge sort.
     *
     * @param list The ArrayList to be sorted.
     */
    public static void mergeSort(ArrayList<Object[]> list) {
        if (list.size() < 2) {
            return; // Base case: a list of zero or one elements is already sorted
        }
        ArrayList<Object[]> tempList = new ArrayList<>(list);
        mergeSort(list, tempList, 0, list.size() - 1);
    }

    /**
     * Recursively sorts an ArrayList of strings by their lengths and merges the sorted halves.
     *
     * @param list      The list of strings to be sorted.
     * @param tempList  Temporary list used for merging.
     * @param left      The starting index of the segment to be sorted.
     * @param right     The ending index of the segment to be sorted.
     */
    private static void mergeSortString(ArrayList<String> list, ArrayList<String> tempList, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortString(list, tempList, left, mid);
            mergeSortString(list, tempList, mid + 1, right);
            mergeString(list, tempList, left, mid, right);
        }
    }

    /**
     * Recursively sorts an ArrayList of Object arrays based on distance and merges the sorted halves.
     *
     * @param list      The list of Object arrays to be sorted.
     * @param tempList  Temporary list used for merging.
     * @param left      The starting index of the segment to be sorted.
     * @param right     The ending index of the segment to be sorted.
     */
    private static void mergeSort(ArrayList<Object[]> list, ArrayList<Object[]> tempList, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(list, tempList, left, mid);
            mergeSort(list, tempList, mid + 1, right);

            merge(list, tempList, left, mid, right);
        }
    }

    /**
     * Merges two sorted halves of an ArrayList of Object arrays based on distance.
     *
     * @param list      The list to be merged.
     * @param tempList  Temporary list used for merging.
     * @param left      The starting index of the left half.
     * @param mid       The ending index of the left half.
     * @param right     The ending index of the right half.
     */
    private static void merge(ArrayList<Object[]> list, ArrayList<Object[]> tempList, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            tempList.set(i, list.get(i));
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (compareDistance(tempList.get(i), tempList.get(j)) <= 0) {
                list.set(k++, tempList.get(i++));
            } else {
                list.set(k++, tempList.get(j++));
            }
        }

        while (i <= mid) {
            list.set(k++, tempList.get(i++));
        }

        while (j <= right) {
            list.set(k++, tempList.get(j++));
        }
    }

    /**
     * Compares the distances of two Object arrays.
     *
     * @param a The first Object array.
     * @param b The second Object array.
     * @return An integer representing the comparison result.
     */
    private static int compareDistance(Object[] a, Object[] b) {
        double distanceA = (Double) a[1];
        double distanceB = (Double) b[1];
        return Double.compare(distanceA, distanceB);
    }

    /**
     * Public method to sort an ArrayList of strings based on their lengths.
     *
     * @param list The ArrayList of strings to be sorted.
     */
    public static void mergeSortString(ArrayList<String> list) {
        if (list.size() < 2) {
            return; // Base case: a list of zero or one elements is already sorted
        }
        ArrayList<String> tempList = new ArrayList<>(list);
        mergeSortString(list, tempList, 0, list.size() - 1);
    }

    /**
     * Merges two sorted halves of an ArrayList of strings based on their lengths.
     *
     * @param list      The list to be merged.
     * @param tempList  Temporary list used for merging.
     * @param left      The starting index of the left half.
     * @param mid       The ending index of the left half.
     * @param right     The ending index of the right half.
     */
    private static void mergeString(ArrayList<String> list, ArrayList<String> tempList, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            tempList.set(i, list.get(i));
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (compareStringLength(tempList.get(i), tempList.get(j)) <= 0) {
                list.set(k++, tempList.get(i++));
            } else {
                list.set(k++, tempList.get(j++));
            }
        }

        while (i <= mid) {
            list.set(k++, tempList.get(i++));
        }

        while (j <= right) {
            list.set(k++, tempList.get(j++));
        }
    }

    /**
     * Compares the lengths of two strings.
     *
     * @param a The first string.
     * @param b The second string.
     * @return An integer representing the comparison result.
     */
    private static int compareStringLength(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }

    /**
     * Extracts and sorts the keys of a map based on the length of the keys.
     *
     * @param map The map whose keys are to be sorted.
     * @return An ArrayList of sorted keys.
     */
    public static ArrayList<String> mergeSortHashMap(Map<String, ?> map) {
        Set<String> keySet = map.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);
        mergeSortString(keyList); // Reuse the sorting method for Strings
        return keyList;
    }
}
