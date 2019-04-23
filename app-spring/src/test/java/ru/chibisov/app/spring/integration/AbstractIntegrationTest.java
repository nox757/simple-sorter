package ru.chibisov.app.spring.integration;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.chibisov.app.spring.config.AppServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppServiceConfig.class})
@WebAppConfiguration
abstract class AbstractIntegrationTest {

    protected MockMvc mockMvc;
}
