package ru.chibisov.app.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("ru.chibisov.app")
@Import({DaoConfig.class, MapperConfig.class})
public class AppServiceConfig {

}
