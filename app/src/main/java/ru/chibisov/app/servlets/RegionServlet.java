package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.RegionDTO;
import ru.chibisov.app.servicies.RegionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegionServlet extends HttpServlet {

    private RegionService regionService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        regionService = (RegionService) getServletContext().getAttribute("regionService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<RegionDTO> countries = regionService.getAll();
                writer.println(gson.toJson(countries));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long regionId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                RegionDTO region = regionService.getById(regionId);
                if (region == null) {
                    return;
                }
                writer.println(gson.toJson(region));
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
                RegionDTO region = gson.fromJson(jsonString, RegionDTO.class);
                region = regionService.create(region);
                writer.println(gson.toJson(region));
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
                Long regionId = Long.valueOf(subPaths[1]);
                RegionDTO region = gson.fromJson(jsonString, RegionDTO.class);
                region.setId(regionId);
                region = regionService.update(region);
                writer.println(gson.toJson(region));
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
            try (PrintWriter writer = resp.getWriter()) {
                Long regionId = Long.valueOf(subPaths[1]);
                RegionDTO region = new RegionDTO();
                region.setId(regionId);
                regionService.delete(region);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
