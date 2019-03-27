package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.MayorDTO;
import ru.chibisov.app.servicies.MayorService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MayorServlet extends AbstractApiServlet {

    private MayorService mayorService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mayorService = (MayorService) getServletContext().getAttribute("mayorService");
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
                (vars, resp) -> printJsonToResp(resp, () -> mayorService.getAll())
        );
        pathGetMap.put("^/([0-9]+)/?$",
                (vars, resp) -> {
                    Long mayorId = Long.valueOf(vars.get("URL_1"));
                    printJsonToResp(resp, () -> mayorService.getById(mayorId));
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
                MayorDTO mayor = gson.fromJson(json, MayorDTO.class);
                return mayorService.create(mayor);
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
                MayorDTO mayor = gson.fromJson(json, MayorDTO.class);
                Long mayorId = Long.valueOf(vars.get("URL_1"));
                mayor.setId(mayorId);
                return mayorService.update(mayor);
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
            MayorDTO mayor = new MayorDTO();
            Long mayorId = Long.valueOf(vars.get("URL_1"));
            mayor.setId(mayorId);
            mayorService.delete(mayor);
        });
    }
}
