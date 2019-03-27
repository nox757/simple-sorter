package ru.chibisov.app.servlets;

import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.servicies.AttributeTypeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class AttributeTypeServlet extends AbstractApiServlet {

    private AttributeTypeService attributeTypeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        attributeTypeService = (AttributeTypeService) getServletContext().getAttribute("attributeTypeService");
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
                (vars, resp) -> printJsonToResp(resp, () -> attributeTypeService.getAll())
        );
        pathGetMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    Long attributeTypeId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> attributeTypeService.getById(attributeTypeId));
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
                AttributeTypeDTO attributeType = gson.fromJson(json, AttributeTypeDTO.class);
                return attributeTypeService.create(attributeType);
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
                AttributeTypeDTO attributeType = gson.fromJson(json, AttributeTypeDTO.class);
                Long attributeTypeId = Long.valueOf(vars.get("URL_1"));
                attributeType.setId(attributeTypeId);
                return attributeTypeService.update(attributeType);
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
            AttributeTypeDTO attributeType = new AttributeTypeDTO();
            Long attributeTypeId = Long.valueOf(vars.get("URL_1"));
            attributeType.setId(attributeTypeId);
            attributeTypeService.delete(attributeType);
        });
    }
}
