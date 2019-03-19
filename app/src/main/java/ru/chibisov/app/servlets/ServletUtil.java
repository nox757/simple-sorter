package ru.chibisov.app.servlets;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class ServletUtil {

    public static String[] getPathParam(String url) {
        if (url == null) {
            return null;
        }
        return url.split("/");
    }

    public static String getRequestBody(final HttpServletRequest request) {
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
