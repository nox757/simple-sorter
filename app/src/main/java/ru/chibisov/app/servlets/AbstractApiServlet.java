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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Common class to create and work with Api methods
 */
abstract class AbstractApiServlet extends HttpServlet {

    protected Gson gson;

    /**
     * Map should be contains urls(regexp) and methods to GET
     */
    protected Map<String, ResponseMethodHandler> pathGetMap;

    /**
     * Map should be contains urls(regexp) and methods to POST
     */
    protected Map<String, ResponseMethodHandler> pathPostMap;
    /**
     * Map should be contains urls(regexp) and methods to PUT
     */
    protected Map<String, ResponseMethodHandler> pathPutMap;
    /**
     * Map should be contains urls(regexp) and methods to DELETE
     */
    protected Map<String, ResponseMethodHandler> pathDeleteMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gson = new GsonBuilder().serializeNulls().create();
    }

    protected void handlerDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (pathGetMap == null) {
            throw new ServletException("Not fill 'pathGetMap' actions to execute doGet");
        }
        doApiHttp(req, resp, pathGetMap);
    }

    protected void handlerDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (pathPostMap == null) {
            throw new ServletException("Not fill 'pathPostMap' actions to execute doPost");
        }
        doApiHttp(req, resp, pathPostMap);
    }

    protected void handlerDoPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (pathPutMap == null) {
            throw new ServletException("Not fill 'pathPutMap' actions to execute doPut");
        }
        doApiHttp(req, resp, pathPutMap);
    }

    protected void handlerDoDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (pathDeleteMap == null) {
            throw new ServletException("Not fill 'pathDeleteMap' actions to execute doDelete");
        }
        doApiHttp(req, resp, pathDeleteMap);
    }

    private void doApiHttp(HttpServletRequest req, HttpServletResponse resp, Map<String, ResponseMethodHandler> pathDeleteMap) {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String jsonString = null;
        try {
            jsonString = getRequestBody(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<>();
        params.put("JSON", jsonString);
        String pathKey = req.getPathInfo();
        if (req.getPathInfo() == null) {
            pathKey = "/";
        }
        ResponseMethodHandler method = null;
        for (Map.Entry<String, ResponseMethodHandler> pathEntry : pathDeleteMap.entrySet()) {
            String regexKey = pathEntry.getKey();
            if (pathKey.toLowerCase().matches(regexKey)) {
                method = pathEntry.getValue();
                params.putAll(UrlDecoderUtil.extractPathParam(pathKey, regexKey));
                break;
            }
        }
        if (method == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            method.accept(params, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    /**
     * Read request body and return string-result
     * @param request
     * @return
     * @throws IOException
     */
    protected String getRequestBody(final HttpServletRequest request) throws IOException {
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
            throw e;
        }
    }

    /**
     * Print item(-s) to resp like a json-string
     * @param resp
     * @param supplier return Object(-s) to print json
     * @param <E> type of return Object(-s)
     */
    protected <E> void printJsonToResp(final HttpServletResponse resp, Supplier<E> supplier) {
        try (PrintWriter writer = resp.getWriter()) {
            E item = supplier.get();
            if(item == null) {
                return;
            }
            writer.println(gson.toJson(item));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
