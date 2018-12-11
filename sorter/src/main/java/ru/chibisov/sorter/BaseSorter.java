package ru.chibisov.sorter;

import java.util.Comparator;
import java.util.List;

public class BaseSorter<T> implements Sorter<T> {

    private SortStrategy<T> sortStrategy;
    private Comparator<? super T> comparator;

    public BaseSorter(SortStrategy<T> sortStrategy, Comparator<? super T> comparator) {
        this.sortStrategy = sortStrategy;
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        this.sortStrategy.sort(array, comparator);
    }

    public void sort(List<T> collection) {
        this.sortStrategy.sort(collection, comparator);
    }
}
