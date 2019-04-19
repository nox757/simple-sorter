package ru.chibisov.app.servlet;

import ru.chibisov.app.dto.CountryDTO;
import ru.chibisov.app.service.CountryService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CountryServlet extends AbstractApiServlet {

    private CountryService countryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countryService = (CountryService) getServletContext().getAttribute("countryService");
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
     */
    public void fillGetRequestMap() {
        pathGetMap = new ConcurrentHashMap<>();
        pathGetMap.put("^/$",
                (vars, resp) -> printJsonToResp(resp, () -> countryService.getAll())
        );
        pathGetMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    Long countryId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> countryService.getById(countryId));
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
                CountryDTO country = gson.fromJson(json, CountryDTO.class);
                return countryService.create(country);
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
                CountryDTO country = gson.fromJson(json, CountryDTO.class);
                Long countryId = Long.valueOf(vars.get("URL_1"));
                country.setId(countryId);
                return countryService.update(country);
            });
        });
    }

    /**
     * Fill Map of method to execute Delete by path:
     * - "/{id}"
     */
    public void fillDeleteRequestMap() {
        pathDeleteMap = new ConcurrentHashMap<>();
        pathDeleteMap.put("^/([0-9]+)/?$", (vars, resp) -> {
            CountryDTO country = new CountryDTO();
            Long countryId = Long.valueOf(vars.get("URL_1"));
            country.setId(countryId);
            countryService.delete(country);
        });
    }
}
