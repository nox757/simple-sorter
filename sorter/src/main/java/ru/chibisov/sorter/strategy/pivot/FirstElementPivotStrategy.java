package ru.chibisov.sorter.strategy.pivot;

import java.util.List;

public class FirstElementPivotStrategy<T> implements PivotStrategy<T> {

    @Override
    public int getPivot(List<T> collection, int first, int last) {
        return first;
    }
}
