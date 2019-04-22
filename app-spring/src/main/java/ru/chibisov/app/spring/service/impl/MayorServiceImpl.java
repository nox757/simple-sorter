package ru.chibisov.app.spring.service.impl;

import dai.MayorDao;
import entities.Mayor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.MayorDTO;
import ru.chibisov.app.service.MayorService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.List;

@Service
public class MayorServiceImpl implements MayorService {
    
    @Autowired
    private MayorDao mayorDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<MayorDTO> getAll() {
        return mapper.mapAsList(mayorDao.getAll(), MayorDTO.class);
    }

    @Override
    public MayorDTO getById(Long id) {
        return mapper.map(mayorDao.read(id), MayorDTO.class);
    }

    @Override
    public MayorDTO update(MayorDTO mayor) {
        mayorDao.update(mapper.map(mayor, Mayor.class));
        return mapper.map(mayorDao.read(mayor.getId()), MayorDTO.class);
    }

    @Override
    public MayorDTO create(MayorDTO mayor) {
        Long id = mayorDao.create(mapper.map(mayor, Mayor.class));
        return mapper.map(mayorDao.read(id), MayorDTO.class);
    }

    @Override
    public void delete(MayorDTO mayor) {
        mayorDao.delete(mapper.map(mayor, Mayor.class));
    }
}
