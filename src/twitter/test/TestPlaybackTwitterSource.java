package twitter.test;

import org.junit.jupiter.api.Test;
import twitter.PlaybackTwitterSource;
import twitter4j.Status;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

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
        source.setFilterTerms(set("food"));
        pause(3 * 1000);
        assertTrue(testObserver.getNTweets() > 0, "Expected getNTweets() testObserver be > 0, was " + testObserver.getNTweets());
        assertTrue(testObserver.getNTweets() <= 10, "Expected getNTweets() testObserver be <= 10, was " + testObserver.getNTweets());
        int firstBunch = testObserver.getNTweets();
        System.out.println("Now adding 'the'");
        source.setFilterTerms(set("food", "the"));
        pause(3 * 1000);
        assertTrue(testObserver.getNTweets() > 0, "Expected getNTweets() testObserver be > 0, was " + testObserver.getNTweets());
        assertTrue(testObserver.getNTweets() > firstBunch, "Expected getNTweets() testObserver be < firstBunch (" + firstBunch + "), was " + testObserver.getNTweets());
        assertTrue(testObserver.getNTweets() <= 10, "Expected getNTweets() testObserver be <= 10, was " + testObserver.getNTweets());
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <E> Set<E> set(E ... p) {
        Set<E> ans = new HashSet<>();
        for (E a : p) {
            ans.add(a);
        }
        return ans;
    }
    private class TestObserver implements Observer {
        private int nTweets = 0;

        @Override
        public void update(Observable o, Object arg) {
            Status s = (Status) arg;
            nTweets ++;
        }

        public int getNTweets() {
            return nTweets;
        }
    }
}
