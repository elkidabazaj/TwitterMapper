package query;

import twitter.LiveTwitterSource;
import twitter.TwitterSource;
import ui.panels.ContentPanel;

import java.util.*;

public class QueryManager {
    private static final QueryManager INSTANCE;
    private final List<Query> queryList;
    private TwitterSource twitterSource;

    static {
        INSTANCE = new QueryManager();
    }

    private QueryManager() {
        twitterSource = new LiveTwitterSource();
        /** The number passed to the constructor is a speedup value:
         *  1.0 - play back at the recorded speed
         *  2.0 - play back twice as fast
         */
//        twitterSource = new PlaybackTwitterSource(60.0);
        queryList = new ArrayList<>();
    }

    public static QueryManager getInstance() {
        return INSTANCE;
    }

    public List<Query> getQueryList() {
        return Collections.unmodifiableList(queryList);
    }

    /**
     * A new query has been entered via the User Interface
     * @param   query   The new query object
     */
    public void addQuery(Query query, ContentPanel contentPanel) {
        addQueryToList(query);
        twitterSource.setFilterTerms(getQueryTerms());
        addQueryToContentPanel(query, contentPanel);
        twitterSource.addObserver(query);
    }

    private void addQueryToList(Query query) {
        queryList.add(query);
    }

    private void addQueryToContentPanel(Query query, ContentPanel contentPanel) {
        contentPanel.addQueryToPanel(query);
    }

    /**
     * return a list of all terms mentioned in all queries. The live twitter source uses this
     * to request matching tweets from the Twitter API.
     * @return
     */
    private Set<String> getQueryTerms() {
        Set<String> queryTerms = new HashSet<>();
        for (Query query : queryList) {
            queryTerms.addAll(query.getFilter().terms());
        }
        return queryTerms;
    }

    /**
     * After a query has been deleted, remove all traces of it
     * @param query
     */
    public void terminateQuery(Query query) {
        query.terminate();
        twitterSource.deleteObserver(query);

        queryList.remove(query);
        twitterSource.setFilterTerms(getQueryTerms());
    }

}
