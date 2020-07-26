package tests;

import filters.BasicFilter;
import filters.Filter;
import query.Query;


public class MockDefaultQuery extends Query {
    private String queryString;
    private final Filter filter;

    public MockDefaultQuery(String queryString) {
        this.queryString = queryString;
        this.filter = new BasicFilter("basic filter");
    }
}
