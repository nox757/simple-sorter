package ru.chibisov.app.spring.controller;

import entities.AttributeCity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.service.AttributeService;
import ru.chibisov.app.spring.config.AppServiceConfig;
import ru.chibisov.app.spring.config.MapperConfig;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;
import ru.chibisov.app.spring.dto.mapper.MapperFacadeImpl;
import ru.chibisov.app.spring.dto.mapper.registrator.AttributeMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.CityMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.MayorMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.RegionMapperRegistrator;
import ru.chibisov.app.spring.gen.AttributeGenerator;
import ru.chibisov.app.spring.service.impl.AttributeServiceImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AttributeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AttributeController controller = new AttributeController();

    @Mock
    private AttributeService service;

    private MapperFacade mapper;

    private List<AttributeCity> expected = new AttributeGenerator(AttributeCity.class).getList(3);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MapperFactory mapperFactory =  new DefaultMapperFactory.Builder().build();
        new AttributeMapperRegistrator().register(mapperFactory);
        new CityMapperRegistrator().register(mapperFactory);
        new MayorMapperRegistrator().register(mapperFactory);
        new RegionMapperRegistrator().register(mapperFactory);
        mapper = new MapperFacadeImpl(mapperFactory);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAttributeList() throws Exception {
        String uri = "/attributes";
        System.out.println(expected.get(0).getName());
        System.out.println(mapper.mapAsList(expected, AttributeDTO.class).get(0).getName());
        when(service.getAll()).thenReturn(mapper.mapAsList(expected, AttributeDTO.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!_"+content);
    }
}
