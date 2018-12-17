package ru.chibisov.sorter.strategy;

import ru.chibisov.sorter.strategy.pivot.PivotStrategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy<T> implements SortStrategy<T> {

    private PivotStrategy<T> pivotStrategy;

    public QuickSortStrategy(PivotStrategy<T> pivotStrategy) {
        this.pivotStrategy = pivotStrategy;
    }

    @Override
    public void sort(T[] array, Comparator<? super T> c) {
        List<T> collection = Arrays.asList(array);
        quickSort(collection, 0 ,array.length - 1, c );
        for(int i = 0; i < array.length; i++) {
            array[i] = collection.get(i);
        }
    }

    @Override
    public void sort(List<T> collection, Comparator<? super T> c) {
        quickSort(collection, 0 ,collection.size() - 1, c );
    }

    private void quickSort(List<T> collection, int first, int last, Comparator<? super T> c) {
        if (collection.size() == 0)
            return;
        if (first >= last)
            return;

        int pivot = pivotStrategy.getPivot(collection, first, last);
        T opora = collection.get(pivot);

        int i = first, j = last;
        while (i <= j) {
            while (c.compare(collection.get(i), opora) < 0) {
                i++;
            }
            while (c.compare(collection.get(j), opora) > 0) {
                j--;
            }

            if (i <= j) {
                T temp = collection.get(i);
                collection.set(i, collection.get(j));
                collection.set(j, temp);
                i++;
                j--;
            }
        }

        if (first < j) {
            quickSort(collection, first, j, c);
        }

        if (last > i) {
            quickSort(collection, i, last, c);
        }
    }
}
