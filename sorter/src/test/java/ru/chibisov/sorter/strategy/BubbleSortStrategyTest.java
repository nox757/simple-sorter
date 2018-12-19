package ru.chibisov.sorter.strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class BubbleSortStrategyTest {

    private Comparator<String> comparator = (s1, s2) -> {
        int x = Integer.parseInt(s1);
        int y = Integer.parseInt(s2);

        if (x == y) return 0;
        return x > y ? 1 : -1;
    };

    @Test
    public void sortStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<>();
        assertNotNull(target);
    }

    @Test
    public void sortListStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<>();
        List<String> source = Arrays.asList("3", "2", "1");
        target.sort(source, comparator);
        assertArrayEquals(new String[]{"1", "2", "3"}, source.toArray());
    }

    @Test
    public void sortArrayStrategy() {
        SortStrategy<String> target = new BubbleSortStrategy<>();
        String[] source = new String[]{"3", "2", "1"};
        target.sort(source, comparator);
        assertArrayEquals(new String[]{"1", "2", "3"}, source);
    }
}
