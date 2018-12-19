package ru.chibisov.sorter.strategy.pivot;

import java.util.List;

public interface PivotStrategy<T> {
    int getPivot(List<T> collection, int first, int last);
}
