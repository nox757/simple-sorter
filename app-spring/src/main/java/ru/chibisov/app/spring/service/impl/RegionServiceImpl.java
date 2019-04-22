package ru.chibisov.app.spring.service.impl;

import dai.RegionDao;
import entities.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.RegionDTO;
import ru.chibisov.app.service.RegionService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<RegionDTO> getAll() {
        return mapper.mapAsList(regionDao.getAll(), RegionDTO.class);
    }

    @Override
    public RegionDTO getById(Long id) {
        return mapper.map(regionDao.read(id), RegionDTO.class);
    }

    @Override
    public RegionDTO update(RegionDTO region) {
        regionDao.update(mapper.map(region, Region.class));
        return mapper.map(regionDao.read(region.getId()), RegionDTO.class);
    }

    @Override
    public RegionDTO create(RegionDTO region) {
        Long id = regionDao.create(mapper.map(region, Region.class));
        return mapper.map(regionDao.read(id), RegionDTO.class);
    }

    @Override
    public void delete(RegionDTO region) {
        regionDao.delete(mapper.map(region, Region.class));
    }
}
