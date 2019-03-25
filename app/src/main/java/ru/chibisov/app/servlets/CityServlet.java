package ru.chibisov.app.servlets;

import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.servicies.CityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CityServlet extends AbstractApiServlet {

    private CityService cityService;
    private Map<String, ResponseMethodHandler> pathGetMap;
    private Map<String, ResponseMethodHandler> pathPostMap;
    private Map<String, ResponseMethodHandler> pathPutMap;
    private Map<String, ResponseMethodHandler> pathDeleteMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cityService = (CityService) getServletContext().getAttribute("cityService");
        fillGetRequestMap();
    }

    /**
     * Fill Map of method to execute GET by path:
     *  - "/"
     *  - "/{id}"
     *  - "/{id}/attributes"
     */
    public void fillGetRequestMap() {
        pathGetMap = new ConcurrentHashMap<>();
        pathGetMap.put("^\\/$",
                (vars, resp) -> printJsonToResp(resp, () -> cityService.getAll())
        );
        pathGetMap.put("^\\/([0-9])\\/?$",
                (vars, resp) -> {
                    Long cityId = Long.valueOf(vars.get(0));
                    printJsonToResp(resp, () -> cityService.getById(cityId));
                }
        );
        pathGetMap.put("^\\/([0-9])\\/attributes\\/?$",
                (vars, resp) -> {
                    Long cityId = Long.valueOf(vars.get(0));
                    printJsonToResp(resp, () -> cityService.getCityAttributes(cityId));
                }
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        handlerDoGet(req, resp, pathGetMap);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        handlerDoPost(req, resp, jsonString -> printJsonToResp(resp, () -> {
            CityDTO city = gson.fromJson(jsonString, CityDTO.class);
            return cityService.create(city);
        }));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlerDoPut(req, resp, (cityId, jsonString) -> printJsonToResp(resp, () -> {
            CityDTO city = gson.fromJson(jsonString, CityDTO.class);
            city.setId(cityId);
            return cityService.update(city);
        }));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlerDoDelete(req, resp, cityId -> {
            CityDTO city = new CityDTO();
            city.setId(cityId);
            cityService.delete(city);
        });
    }
}
