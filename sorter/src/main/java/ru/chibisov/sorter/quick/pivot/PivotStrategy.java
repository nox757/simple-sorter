package ru.chibisov.sorter.quick.pivot;

public interface PivotStrategy<T> {
    int getPivot(T[] array,int first, int last);
}
