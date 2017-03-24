package ru.ifmo.ctd.se.dmukhutdinov.lab4.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.actors.MasterActor;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.model.SearchResult;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author flyingleafe
 */
@Controller
public class TodoController {
    @Autowired
    private ActorSystem actorSystem;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) throws Exception {
        map.addAttribute("results", new ArrayList<SearchResult>());
        map.addAttribute("query", "");
        map.addAttribute("timeout", false);
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @SuppressWarnings("unchecked cast")
    public String search(@RequestParam("query") String query, ModelMap map) throws Exception {
        map.addAttribute("query", query);
        ActorRef masterActor = actorSystem.actorOf(Props.create(MasterActor.class));
        Timeout timeout = new Timeout(Duration.create(1, TimeUnit.MINUTES));
        Future<Object> future = Patterns.ask(masterActor, query, timeout);
        Object res = Await.result(future, timeout.duration());
        if (res instanceof ReceiveTimeout) {
            map.addAttribute("timeout", true);
        } else {
            List<SearchResult> ls = (List<SearchResult>) res;
            map.addAttribute("timeout", false);
            map.addAttribute("results", ls);
        }

        return "index";
    }
}
