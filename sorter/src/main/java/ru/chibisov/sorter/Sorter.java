package ru.chibisov.sorter;

import java.util.List;

/**
 * Sorting of elements
 * @param <T> type of sorting elements
 */
public interface Sorter<T> {
    /**
     * Sorting array of elements
     * @param array array of elements to sort
     * @return array after sort
     */
    T[] sort(T[] array);

    /**
     * Sorting list of elements
     * @param collection list of elements
     * @return list after sorting
     */
    List<T> sort(List<T> collection);
}
