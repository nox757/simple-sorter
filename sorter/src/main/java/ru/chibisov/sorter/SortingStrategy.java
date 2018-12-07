package ru.chibisov.sorter;

public interface SortingStrategy<T> {
    void sort(T[] array);
}
