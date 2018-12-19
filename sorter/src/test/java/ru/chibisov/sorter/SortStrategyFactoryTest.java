package ru.chibisov.sorter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.chibisov.sorter.strategy.BubbleSortStrategy;
import ru.chibisov.sorter.strategy.QuickSortStrategy;
import ru.chibisov.sorter.strategy.SortStrategy;
import ru.chibisov.sorter.strategy.SortStrategyType;
import ru.chibisov.sorter.strategy.pivot.PivotStrategyType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SortStrategyFactoryTest {

    private SortStrategyType type;
    private PivotStrategyType pivot;
    private Class<? extends SortStrategy> expected;
    private Class<? extends Exception> expectedException;
    private String expectedExceptionMsg;

    public SortStrategyFactoryTest(SortStrategyType type, PivotStrategyType pivot, Class<? extends SortStrategy> expected,
                                   Class<? extends Exception> expectedException, String expectedExceptionMsg) {
        this.type = type;
        this.pivot = pivot;
        this.expected = expected;
        this.expectedException = expectedException;
        this.expectedExceptionMsg = expectedExceptionMsg;
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(
                new Object[][]{
                        {null, null, null, IllegalArgumentException.class, "Sort type can not null"},
                        {SortStrategyType.QUICK, null, null, IllegalArgumentException.class, "Pivot type can not null"},
                        {SortStrategyType.BUBBLE, null, BubbleSortStrategy.class, null, null},
                        {SortStrategyType.BUBBLE, PivotStrategyType.FIRST_ELEMENT, BubbleSortStrategy.class, null, null},
                        {SortStrategyType.QUICK, PivotStrategyType.FIRST_ELEMENT, QuickSortStrategy.class, null, null},
                        {SortStrategyType.QUICK, PivotStrategyType.LAST_ELEMENT, QuickSortStrategy.class, null, null}
                });
    }

    @Test
    public void getSortStrategy() {
        if (expectedException != null) {
            expectedEx.expect(expectedException);
            expectedEx.expectMessage(expectedExceptionMsg);
        }
        SortStrategy strategy = SortStrategyFactory.getSortStrategy(type, pivot);
        assertEquals(expected, strategy.getClass());
    }


}