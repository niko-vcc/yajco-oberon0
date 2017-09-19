package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.*;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
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
        NamesResolver.resolve(module);
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getVariable().getName(), is("x"));
    }

    @Test
    public void variableReference() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: INTEGER; BEGIN x := x END Test.");
        NamesResolver.resolve(module);
        Assignment assignment = (Assignment) module.getStatements().get(0);
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getDeclaration(), instanceOf(Variable.class));
        assertThat(ref.getDeclaration().getName(), is("x"));
    }

    @Test
    public void constantReference() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; CONST a = 3; VAR x: INTEGER; BEGIN x := a END Test.");
        NamesResolver.resolve(module);
        Assignment assignment = (Assignment) module.getStatements().get(0);
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getDeclaration(), instanceOf(Constant.class));
        assertThat(ref.getDeclaration().getName(), is("a"));
    }

}
