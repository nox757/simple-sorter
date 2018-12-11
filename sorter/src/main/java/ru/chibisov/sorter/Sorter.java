package ru.chibisov.sorter;

import java.util.List;

public interface Sorter<T> {
    void sort(T[] array);
    void sort(List<T> collection);
}
