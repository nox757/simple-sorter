package ru.chibisov.app;

import com.google.common.base.Joiner;
import ru.chibisov.sorter.SortBuilder;
import ru.chibisov.sorter.Sorter;
import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> sequence = Arrays.asList(args);

        Sorter<String> sorter = SortBuilder.newBuilder()
                .sortStrategy(SortStrategyType.QUICK)
                .pivotStrategy(PivotStrategyType.LAST_ELEMENT)
                .build(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });

        List<String> result = sorter.sort(sequence);
        System.out.print(Joiner.on(" ").join(result));
    }

}

