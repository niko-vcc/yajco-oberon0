package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.parser.ParseException;
import yajco.oberon0.parser.Parser;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        parser.parse("MODULE First; END Second.");
    }

    @Test
    public void variableDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; VAR x: INTEGER; END Sample.");
        List<Declaration> declarations = module.getDeclarations();
        assertEquals(1, declarations.size());
        assertEquals("x", declarations.get(0).getName());
        assertThat(declarations.get(0), instanceOf(Variable.class));
    }

    @Test
    public void constantDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = 3; END Sample.");
        List<Declaration> declarations = module.getDeclarations();
        assertEquals(1, declarations.size());
        assertThat(declarations.get(0), instanceOf(Constant.class));
        Constant constant = (Constant) declarations.get(0);
        assertEquals("n", constant.getName());
        assertThat(constant.getExpression(), instanceOf(Number.class));
        assertEquals(3, ((Number) constant.getExpression()).getValue());
    }
}
