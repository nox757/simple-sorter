package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import entities.Country;
import ru.chibisov.app.servicies.CountryService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
        resp.setContentType("text");
        PrintWriter writer = resp.getWriter();
        try {
            Country country = countryService.getById(437L);
            writer.println(country.getName());
            writer.println("Path: " + req.getPathInfo());
            writer.println("Path: " + gson.toJson(country));
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = getRequestBody(req);
        System.out.println(jsonString);
        Country country = gson.fromJson(jsonString, Country.class);
        System.out.println(country.getName() + country.getId());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Arrays.toString(req.getPathInfo().split("/")));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private String[] getPathParam(String url) {
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
