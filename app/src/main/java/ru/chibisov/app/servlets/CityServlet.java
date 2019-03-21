package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.servicies.CityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.Attribute;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CityServlet extends HttpServlet {

    private CityService cityService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cityService = (CityService) getServletContext().getAttribute("cityService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<CityDTO> citys = cityService.getAll();
                writer.println(gson.toJson(citys));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long cityId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                CityDTO city = cityService.getById(cityId);
                if (city == null) {
                    return;
                }
                writer.println(gson.toJson(city));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 3 && subPaths[2].equals("attributes")) {
            Long cityId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                List<AttributeDTO> attributes = cityService.getCityAttributes(cityId);
//                if (city == null) {
//                    writer.println(gson.toJson(new JsonArray()));
//
//                }
                writer.println(gson.toJson(attributes));
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String jsonString = ServletUtil.getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                CityDTO city = gson.fromJson(jsonString, CityDTO.class);
                city = cityService.create(city);
                writer.println(gson.toJson(city));
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String jsonString = ServletUtil.getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try (PrintWriter writer = resp.getWriter()) {
                Long cityId = Long.valueOf(subPaths[1]);
                CityDTO city = gson.fromJson(jsonString, CityDTO.class);
                city.setId(cityId);
                city = cityService.update(city);
                writer.println(gson.toJson(city));
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try {
                Long cityId = Long.valueOf(subPaths[1]);
                CityDTO city = new CityDTO();
                city.setId(cityId);
                cityService.delete(city);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
