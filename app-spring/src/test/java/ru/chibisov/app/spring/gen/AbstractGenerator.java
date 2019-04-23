package ru.chibisov.app.spring.gen;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenerator<M> implements RandomGenerator<M> {

    final Class<M> type;

    public AbstractGenerator(Class<M> type) {
        this.type = type;
    }

    @Override
    public M fill(M model) {
        return model;
    }

    @Override
    public M getData() {
        M model = null;
        try {
            model = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fill(model);
    }

    @Override
    public List<M> getList(int size) {
        List<M> models = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            models.add(getData());
        }
        return models;
    }
}
