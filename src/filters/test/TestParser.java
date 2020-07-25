package filters.test;

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
}
