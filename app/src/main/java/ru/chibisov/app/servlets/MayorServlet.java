package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.MayorDTO;
import ru.chibisov.app.servicies.AttributeService;
import ru.chibisov.app.servicies.MayorService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MayorServlet extends HttpServlet {

    private MayorService mayorService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mayorService = (MayorService) getServletContext().getAttribute("mayorService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<MayorDTO> mayors = mayorService.getAll();
                writer.println(gson.toJson(mayors));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long mayorId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                MayorDTO mayor = mayorService.getById(mayorId);
                if (mayor == null) {
                    return;
                }
                writer.println(gson.toJson(mayor));
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
                MayorDTO mayor = gson.fromJson(jsonString, MayorDTO.class);
                mayor = mayorService.create(mayor);
                writer.println(gson.toJson(mayor));
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
                Long mayorId = Long.valueOf(subPaths[1]);
                MayorDTO mayor = gson.fromJson(jsonString, MayorDTO.class);
                mayor.setId(mayorId);
                mayor = mayorService.update(mayor);
                writer.println(gson.toJson(mayor));
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
                Long mayorId = Long.valueOf(subPaths[1]);
                MayorDTO mayor = new MayorDTO();
                mayor.setId(mayorId);
                mayorService.delete(mayor);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
