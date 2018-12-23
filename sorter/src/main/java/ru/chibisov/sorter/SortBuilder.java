package ru.chibisov.sorter;

import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

import java.util.Comparator;

/**
 * Constructor of sorter of elements
 */
public class SortBuilder {

    private SortStrategyType sortStrategy;
    private PivotStrategyType pivotType;

    private SortBuilder() {
    }

    public static SortBuilder newBuilder() {
        return new SortBuilder();
    }

    public SortBuilder sortStrategy(SortStrategyType sortStrategyType) {
        this.sortStrategy = sortStrategyType;
        return this;
    }

    public SortBuilder pivotStrategy(PivotStrategyType pivotType) {
        this.pivotType = pivotType;
        return this;
    }

    public <T> BaseSorter<T> build(Comparator<? super T> comparator) {

        checkAllComponents(comparator);
        return new BaseSorter<>(SortStrategyFactory.<T>getSortStrategy(sortStrategy, pivotType),
                comparator);
    }

    public <T> ImmutableSorter<T> buildImmutable(Comparator<? super T> comparator) {

        checkAllComponents(comparator);
        return new ImmutableSorter<>(SortStrategyFactory.<T>getSortStrategy(sortStrategy, pivotType),
                comparator);
    }

    private void checkAllComponents(Comparator comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Add comparator");
        }

        if (sortStrategy == null) {
            throw new IllegalArgumentException("Add sortStrategy");
        }
    }
}
