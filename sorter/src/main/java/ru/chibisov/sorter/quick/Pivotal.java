package ru.chibisov.sorter.quick;


import ru.chibisov.sorter.quick.pivot.PivotStrategy;

public interface Pivotal<T> {
    void setPivotStrategy(PivotStrategy<T> pivotStrategy);
}
