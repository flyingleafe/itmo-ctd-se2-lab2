package ru.ifmo.ctd.se.dmukhutdinov.lab4.actors;

import akka.actor.UntypedActor;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.model.SearchResult;

import java.util.List;

/**
 * @author flyingleafe
 */
public abstract class AbstractChildActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            List<SearchResult> res = getSearchResults((String) message);
            sender().tell(res, getSelf());
        }
    }

    public abstract List<SearchResult> getSearchResults(String query) throws Exception;
}
