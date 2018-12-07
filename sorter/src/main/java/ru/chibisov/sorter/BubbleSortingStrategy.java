package ru.chibisov.sorter;

public class BubbleSortingStrategy<T extends Comparable<? super T>> implements SortingStrategy<T> {

    @Override
    public void sort(T[] array) {
        int sizeArray = array.length;
        T temp;
        for(int i = 0; i < sizeArray; i++) {
            for (int j = sizeArray - 1; j > i; j--) {
                if(array[j-1].compareTo(array[j]) > 0) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }

            }
        }
    }
}
