package ru.chibisov.sorter;

import ru.chibisov.sorter.strategy.BubbleSortStrategy;
import ru.chibisov.sorter.strategy.QuickSortStrategy;
import ru.chibisov.sorter.strategy.SortStrategy;
import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.FirstElementPivotStrategy;
import ru.chibisov.sorter.strategy.pivot.LastElementPivotStrategy;
import ru.chibisov.sorter.strategy.pivot.PivotStrategy;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

public class SortStrategyFactory {

    static <T> SortStrategy<T> getSortStrategy(SortStrategyType strategy, PivotStrategyType pivotType) {

        if (strategy == null) {
            throw new NullPointerException("Sort type can not null");
        }

        SortStrategy<T> realization;
        switch (strategy) {
            case BUBBLE:
                realization = new BubbleSortStrategy<T>();
                break;
            case QUICK:
                realization = new QuickSortStrategy<T>(SortStrategyFactory.<T>pivot(pivotType));
                break;
            default:
                throw new RuntimeException("Unknown sort type");
        }
        return realization;
    }

    private static <T> PivotStrategy<T> pivot(PivotStrategyType type) {

        if (type == null) {
            throw new NullPointerException("Pivot type can not null");
        }

        PivotStrategy<T> pivot;
        switch (type) {
            case FIRST_ELEMENT:
                pivot = new FirstElementPivotStrategy<T>();
                break;
            case LAST_ELEMENT:
                pivot = new LastElementPivotStrategy<T>();
                break;
            default:
                pivot = new FirstElementPivotStrategy<T>();
        }
        return pivot;
    }

}
