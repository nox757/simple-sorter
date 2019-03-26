package ru.chibisov.app.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFullName() throws IOException, ServletException {

//        ServletContext context = mock(ServletContext.class);
//        when(request.getServletContext()).thenReturn(context);

        when(request.getPathInfo()).thenReturn("/");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        List<CityDTO> list = new ArrayList<>();
        list.add(new CityDTO().setName("neeee").setMayor(1L).setRegion(3L));
        when(cityService.getAll()).thenReturn(list);
        when(sc.getAttribute("cityService")).thenReturn(cityService);

        CityServlet myServlet = new CityServlet() {
            public ServletContext getServletContext() {
                return sc;
            }
        };
//        when(myServlet.getServletContext()).thenReturn(sc);
        myServlet.init(sg);
        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(result, new String("Full Name: Vinod Kashyap"));

    }
}
