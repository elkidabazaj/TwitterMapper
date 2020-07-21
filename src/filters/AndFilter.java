package filters;

import twitter4j.Status;

import java.util.List;

public class AndFilter implements Filter {
    private final Filter firstChild;
    private final Filter secondChild;

    public AndFilter(Filter firstChild, Filter secondChild) {
        this.firstChild = firstChild;
        this.secondChild = secondChild;
    }
    @Override
    public boolean matches(Status status) {
        return firstChild.matches(status) && secondChild.matches(status);
    }

    @Override
    public List<String> terms() {
        List<String> terms = firstChild.terms();
        terms.addAll(secondChild.terms());
        return terms;
    }

    @Override
    public String toString() {
        return firstChild.toString() + " and " + secondChild.toString();
    }
}
