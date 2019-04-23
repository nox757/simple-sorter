package ru.chibisov.app.spring.gen;

import java.util.List;

public interface RandomGenerator<M> {

    M fill(M model);
    M getData();
    List<M> getList(int size);
}
