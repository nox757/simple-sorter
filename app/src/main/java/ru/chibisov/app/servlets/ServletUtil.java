package ru.chibisov.app.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServletUtil {

    public static String[] getPathParam(String url) {
        if (url == null) {
            return null;
        }
        return url.split("/");
    }

    public static List<String> extractPathParam(String url, String regex) {
        if (url == null || regex == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        List<String> res = new ArrayList<>();
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()){
            for (int j = 1; j <= matcher.groupCount(); j++) {
                res.add(matcher.group(j));
            }
        }
        return res;
    }

    /**
     * Method extract body from HTTP request
     * @param request
     * @return String
     * @deprecated use emthod from Abstract class {@link AbstractApiServlet}
     */
    @Deprecated
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
