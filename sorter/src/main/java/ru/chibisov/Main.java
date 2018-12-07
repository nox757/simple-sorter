package ru.chibisov;

import ru.chibisov.sorter.BubbleSortingStrategy;
import ru.chibisov.sorter.QuickSortingStrategy;
import ru.chibisov.sorter.Sorter;
import ru.chibisov.sorter.SorterBuilder;
import ru.chibisov.sorter.quick.Pivotal;
import ru.chibisov.sorter.quick.pivot.FirstElementPivotStrategy;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Sorter<String> sorter  = SorterBuilder.newBuilder()
                .sortingStrategy(new QuickSortingStrategy<String>())
                .pivotStrategy(new FirstElementPivotStrategy())
                .build();

        Sorter<Integer> sorter1 = SorterBuilder.newBuilder()
                .sortingStrategy(new QuickSortingStrategy())
                .pivotStrategy(new FirstElementPivotStrategy())
                .build();

        Integer[] array = new Integer[]{2, 1, -1, 1, 0};
        sorter1.sort(array);

        System.out.println(Arrays.toString(array));

    }
}

