package ru.chibisov.app.servicies.impl;

import dai.MayorDao;
import entities.Mayor;
import ru.chibisov.app.dto.MayorDTO;
import ru.chibisov.app.dto.mapper.MayorMapperDTO;
import ru.chibisov.app.servicies.MayorService;

import java.util.ArrayList;
import java.util.List;

public class MayorServiceImpl implements MayorService {

    private static final MayorMapperDTO mapperDTO = new MayorMapperDTO();
    private MayorDao mayorDao;

    public MayorServiceImpl(MayorDao mayorDao) {
        this.mayorDao = mayorDao;
    }

    @Override
    public List<MayorDTO> getAll() {
        List<Mayor> mayors = mayorDao.getAll();
        if(mayors == null) {
            return null;
        }
        List<MayorDTO> mayorDtos = new ArrayList<>();
        for(Mayor mayor : mayors) {
            mayorDtos.add(mapperDTO.mapToDto(mayor));
        }
        return mayorDtos;
    }

    @Override
    public MayorDTO getById(Long id) {
        Mayor mayor = mayorDao.read(id);
        if(mayor == null) {
            return null;
        }
        return mapperDTO.mapToDto(mayor);
    }

    @Override
    public MayorDTO update(MayorDTO mayor) {
        Mayor mayorEntity = mapperDTO.mapFromDto(mayor);
        mayorDao.update(mayorEntity);
        return mapperDTO.mapToDto(mayorDao.read(mayor.getId()));
    }

    @Override
    public MayorDTO create(MayorDTO mayor) {
        Long id = mayorDao.create(mapperDTO.mapFromDto(mayor));
        return mapperDTO.mapToDto(mayorDao.read(id));
    }

    @Override
    public void delete(MayorDTO mayor) {
        Mayor mayorEntity = mapperDTO.mapFromDto(mayor);
        mayorDao.delete(mayorEntity);
    }
}
