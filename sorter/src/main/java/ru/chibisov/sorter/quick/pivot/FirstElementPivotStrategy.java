package ru.chibisov.sorter.quick.pivot;

import java.util.List;

public class FirstElementPivotStrategy<T> implements PivotStrategy<T> {

    @Override
    public int getPivot(T[] array, int first, int last) {
        return first;
    }

    @Override
    public int getPivot(List<T> collection, int first, int last) {
        return first;
    }
}