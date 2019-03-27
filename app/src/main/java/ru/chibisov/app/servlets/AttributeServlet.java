package ru.chibisov.app.servlets;

import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.servicies.AttributeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class AttributeServlet extends AbstractApiServlet {

    private AttributeService attributeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        attributeService = (AttributeService) getServletContext().getAttribute("attributeService");
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
                (vars, resp) -> printJsonToResp(resp, () -> attributeService.getAll())
        );
        pathGetMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    Long attributeId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> attributeService.getById(attributeId));
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
                AttributeDTO attribute = gson.fromJson(json, AttributeDTO.class);
                return attributeService.create(attribute);
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
                AttributeDTO attribute = gson.fromJson(json, AttributeDTO.class);
                Long attributeId = Long.valueOf(vars.get("URL_1"));
                attribute.setId(attributeId);
                return attributeService.update(attribute);
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
            AttributeDTO attribute = new AttributeDTO();
            Long attributeId = Long.valueOf(vars.get("URL_1"));
            attribute.setId(attributeId);
            attributeService.delete(attribute);
        });
    }
}
