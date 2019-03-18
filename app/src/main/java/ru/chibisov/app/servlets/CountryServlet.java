package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import ru.chibisov.app.dto.CountryDTO;
import ru.chibisov.app.servicies.CountryService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CountryServlet extends HttpServlet {

    private CountryService countryService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countryService = (CountryService) getServletContext().getAttribute("countryService");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        String[] subPaths = getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                List<CountryDTO> countries = countryService.getAll();
                writer.println(gson.toJson(countries));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (subPaths.length == 2) {
            Long countryId = Long.valueOf(subPaths[1]);
            try (PrintWriter writer = resp.getWriter()) {
                CountryDTO country = countryService.getById(countryId);
                if (country == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                writer.println(gson.toJson(country));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonString = getRequestBody(req);
        if(jsonString == null || jsonString.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] subPaths = getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            try (PrintWriter writer = resp.getWriter()) {
                CountryDTO country = gson.fromJson(jsonString, CountryDTO.class);
                country = countryService.create(country);
                writer.println(gson.toJson(country));
                resp.setStatus(HttpServletResponse.SC_CREATED);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonString = getRequestBody(req);
        if(jsonString == null || jsonString.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] subPaths = getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try (PrintWriter writer = resp.getWriter()) {
                Long countryId = Long.valueOf(subPaths[1]);
                CountryDTO country = gson.fromJson(jsonString, CountryDTO.class);
                country.setId(countryId);
                country = countryService.update(country);
                writer.println(gson.toJson(country));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] subPaths = getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try (PrintWriter writer = resp.getWriter()) {
                Long countryId = Long.valueOf(subPaths[1]);
                CountryDTO country = new CountryDTO();
                country.setId(countryId);
                countryService.delete(country);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String[] getPathParam(String url) {
        if (url == null) {
            return null;
        }
        return url.split("/");
    }

    private String getRequestBody(final HttpServletRequest request) {
        final StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            if (reader == null) {
                return null;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (final Exception e) {
            return null;
        }
    }
}
