package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.Statement;
import yajco.oberon0.model.l3.DeclarationsWithProcedures;
import yajco.oberon0.model.l3.FormalParameters;
import yajco.oberon0.model.l3.Procedure;
import yajco.oberon0.model.l3.ProcedureCall;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class L3Test {
    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Test
    public void unparametrizedProcedureCall() throws ParseException {
        Module module = parser.parse("MODULE Test; BEGIN Hello END Test.");
        Statement call = module.getStatements().get(0);
        assertThat(((ProcedureCall) call).getName(), is("Hello"));
    }

    @Test
    public void parametrizedProcedureCall() throws ParseException {
        Module module = parser.parse("MODULE Test; BEGIN Multiply(2, 4) END Test.");
        Statement statement = module.getStatements().get(0);
        ProcedureCall call = (ProcedureCall) statement;
        assertThat(call.getActualParameters().size(), is(2));
    }

    @Test
    public void emptyProcedure() throws ParseException {
        Module module = parser.parse("MODULE Test; PROCEDURE Hello; END Hello; END Test.");
        DeclarationsWithProcedures declarations = (DeclarationsWithProcedures) module.getDeclarations();
        assertThat(declarations.getProcedures().size(), is(1));
        assertThat(declarations.getProcedures().get(0).getName(), is("Hello"));
    }

    @Test
    public void procedureWithStatements() throws ParseException {
        Module module = parser.parse("MODULE Test; PROCEDURE Hello; BEGIN x := 1; y := 2 END Hello; END Test.");
        DeclarationsWithProcedures declarations = (DeclarationsWithProcedures) module.getDeclarations();
        Procedure procedure = declarations.getProcedures().get(0);
        assertThat(procedure.getStatements().size(), is(2));
    }

    @Test
    public void procedureWithDeclarations() throws ParseException {
        Module module = parser.parse("MODULE Test; PROCEDURE Hello; CONST a = 0; VAR x, y: INTEGER; END Hello; END Test.");
        DeclarationsWithProcedures declarations = (DeclarationsWithProcedures) module.getDeclarations();
        Procedure procedure = declarations.getProcedures().get(0);
        assertThat(procedure.getDeclarations().size(), is(3));
    }

    @Test
    public void procedureWithParameters() throws ParseException {
        Module module = parser.parse("MODULE Test;" +
                "PROCEDURE Multiply (x, y: INTEGER; VAR z: INTEGER); BEGIN z := x * y END Multiply;" +
                "END Test.");
        DeclarationsWithProcedures declarations = (DeclarationsWithProcedures) module.getDeclarations();
        FormalParameters parameters = declarations.getProcedures().get(0).getParameters();
        assertThat(parameters.size(), is(3));
        assertThat(parameters.get(0).getName(), is("x"));
        assertThat(parameters.get(0).isVariable(), is(false));
        assertThat(parameters.get(1).getName(), is("y"));
        assertThat(parameters.get(1).isVariable(), is(false));
        assertThat(parameters.get(2).getName(), is("z"));
        assertThat(parameters.get(2).isVariable(), is(true));
    }
}
