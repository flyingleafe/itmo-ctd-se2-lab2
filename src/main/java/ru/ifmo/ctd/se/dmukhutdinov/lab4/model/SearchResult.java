package ru.ifmo.ctd.se.dmukhutdinov.lab4.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author flyingleafe
 */
@Data
@AllArgsConstructor
public class SearchResult {
    private String system;
    private String title;
    private String url;
    private String contents;
}
