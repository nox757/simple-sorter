package ru.chibisov.sorter;

import ru.chibisov.sorter.quick.Pivotal;
import ru.chibisov.sorter.quick.pivot.PivotStrategy;

public class SorterBuilder {

    private SortingStrategy sortingStrategy;
    private PivotStrategy pivotStrategy;

    private  SorterBuilder() {
    }

    public static SorterBuilder newBuilder() {
        return new SorterBuilder();
    }

    public SorterBuilder sortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
        return this;
    }

    public  SorterBuilder pivotStrategy(PivotStrategy pivotStrategy) {
        this.pivotStrategy = pivotStrategy;
        return this;
    }

    public <T extends Comparable<? super T>> Sorter<T> build() {
        if(pivotStrategy != null) {
            ((Pivotal<T>) sortingStrategy).setPivotStrategy((PivotStrategy<T>)pivotStrategy);
        }
        return new Sorter<>((SortingStrategy<T>)sortingStrategy);
    }
}
