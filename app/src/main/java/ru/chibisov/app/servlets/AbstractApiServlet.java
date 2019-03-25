package ru.chibisov.app.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AbstractApiServlet extends HttpServlet {

    protected Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gson = new GsonBuilder().serializeNulls().create();
    }

    protected void handlerDoGet(HttpServletRequest req, HttpServletResponse resp, Map<String, ResponseMethodHandler> pathMap) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String pathKey = req.getPathInfo();
        if (req.getPathInfo() == null) {
            pathKey = "/";
        }
        ResponseMethodHandler method = null;
        List<String> subPaths = null;
        for (Map.Entry<String, ResponseMethodHandler> pathEntry : pathMap.entrySet()) {
            String regexKey = pathEntry.getKey();
            if (pathKey.toLowerCase().matches(regexKey)) {
                method = pathEntry.getValue();
                subPaths = ServletUtil.extractPathParam(pathKey, regexKey);
                break;
            }
        }
        if (method == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        method.accept(subPaths, resp);
    }

    protected void handlerDoPost(HttpServletRequest req, HttpServletResponse resp, Consumer<String> consumer) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String jsonString = getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths == null || subPaths.length == 0) {
            consumer.accept(jsonString);
        }
    }

    protected void handlerDoPut(HttpServletRequest req, HttpServletResponse resp, BiConsumer<Long, String> consumer) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String jsonString = getRequestBody(req);
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            Long id = Long.valueOf(subPaths[1]);
            consumer.accept(id, jsonString);
        }
    }

    /**
     * Base API to delete Entity with Path /entities/{id}
     */
    protected void handlerDoDelete(HttpServletRequest req, HttpServletResponse resp, Consumer<Long> consumer) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String[] subPaths = ServletUtil.getPathParam(req.getPathInfo());
        if (subPaths.length == 2) {
            try {
                Long id = Long.valueOf(subPaths[1]);
                consumer.accept(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected String getRequestBody(final HttpServletRequest request) {
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

    protected <E> void printJsonToResp(final HttpServletResponse resp, Supplier<E> supplier) {
        try (PrintWriter writer = resp.getWriter()) {
            E item = supplier.get();
            writer.println(gson.toJson(item));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
