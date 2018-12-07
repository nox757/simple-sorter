package ru.chibisov.sorter;

public class Sorter<T> {

    private SortingStrategy<T> sortingStrategy;

    public Sorter(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public SortingStrategy<T> getSortingStrategy() {
        return sortingStrategy;
    }

    public Sorter<T> setSortingStrategy(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
        return this;
    }

    public void sort(T[] array) {
        this.sortingStrategy.sort(array);
    }
}
