package ru.chibisov.sorter.strategy.pivot;

import java.util.List;

public class LastElementPivotStrategy<T> implements PivotStrategy<T> {
    @Override
    public int getPivot(T[] array, int first, int last) {
        return last;
    }

    @Override
    public int getPivot(List<T> collection, int first, int last) {
        return last;
    }
}
