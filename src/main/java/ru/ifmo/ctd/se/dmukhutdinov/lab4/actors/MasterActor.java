package ru.ifmo.ctd.se.dmukhutdinov.lab4.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.model.SearchResult;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author flyingleafe
 */
public class MasterActor extends UntypedActor {
    private static final Duration TIMEOUT = Duration.create(10, TimeUnit.SECONDS);
    private int answersReceived = 0;
    private List<SearchResult> results = new ArrayList<>();
    private ActorRef originalSender;

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(false, DeciderBuilder
                .match(QueryException.class, e -> OneForOneStrategy.escalate())
                .match(ResponseException.class, e -> OneForOneStrategy.escalate())
                .build());
    }

    @Override
    @SuppressWarnings("unchecked cast")
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            List<ActorRef> children = new ArrayList<>();
            children.add(getContext().actorOf(Props.create(GoogleActor.class)));
            children.add(getContext().actorOf(Props.create(YandexActor.class)));
            children.add(getContext().actorOf(Props.create(BingActor.class)));

            for (ActorRef child: children) {
                child.tell(message, getSelf());
            }

            originalSender = getSender();
            getContext().setReceiveTimeout(TIMEOUT);
        } else if (message instanceof List) {
            addResult((List<SearchResult>) message);
            if (answersReceived == 3) {
                originalSender.tell(results, getSelf());
                getContext().stop(getSelf());
            }
        } else if (message instanceof ReceiveTimeout) {
            originalSender.tell(message, getSelf());
            getContext().stop(getSelf());
        }
    }

    private void addResult(List<SearchResult> partial) {
        int j = 0;
        answersReceived++;
        int step = answersReceived + 1;

        for (int i = answersReceived; i < results.size(); i += step) {
            results.add(i, partial.get(j));
            j++;
        }

        for ( ; j < partial.size(); j++) {
            results.add(partial.get(j));
        }
    }
}
