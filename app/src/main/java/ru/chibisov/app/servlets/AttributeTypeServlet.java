package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.servicies.AttributeTypeService;
import ru.chibisov.app.servicies.RegionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AttributeTypeServlet extends AbstractApiServlet {

    private AttributeTypeService attributeTypeService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        attributeTypeService = (AttributeTypeService) getServletContext().getAttribute("attributeTypeService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<AttributeTypeDTO> attributeTypes = attributeTypeService.getAll();
                writer.println(gson.toJson(attributeTypes));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long attributeTypeId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                AttributeTypeDTO attributeType = attributeTypeService.getById(attributeTypeId);
                if (attributeType == null) {
                    return;
                }
                writer.println(gson.toJson(attributeType));
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
        String jsonString = getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                AttributeTypeDTO attributeType = gson.fromJson(jsonString, AttributeTypeDTO.class);
                attributeType = attributeTypeService.create(attributeType);
                writer.println(gson.toJson(attributeType));
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
        String jsonString = getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try (PrintWriter writer = resp.getWriter()) {
                Long attributeTypeId = Long.valueOf(subPaths[1]);
                AttributeTypeDTO attributeType = gson.fromJson(jsonString, AttributeTypeDTO.class);
                attributeType.setId(attributeTypeId);
                attributeType = attributeTypeService.update(attributeType);
                writer.println(gson.toJson(attributeType));
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
                Long attributeTypeId = Long.valueOf(subPaths[1]);
                AttributeTypeDTO attributeType = new AttributeTypeDTO();
                attributeType.setId(attributeTypeId);
                attributeTypeService.delete(attributeType);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
