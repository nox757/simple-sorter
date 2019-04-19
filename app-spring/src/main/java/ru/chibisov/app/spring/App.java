package ru.chibisov.app.spring;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.chibisov.app.spring.config.AppServiceConfig;

public class App extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppServiceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
