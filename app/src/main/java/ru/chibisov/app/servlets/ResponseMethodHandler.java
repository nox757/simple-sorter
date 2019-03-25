package ru.chibisov.app.servlets;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ResponseMethodHandler extends BiConsumer<List<String>, HttpServletResponse> {

}
