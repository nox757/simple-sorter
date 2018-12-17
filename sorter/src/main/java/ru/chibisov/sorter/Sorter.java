package ru.chibisov.sorter;

import java.util.List;

public interface Sorter<T> {
    T[] sort(T[] array);
    List<T> sort(List<T> collection);
}
