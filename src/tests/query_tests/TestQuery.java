package tests.query_tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import query.Query;
import query.QueryManager;
import tests.MockDefaultQuery;
import ui.panels.ContentPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestQuery {

    @Test
    public void testQueryOperationsOnList() {
        ContentPanel contentPanel = Mockito.mock(ContentPanel.class);
        QueryManager queryManager = QueryManager.getInstance();
        Query firstQuery = new MockDefaultQuery("fred");
        Query secondQuery = new MockDefaultQuery("vilma");
        Query thirdQuery = new MockDefaultQuery("barney");

        testAddQueryToList(queryManager, firstQuery, contentPanel, 1);
        testAddQueryToList(queryManager, secondQuery, contentPanel, 2);
        testRemoveQueryFromList(queryManager, firstQuery, 1);
        testAddQueryToList(queryManager, firstQuery, contentPanel, 2);
        testAddQueryToList(queryManager, thirdQuery, contentPanel, 3);
        testRemoveQueryFromList(queryManager, firstQuery, 2);
        testRemoveQueryFromList(queryManager, secondQuery, 1);
        testRemoveQueryFromList(queryManager, thirdQuery, 0);

    }

    @Test
    private void testAddQueryToList(QueryManager instance, Query query, ContentPanel contentPanel, int expectedSize) {
        instance.addQuery(query, contentPanel);
        assertEquals(expectedSize, instance.getQueryList().size());
    }

    @Test
    private void testRemoveQueryFromList(QueryManager instance, Query query, int expectedSize) {
        instance.terminateQuery(query);
        assertEquals(expectedSize, instance.getQueryList().size());
    }
}
