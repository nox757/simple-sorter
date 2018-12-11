package ru.chibisov;


import ru.chibisov.sorter.QuickSortStrategy;
import ru.chibisov.sorter.SortBuilder;
import ru.chibisov.sorter.Sorter;
import ru.chibisov.sorter.quick.pivot.FirstElementPivotStrategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> sequence = Arrays.asList(args);

        Sorter<String> sorter = SortBuilder.newBuilder()
                .sortStrategy(new QuickSortStrategy())
                .pivotStrategy(new FirstElementPivotStrategy())
//                .returnOnlyView(true)
                .build(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });

        sorter.sort(sequence);
//        System.out.println(Joiner.on(" ").join(sequence));
    }

}

