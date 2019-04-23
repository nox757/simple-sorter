package ru.chibisov.app.spring.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.chibisov.app.spring.controller.AttributeController;
import ru.chibisov.app.spring.controller.AttributeTypeController;

import static org.junit.Assert.assertEquals;

public class AttributeTypeIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private AttributeTypeController controller;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAttributeList() throws Exception {
        String uri = "/attributetypes";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!_"+content);
    }
}
