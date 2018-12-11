package ru.chibisov.sorter;

import ru.chibisov.sorter.quick.pivot.PivotStrategy;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy<T> implements SortStrategy<T> {

    private PivotStrategy<T> pivotStrategy;

    public void setPivotStrategy(PivotStrategy<T> pivotStrategy) {
        this.pivotStrategy = pivotStrategy;
    }

    public PivotStrategy<T> getPivotStrategy() {
        return pivotStrategy;
    }

    @Override
    public void sort(T[] array, Comparator<? super T> c) {
        quickSort(array, 0 ,array.length - 1, c );
    }

    private void quickSort(T[] array, int first, int last, Comparator<? super T> c) {
        if (array.length == 0)
            return;
        if (first >= last)
            return;

        int pivot = pivotStrategy.getPivot(array, first, last);
        T opora = array[pivot];

        int i = first, j = last;
        while (i <= j) {
            while (c.compare(array[i], opora) < 0) {
                i++;
            }
            while (c.compare(array[j], opora) > 0) {
                j--;
            }

            if (i <= j) {
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (first < j) {
            quickSort(array, first, j, c);
        }

        if (last > i) {
            quickSort(array, i, last, c);
        }
    }


    @Override
    public void sort(List<T> collection, Comparator<? super T> c) {
        //todo: add release method
    }
}
