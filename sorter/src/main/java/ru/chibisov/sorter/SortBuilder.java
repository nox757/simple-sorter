package ru.chibisov.sorter;

import ru.chibisov.sorter.quick.pivot.PivotStrategy;

import java.util.Comparator;

public class SortBuilder {

    private SortStrategy sortStrategy;
    private PivotStrategy pivotStrategy;

    private SortBuilder() {
    }

    public static SortBuilder newBuilder() {
        return new SortBuilder();
    }

    public SortBuilder sortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
        return this;
    }

    public SortBuilder pivotStrategy(PivotStrategy pivotStrategy) {
        this.pivotStrategy = pivotStrategy;
        return this;
    }

    public <T> BaseSorter<T> build(Comparator<? super T> comparator) {

        if(comparator == null) {
            throw new IllegalArgumentException("Add comparator");
        }

        if(sortStrategy == null) {
            throw new IllegalArgumentException("Add sortStrategy");
        }

        if(pivotStrategy != null) {
            try {
                ((QuickSortStrategy<T>) sortStrategy).setPivotStrategy((PivotStrategy<T>)pivotStrategy);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("Strategy haven't Pivot", ex);
            }
        }

        return new BaseSorter<T>((SortStrategy<T>) sortStrategy, comparator);
    }
}
