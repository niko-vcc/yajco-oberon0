package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.parser.ParseException;
import yajco.oberon0.parser.Parser;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    private Parser parser;

    @Before
    public void createParser() {
        parser = new Parser();
    }

    @Test
    public void emptyModule() throws ParseException {
        Module module = parser.parse("MODULE Empty; END Empty.");
        assertEquals("Empty", module.getName());
    }

    @Test(expected = RuntimeException.class)
    public void unmatchingModuleName() throws ParseException {
        Module module = parser.parse("MODULE First; END Second.");
    }

    @Test
    public void variableDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; VAR x: INTEGER; END Sample.");
        List<Declaration> declarations = module.getDeclarations();
        assertEquals(1, declarations.size());
        assertEquals("x", declarations.get(0).getName());
    }
}
