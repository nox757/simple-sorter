package ru.chibisov.sorter;

import org.junit.Test;
import ru.chibisov.sorter.strategy.BubbleSortStrategy;
import ru.chibisov.sorter.strategy.SortStrategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class ImmutableSorterTest {

    private SortStrategy<String> sortStrategy = new BubbleSortStrategy<>();

    private Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String s, String t1) {
            int x = Integer.parseInt(s);
            int y = Integer.parseInt(t1);

            if (x == y) return 0;
            return x > y ? 1 : -1;
        }
    };

    @Test
    public void immutableSorter() {
        ImmutableSorter<String> target = new ImmutableSorter<>(sortStrategy, comparator);
        assertNotNull(target);
    }

    @Test
    public void sortArray() {

        ImmutableSorter<String> sorter = new ImmutableSorter<>(sortStrategy, comparator);

       String[] source = new String[]{"3", "2", "1"};
       String[] result = sorter.sort(source);

        assertArrayEquals(new String[]{"1", "2", "3"}, result);
        assertNotSame(source, result);
    }

    @Test
    public void sortList() {

        ImmutableSorter<String> sorter = new ImmutableSorter<>(sortStrategy, comparator);

        List<String> source = Arrays.asList("3", "2", "1");
        List<String> result = sorter.sort(source);

        assertArrayEquals(new String[]{"1", "2", "3"}, result.toArray());
        assertNotSame(source, result);
    }

}
