package ru.chibisov.app.servicies.impl;

import dai.MayorDao;
import ru.chibisov.app.servicies.MayorService;

public class MayorServiceImpl implements MayorService {

    private MayorDao mayorDao;

    public MayorServiceImpl(MayorDao mayorDao) {
        this.mayorDao = mayorDao;
    }
}
