package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.*;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NameResolverTest {

    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Test
    public void singleAssignment() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: INTEGER; BEGIN x := 5 END Test.");
        List<ParserError> errors = NamesResolver.resolve(module);
        assertThat(errors, empty());
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getVariable().getName(), is("x"));
    }

    @Test
    public void variableReference() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: INTEGER; BEGIN x := x END Test.");
        List<ParserError> errors = NamesResolver.resolve(module);
        assertThat(errors, empty());
        Assignment assignment = (Assignment) module.getStatements().get(0);
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getDeclaration(), instanceOf(Variable.class));
        assertThat(ref.getDeclaration().getName(), is("x"));
    }

    @Test
    public void constantReference() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; CONST a = 3; VAR x: INTEGER; BEGIN x := a END Test.");
        List<ParserError> errors = NamesResolver.resolve(module);
        assertThat(errors, empty());
        Assignment assignment = (Assignment) module.getStatements().get(0);
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getDeclaration(), instanceOf(Constant.class));
        assertThat(ref.getDeclaration().getName(), is("a"));
    }

    @Test
    public void undefinedSymbolInReference() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: INTEGER; BEGIN x := a END Test.");
        List<ParserError> errors = NamesResolver.resolve(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void undefinedSymbolInAssignment() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; BEGIN x := 5 END Test.");
        List<ParserError> errors = NamesResolver.resolve(module);
        assertThat(errors, hasSize(1));
    }
}
