package ru.ifmo.ctd.se.dmukhutdinov.lab4.actors;

import ru.ifmo.ctd.se.dmukhutdinov.lab4.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flyingleafe
 */
public class YandexActor extends AbstractChildActor {
    @Override
    public List<SearchResult> getSearchResults(String query) {
        return new ArrayList<>();
    }
}
