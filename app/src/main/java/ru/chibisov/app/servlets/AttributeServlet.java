package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.servicies.AttributeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AttributeServlet extends HttpServlet {

    private AttributeService attributeService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        attributeService = (AttributeService) getServletContext().getAttribute("attributeService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<AttributeDTO> attributes = attributeService.getAll();
                writer.println(gson.toJson(attributes));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long attributeId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                AttributeDTO attribute = attributeService.getById(attributeId);
                if (attribute == null) {
                    return;
                }
                writer.println(gson.toJson(attribute));
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
                AttributeDTO attribute = gson.fromJson(jsonString, AttributeDTO.class);
                attribute = attributeService.create(attribute);
                writer.println(gson.toJson(attribute));
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
                Long attributeId = Long.valueOf(subPaths[1]);
                AttributeDTO attribute = gson.fromJson(jsonString, AttributeDTO.class);
                attribute.setId(attributeId);
                attribute = attributeService.update(attribute);
                writer.println(gson.toJson(attribute));
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
                Long attributeId = Long.valueOf(subPaths[1]);
                AttributeDTO attribute = new AttributeDTO();
                attribute.setId(attributeId);
                attributeService.delete(attribute);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
