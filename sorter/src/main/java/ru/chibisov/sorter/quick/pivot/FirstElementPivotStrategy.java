package ru.chibisov.sorter.quick.pivot;

public class FirstElementPivotStrategy<T> implements PivotStrategy<T> {

    @Override
    public int getPivot(T[] array, int first, int last) {
        return first;
    }
}
