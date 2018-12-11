package ru.chibisov.sorter;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(T[] array, Comparator<? super T> c) {
        int sizeArray = array.length;
        T temp;
        for(int i = 0; i < sizeArray; i++) {
            for (int j = sizeArray - 1; j > i; j--) {
                if(c.compare(array[j-1], array[j]) > 0) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }

            }
        }
    }

    @Override
    public void sort(List<T> collection, Comparator<? super T> c) {
        int sizeArray = collection.size();
        T temp;
        for(int i = 0; i < sizeArray; i++) {
            for (int j = sizeArray - 1; j > i; j--) {
                if(c.compare(collection.get(j-1), collection.get(j)) > 0) {
                    temp = collection.get(i);
                    collection.set(i, collection.get(j));
                    collection.set(j, temp);
                }

            }
        }
    }
}
