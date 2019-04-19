package ru.chibisov.app.servlet;

import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.service.CityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CityServlet extends AbstractApiServlet {

    private CityService cityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cityService = (CityService) getServletContext().getAttribute("cityService");
        fillGetRequestMap();
        fillPostRequestMap();
        fillPutRequestMap();
        fillDeleteRequestMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handlerDoGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlerDoPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlerDoPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlerDoDelete(req, resp);
    }

    /**
     * Fill Map of method to execute GET by path:
     * - "/"
     * - "/{id}"
     * - "/{id}/attributes"
     */
    public void fillGetRequestMap() {
        pathGetMap = new ConcurrentHashMap<>();
        pathGetMap.put("^/$",
                (vars, resp) -> printJsonToResp(resp, () -> cityService.getAll())
        );
        pathGetMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    Long cityId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> cityService.getById(cityId));
                }
        );
        pathGetMap.put("^/([0-9]+)/attributes/?$",
                (vars, resp) -> {
                    Long cityId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> cityService.getCityAttributes(cityId));
                }
        );
    }

    /**
     * Fill Map of method to execute Post by path:
     * - "/"
     */
    public void fillPostRequestMap() {
        pathPostMap = new ConcurrentHashMap<>();
        pathPostMap.put("^/$", (vars, resp) -> {
            String json = vars.get("JSON");
            if (json == null || json.isEmpty()) {
                return;
            }
            printJsonToResp(resp, () -> {
                CityDTO city = gson.fromJson(json, CityDTO.class);
                return cityService.create(city);
            });
        });
    }

    /**
     * Fill Map of method to execute Put by path:
     * - "/{id}"
     */
    public void fillPutRequestMap() {
        pathPutMap = new ConcurrentHashMap<>();
        pathPutMap.put("^/([0-9]+)/?$", (vars, resp) -> {
            String json = vars.get("JSON");
            if (json == null || json.isEmpty()) {
                return;
            }
            printJsonToResp(resp, () -> {
                CityDTO city = gson.fromJson(json, CityDTO.class);
                Long cityId = Long.valueOf(vars.get("URL_1"));
                city.setId(cityId);
                return cityService.update(city);
            });
        });
        pathPutMap.put("/([0-9]+)/attributes/([0-9]+)/?$", (vars, resp) -> {
            printJsonToResp(resp, () -> {
                Long cityId = Long.valueOf(vars.get("URL_1"));
                Long attributeId = Long.valueOf(vars.get("URL_2"));
                return cityService.addCityAttribute(cityId, attributeId);
            });
        });
    }

    /**
     * Fill Map of method to execute Delete by path:
     * - "/{id}"
     */
    public void fillDeleteRequestMap() {
        pathDeleteMap = new ConcurrentHashMap<>();
        pathDeleteMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    CityDTO city = new CityDTO();
                    Long cityId = Long.valueOf(vars.get("URL_1"));
                    city.setId(cityId);
                    cityService.delete(city);
                });
    }
}
