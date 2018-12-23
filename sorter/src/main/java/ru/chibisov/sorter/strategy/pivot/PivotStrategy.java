package ru.chibisov.sorter.strategy.pivot;

import java.util.List;

/**
 * Chosen index of pivot element
 * @param <T> type of list elements
 */
public interface PivotStrategy<T> {

    /**
     * Get pivot element in range of list
     * @param collection list of elements
     * @param first first index of element in chosen range in list
     * @param last last index of element in chosen range in list
     * @return index pivot element in list
     */
    int getPivot(List<T> collection, int first, int last);
}
