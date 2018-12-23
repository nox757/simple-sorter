package ru.chibisov.sorter;

import ru.chibisov.sorter.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Immutable sorter list or array
 * @param <T> type of sorting elements
 */
public class ImmutableSorter<T> extends BaseSorter<T> {

    ImmutableSorter(SortStrategy<T> sortStrategy, Comparator<? super T> comparator) {
        super(sortStrategy, comparator);
    }

    public T[] sort(T[] array) {
        T[] result = Arrays.copyOf(array, array.length);
        super.sort(result);
        return result;
    }

    public List<T> sort(List<T> collection) {
        List<T> result = new ArrayList<>(collection);
        super.sort(result);
        return result;
    }
}
