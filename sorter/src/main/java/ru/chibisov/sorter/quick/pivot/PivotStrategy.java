package ru.chibisov.sorter.quick.pivot;

import java.util.List;

public interface PivotStrategy<T> {
    int getPivot(T[] array, int first, int last);
    int getPivot(List<T> collection, int first, int last);
}
