package ru.chibisov.sorter;

import ru.chibisov.sorter.quick.Pivotal;
import ru.chibisov.sorter.quick.pivot.PivotStrategy;

public class QuickSortingStrategy<T extends Comparable<? super T>> implements Pivotal<T>, SortingStrategy<T> {

    private PivotStrategy<T> pivotStrategy;

    public void setPivotStrategy(PivotStrategy<T> pivotStrategy) {
        this.pivotStrategy = pivotStrategy;
    }

    public PivotStrategy<T> getPivotStrategy() {
        return pivotStrategy;
    }

    @Override
    public void sort(T[] array) {
        quickSort(array, 0 ,array.length - 1 );
    }

    private void quickSort(T[] array, int first, int last) {
        if (array.length == 0)
            return;
        if (first >= last)
            return;

        int pivot = pivotStrategy.getPivot(array, first, last);
        T opora = array[pivot];

        int i = first, j = last;
        while (i <= j) {
            while (array[i].compareTo(opora) < 0) {
                i++;
            }
            while (array[j].compareTo(opora) > 0) {
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
            quickSort(array, first, j);
        }

        if (last > i) {
            quickSort(array, i, last);
        }
    }


}
