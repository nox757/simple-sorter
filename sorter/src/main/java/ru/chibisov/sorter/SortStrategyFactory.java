package ru.chibisov.sorter;

import ru.chibisov.sorter.strategy.BubbleSortStrategy;
import ru.chibisov.sorter.strategy.QuickSortStrategy;
import ru.chibisov.sorter.strategy.SortStrategy;
import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.FirstElementPivotStrategy;
import ru.chibisov.sorter.strategy.pivot.LastElementPivotStrategy;
import ru.chibisov.sorter.strategy.pivot.PivotStrategy;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

class  SortStrategyFactory {

    static <T> SortStrategy<T> getSortStrategy(SortStrategyType strategy, PivotStrategyType pivotType) {

        if (strategy == null) {
            throw new IllegalArgumentException("Sort type can not null");
        }

        SortStrategy<T> realization;
        switch (strategy) {
            case BUBBLE:
                realization = new BubbleSortStrategy<>();
                break;
            case QUICK:
                realization = new QuickSortStrategy<>(SortStrategyFactory.<T>pivot(pivotType));
                break;
            default:
                throw new RuntimeException("Unknown sort type");
        }
        return realization;
    }

    private static <T> PivotStrategy<T> pivot(PivotStrategyType type) {

        if (type == null) {
            throw new IllegalArgumentException("Pivot type can not null");
        }

        PivotStrategy<T> pivot;
        switch (type) {
            case FIRST_ELEMENT:
                pivot = new FirstElementPivotStrategy<>();
                break;
            case LAST_ELEMENT:
                pivot = new LastElementPivotStrategy<>();
                break;
            default:
                throw new RuntimeException("Unknown sort type");
        }
        return pivot;
    }

}
