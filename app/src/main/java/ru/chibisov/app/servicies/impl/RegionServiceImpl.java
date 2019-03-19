package ru.chibisov.app.servicies.impl;

import dai.RegionDao;
import entities.Region;
import ru.chibisov.app.dto.RegionDTO;
import ru.chibisov.app.dto.mapper.RegionMapperDTO;
import ru.chibisov.app.servicies.RegionService;

import java.util.ArrayList;
import java.util.List;

public class RegionServiceImpl implements RegionService {

    private static final RegionMapperDTO mapperDTO = new RegionMapperDTO();
    private RegionDao regionDao;
//    private CountryDao countryDao;

    public RegionServiceImpl(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public List<RegionDTO> getAll() {
        List<Region> regions = regionDao.getAll();
        if(regions == null) {
            return null;
        }
        List<RegionDTO> regionDtos = new ArrayList<>();
        for(Region region : regions) {
            regionDtos.add(mapperDTO.mapToDto(region));
        }
        return regionDtos;
    }

    @Override
    public RegionDTO getById(Long id) {
        Region region = regionDao.read(id);
        if(region == null) {
            return null;
        }
        return mapperDTO.mapToDto(region);
    }

    @Override
    public RegionDTO update(RegionDTO region) {
        Region regionEntity = mapperDTO.mapFromDto(region);
        regionDao.update(regionEntity);
        return mapperDTO.mapToDto(regionDao.read(region.getId()));
    }

    @Override
    public RegionDTO create(RegionDTO region) {
        Long id = regionDao.create(mapperDTO.mapFromDto(region));
        return mapperDTO.mapToDto(regionDao.read(id));
    }

    @Override
    public void delete(RegionDTO region) {
        Region regionEntity = mapperDTO.mapFromDto(region);
        regionDao.delete(regionEntity);
    }
}
