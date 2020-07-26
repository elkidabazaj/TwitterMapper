package tests.filter_tests;

import filters.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the parser.
 */
public class TestParser {
    @Test
    public void testBasic() throws FailedSyntaxException {
        Filter f = new Parser("trump").parse();
        assertTrue(f instanceof BasicFilter);
        assertTrue(((BasicFilter)f).getWord().equals("trump"));
    }

    @Test
    public void testHairy() throws FailedSyntaxException {
        Filter x = new Parser("trump and (evil or blue) and red or green and not not purple").parse();
        assertEquals("(((trump and (evil or blue)) and red) or (green and not not purple))", x.toString());
    }

    @Test
    public void testNot() throws FailedSyntaxException {
        Filter f = new Parser("not trump").parse();
        assertTrue(f instanceof NotFilter);
    }

    @Test
    public void testAnd() throws FailedSyntaxException {
        Filter f = new Parser("green and banana").parse();
        assertTrue(f instanceof AndFilter);
        assertEquals("(green and banana)", f.toString());
    }

    @Test
    public void testOr() throws FailedSyntaxException {
        Filter f = new Parser("(green) or banana").parse();
        assertTrue(f instanceof OrFilter);
        assertEquals("(green or banana)", f.toString());
    }
}
