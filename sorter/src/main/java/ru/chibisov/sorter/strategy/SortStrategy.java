package ru.chibisov.sorter.strategy;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {
    void sort(T[] array, Comparator<? super T> comparator);
    void sort(List<T> collection, Comparator<? super T> comparator);
}
