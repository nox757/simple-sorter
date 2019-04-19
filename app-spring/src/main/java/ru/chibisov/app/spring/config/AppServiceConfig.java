package ru.chibisov.app.spring.config;

import dai.AttributeCityDao;
import dai.AttributeTypeDao;
import dai.CityDao;
import dai.CountryDao;
import dai.MayorDao;
import dai.RegionDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.chibisov.app.service.AttributeService;
import ru.chibisov.app.service.AttributeTypeService;
import ru.chibisov.app.service.CityService;
import ru.chibisov.app.service.CountryService;
import ru.chibisov.app.service.MayorService;
import ru.chibisov.app.service.RegionService;
import ru.chibisov.app.service.impl.AttributeServiceImpl;
import ru.chibisov.app.service.impl.AttributeTypeServiceImpl;
import ru.chibisov.app.service.impl.CityServiceImpl;
import ru.chibisov.app.service.impl.CountryServiceImpl;
import ru.chibisov.app.service.impl.MayorServiceImpl;
import ru.chibisov.app.service.impl.RegionServiceImpl;

@Configuration
@EnableWebMvc
@ComponentScan("ru.chibisov.app")
@Import(DaoConfig.class)
public class AppServiceConfig {

    @Bean
    public CountryService getCountryService(CountryDao countryDao) {
        return new CountryServiceImpl(countryDao);
    }

    @Bean
    public RegionService getRegionService(RegionDao regionDao) {
        return new RegionServiceImpl(regionDao);
    }

    @Bean
    public CityService getCityService(CityDao cityDao, MayorDao mayorDao) {
        return new CityServiceImpl(cityDao, mayorDao);
    }

    @Bean
    public AttributeTypeService getAttributeTypeService(AttributeTypeDao attributeTypeDao) {
        return new AttributeTypeServiceImpl(attributeTypeDao);
    }

    @Bean
    public AttributeService getAttributeService(AttributeCityDao attributeDao) {
        return new AttributeServiceImpl(attributeDao);
    }

    @Bean
    public MayorService getMayorService(MayorDao mayorDao) {
        return new MayorServiceImpl(mayorDao);
    }
}
