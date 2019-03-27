package ru.chibisov.app.servlets;


import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Functional interface to release method to build HttpServletResponse
 * @param Map<String, String> maybe contains params {key : value}
 * @param HttpServletResponse
 */
@FunctionalInterface
interface ResponseMethodHandler extends BiConsumer<Map<String, String>, HttpServletResponse> {

}
