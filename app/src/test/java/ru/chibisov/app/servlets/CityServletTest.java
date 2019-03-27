package ru.chibisov.app.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.servicies.CityService;

public class CityServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletConfig sg;
    @Mock
    ServletContext sc;
    @Mock
    CityService cityService;

    CityServlet cityServlet;
    StringWriter sw;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        mockCityService();
        when(sc.getAttribute("cityService")).thenReturn(cityService);

        cityServlet = new CityServlet() {
            public ServletContext getServletContext() {
                return sc;
            }
        };
        cityServlet.init(sg);
    }

    private void mockCityService() {
        List<CityDTO> cityList = new ArrayList<>();
        cityList.add(
                new CityDTO().setId(0L)
                        .setName("Name1")
                        .setMayor(1L)
                        .setRegion(2L)
                        .setAttributes(Arrays.asList(4L, 5L))
        );
        cityList.add(
                new CityDTO().setId(1L)
                        .setName("Name2")
                        .setMayor(1L)
                        .setRegion(2L)
                        .setAttributes(Arrays.asList(4L, 5L))
        );
        cityList.add(
                new CityDTO().setId(2L)
                        .setName("Name3")
                        .setMayor(1L)
                        .setRegion(2L)
                        .setAttributes(Arrays.asList(4L, 5L))
        );
        when(cityService.getAll()).thenReturn(cityList);
        when(cityService.getById(0L)).thenReturn(cityList.get(0));
        when(cityService.getById(2L)).thenReturn(cityList.get(2));
        when(cityService.getById(2L)).thenReturn(cityList.get(2));
    }

    @Test
    public void testDoGetAll() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/");
        cityServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(result, "[{\"city_id\":0,\"name\":\"Name1\",\"mayor_id\":1,\"region_id\":2,\"attribute_ids\":[4,5]},{\"city_id\":1,\"name\":\"Name2\",\"mayor_id\":1,\"region_id\":2,\"attribute_ids\":[4,5]},{\"city_id\":2,\"name\":\"Name3\",\"mayor_id\":1,\"region_id\":2,\"attribute_ids\":[4,5]}]");
        verify(response, times(2)).setStatus(400);
    }


}
