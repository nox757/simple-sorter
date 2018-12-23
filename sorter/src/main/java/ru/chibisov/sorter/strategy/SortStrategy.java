package ru.chibisov.sorter.strategy;

import java.util.Comparator;
import java.util.List;

/**
 * Algorithm of sorting elements
 * @param <T> type of sorting elements
 */
public interface SortStrategy<T> {

    /**
     * Sorting array of elements
     * @param array array of sorting elements
     * @param comparator method to compare two elements
     */
    void sort(T[] array, Comparator<? super T> comparator);

    /**
     * Sorting list of elements
     * @param collection list of sorting elements
     * @param comparator method to compare two elements
     */
    void sort(List<T> collection, Comparator<? super T> comparator);
}
