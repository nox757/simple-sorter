package ru.chibisov.sorter;

import ru.chibisov.sorter.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

public class BaseSorter<T> implements Sorter<T> {

    protected SortStrategy<T> sortStrategy;
    protected Comparator<? super T> comparator;

    public BaseSorter(SortStrategy<T> sortStrategy, Comparator<? super T> comparator) {
        this.sortStrategy = sortStrategy;
        this.comparator = comparator;
    }

    public T[] sort(T[] array) {
        this.sortStrategy.sort(array, comparator);
        return array;
    }

    public List<T> sort(List<T> collection) {
        this.sortStrategy.sort(collection, comparator);
        return collection;
    }

}
