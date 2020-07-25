package twitter;

import twitter4j.Status;
import util.ImageCache;

import java.util.*;


public abstract class TwitterSource extends Observable {
    protected boolean doLogging = true;
    protected Set<String> terms = new HashSet<>();

    abstract protected void sync();

    protected void storeImageToCache(Status status) {
        ImageCache.getInstance().storeImageToCache(status.getUser().getProfileImageURL());
        if (doLogging) {
            printStatusLog(status);
        }
    }

    protected void printStatusLog(Status status) {
        System.out.println(status.getUser().getName() + ": " + status.getText());
    }

    public void setFilterTerms(Collection<String> newTerms) {
        terms.clear();
        terms.addAll(newTerms);
        sync();
    }

    public List<String> getFilterTerms() {
        return new ArrayList<>(terms);
    }

    protected void handleTweet(Status status) {
        setChanged();
        notifyObservers(status);
    }
}
