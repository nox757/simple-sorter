package ru.chibisov.app.servlets;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDecoderUtil {

    public static String[] getPathParam(String url) {
        if (url == null) {
            return null;
        }
        return url.split("/");
    }

    public static Map<String, String> extractPathParam(String url, String regex) {
        if (url == null || regex == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Map<String, String> res = new HashMap<>();
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()){
            for (int j = 1; j <= matcher.groupCount(); j++) {
                res.put("URL_" + j, matcher.group(j));
            }
        }
        return res;
    }

}
