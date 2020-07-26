package tests.filter_tests;

import filters.*;
import org.junit.jupiter.api.Test;
import tests.MockStatus;
import twitter4j.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFilters {
    @Test
    public void testBasicFilter() {
        Filter f = new BasicFilter("fred");
        assertTrue(f.matches(makeStatus("Fred Flintstone")));
        assertTrue(f.matches(makeStatus("fred Flintstone")));
        assertFalse(f.matches(makeStatus("Red Skelton")));
        assertFalse(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testNotFilter() {
        Filter f = new NotFilter(new BasicFilter("fred"));
        assertFalse(f.matches(makeStatus("Fred Flintstone")));
        assertFalse(f.matches(makeStatus("fred Flintstone")));
        assertTrue(f.matches(makeStatus("Red Skelton")));
        assertTrue(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testAndFilter() {
        Filter f = new AndFilter(new BasicFilter("fred"), new BasicFilter("vilma"));
        assertFalse(f.matches(makeStatus("Fred Flintstone")));
        assertFalse(f.matches(makeStatus("fred Flintstone")));
        assertFalse(f.matches(makeStatus("Red Skelton")));
        assertFalse(f.matches(makeStatus("red Skelton")));
        assertTrue(f.matches(makeStatus("fred fell in love with vilma")));
    }

    @Test
    public void testOrFilter() {
        Filter f = new OrFilter(new BasicFilter("fred"), new BasicFilter("vilma"));
        assertTrue(f.matches(makeStatus("Fred Flintstone married Vilma")));
        assertTrue(f.matches(makeStatus("fred Flintstone")));
        assertFalse(f.matches(makeStatus("Red Skelton")));
        assertFalse(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testNotTerms() {
        Filter f = new NotFilter(new BasicFilter("fred"));
        assertTrue(f.terms().contains("fred"));
        assertFalse(f.terms().contains("FRED"));
        assertFalse(f.terms().contains("fre"));
        assertFalse(f.terms().contains("fredd"));
    }

    @Test
    public void testAndTerms() {
        Filter f = new AndFilter(new BasicFilter("fred"), new BasicFilter("vilma"));
        assertTrue(f.terms().contains("fred"));
        assertFalse(f.terms().contains("FRED"));
        assertTrue(f.terms().contains("vilma"));
        assertFalse(f.terms().contains("barney"));
    }

    @Test
    public void testOrTerms() {
        Filter f = new OrFilter(new BasicFilter("fred"), new BasicFilter("barney"));
        assertTrue(f.terms().contains("barney"));
        assertFalse(f.terms().contains("FRED"));
        assertTrue(f.terms().contains("fred"));
        assertFalse(f.terms().contains("betty"));
    }

    private Status makeStatus(String text) {
        return new MockStatus(text);
    }
}
