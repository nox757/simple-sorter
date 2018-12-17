package ru.chibisov.sorter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class SortBuilderTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void newBuilder() {
        assertNotNull(SortBuilder.newBuilder());
    }

    @Test
    public void sortStrategy() {
        SortBuilder target = SortBuilder.newBuilder().sortStrategy(SortStrategyType.QUICK);
        assertNotNull(target);
    }

    @Test
    public void pivotStrategy() {
        SortBuilder target = SortBuilder.newBuilder().pivotStrategy(PivotStrategyType.FIRST_ELEMENT);
        assertNotNull(target);
    }

    @Test
    public void build() {
        Sorter<String> sorter = SortBuilder.newBuilder()
                .pivotStrategy(PivotStrategyType.FIRST_ELEMENT)
                .sortStrategy(SortStrategyType.QUICK)
                .build(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });

        List<String> source = Arrays.asList("3", "2", "1");
        sorter.sort(source);
        assertArrayEquals(new String[]{"1", "2", "3"}, source.toArray());
    }

    @Test
    public void buildWithoutStrategy() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Add sortStrategy");
        SortBuilder.newBuilder()
                .build(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });
    }

    @Test
    public void buildWithoutPivot() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Pivot type can not null");
        SortBuilder.newBuilder()
                .sortStrategy(SortStrategyType.QUICK)
                .build(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });
    }

    @Test
    public void buildImmutable() {
        Sorter<String> sorter = SortBuilder.newBuilder()
                .pivotStrategy(PivotStrategyType.FIRST_ELEMENT)
                .sortStrategy(SortStrategyType.QUICK)
                .buildImmutable(new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int x = Integer.parseInt(o1);
                        int y = Integer.parseInt(o2);

                        if (x == y) return 0;
                        return x > y ? 1 : -1;
                    }
                });

        List<String> source = Arrays.asList("3", "2", "1");
        List<String> result = sorter.sort(source);

        assertArrayEquals(new String[]{"1", "2", "3"}, result.toArray());
        assertNotSame(source, result);
    }

}
