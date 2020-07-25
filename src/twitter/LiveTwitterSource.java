package twitter;

import twitter4j.*;

/**
 * Encapsulates the connection to Twitter
 *
 * Terms to include in the returned tweets can be set with setFilterTerms
 *
 * Implements Observable - each received tweet is signalled to all observers
 */
public class LiveTwitterSource extends TwitterSource {
    private TwitterStream twitterStream;
    private StatusListener listener;

    public LiveTwitterSource() {
        initializeTwitterStream();
    }

    protected void sync() {
        FilterQuery filter = new FilterQuery();
        String[] queriesArray = terms.toArray(new String[0]);
        filter.track(queriesArray);

        logSync();

        twitterStream.filter(filter);
    }

    private void logSync() {
        System.out.println("Syncing live Twitter stream with " + terms);
    }

    private void initializeListener() {
        listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                if (status.getPlace() != null) {
                    handleTweet(status);
                }
           }
        };
    }

    private void initializeTwitterStream() {
        twitterStream = new TwitterStreamFactory(ConfigurationManager.getDefaultConfiguration()).getInstance();
        initializeListener();
        twitterStream.addListener(listener);
    }
}
