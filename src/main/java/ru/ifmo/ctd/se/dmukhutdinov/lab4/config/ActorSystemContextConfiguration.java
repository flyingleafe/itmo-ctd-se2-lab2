package ru.ifmo.ctd.se.dmukhutdinov.lab4.config;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author flyingleafe
 */
@Configuration
public class ActorSystemContextConfiguration {
    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("SearchSystem");
    }
}
