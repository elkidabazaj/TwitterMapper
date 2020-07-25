package twitter.test;

import org.junit.jupiter.api.Test;
import twitter.PlaybackTwitterSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the basic functionality of the TwitterSource
 */
public class TestPlaybackTwitterSource {

    @Test
    public void testSetup() {
        PlaybackTwitterSource source = new PlaybackTwitterSource(1.0);
        TestObserver testObserver = new TestObserver();
        source.addObserver(testObserver);
        testPlaybackSourceWithSingleTerm(source, testObserver);
        testPlaybackSourceWithTwoTerms(source, testObserver);
    }

    private void testPlaybackSourceWithSingleTerm(PlaybackTwitterSource source, TestObserver testObserver) {
        source.setFilterTerms(set("food"));
        pause(3 * 1000);
        int nTweets = testObserver.getNTweets();
        assertTrue(nTweets > 0, "Expected getNTweets() testObserver be > 0, was " + nTweets);
        assertTrue(nTweets <= 10, "Expected getNTweets() testObserver be <= 10, was " + nTweets);
    }

    private void testPlaybackSourceWithTwoTerms(PlaybackTwitterSource source, TestObserver testObserver) {
        int firstBunch = testObserver.getNTweets();
        System.out.println("Now adding 'the'");
        source.setFilterTerms(set("food", "the"));
        pause(3 * 1000);
        int nTweets = testObserver.getNTweets();
        assertTrue(nTweets > 0, "Expected getNTweets() testObserver be > 0, was " + nTweets);
        assertTrue(nTweets > firstBunch, "Expected getNTweets() testObserver be < firstBunch (" + firstBunch + "), was " + nTweets);
        assertTrue(nTweets <= 10, "Expected getNTweets() testObserver be <= 10, was " + nTweets);
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    private final <E> Set<E> set(E... p) {
        Set<E> ans = new HashSet<>();
        Collections.addAll(ans, p);
        return ans;
    }

    private static class TestObserver implements Observer {
        private int nTweets = 0;

        @Override
        public void update(Observable o, Object arg) {
            nTweets ++;
        }

        private int getNTweets() {
            return nTweets;
        }
    }
}
