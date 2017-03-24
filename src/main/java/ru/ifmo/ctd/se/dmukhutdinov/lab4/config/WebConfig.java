package ru.ifmo.ctd.se.dmukhutdinov.lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author akirakozov
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.ifmo.ctd.se.dmukhutdinov.lab4.controller")
@Import({ActorSystemContextConfiguration.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver configureInternalResourceViewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
