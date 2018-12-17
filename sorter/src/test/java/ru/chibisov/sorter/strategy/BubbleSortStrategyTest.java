package ru.chibisov.sorter.strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class BubbleSortStrategyTest {

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
    public void sortStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<String>();
        assertNotNull(target);
    }

    @Test
    public void sortListStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<String>();
        List<String> source = Arrays.asList("3", "2", "1");
        target.sort(source, comparator);
        assertArrayEquals(new String[]{"1", "2", "3"}, source.toArray());
    }

    @Test
    public void sortArrayStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<String>();
        String[] source = new String[]{"3", "2", "1"};
        target.sort(source, comparator);
        assertArrayEquals(new String[]{"1", "2", "3"}, source);
    }
}
