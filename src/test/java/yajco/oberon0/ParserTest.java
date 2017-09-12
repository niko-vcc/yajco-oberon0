package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.expressions.*;
import yajco.oberon0.expressions.Number;
import yajco.oberon0.parser.ParseException;
import yajco.oberon0.parser.LALRModuleParser;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParserTest {

    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
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
        assertThat(declarations.get(0), instanceOf(Variable.class));
        Variable constant = (Variable) declarations.get(0);
        assertEquals("n", constant.getName());
        assertThat(constant.getExpression(), instanceOf(Number.class));
        assertEquals(3, ((Number) constant.getExpression()).getValue());
    }

    @Test
    public void constantWithExpression() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; CONST n = 10 + (-5 - 2) * (3 DIV 2 MOD 4); END Sample.");
        Variable constant = (Variable) module.getDeclarations().get(0);
        assertThat(constant.getExpression(), instanceOf(Add.class));
        Add add = (Add) constant.getExpression();
        assertThat(add.getLeft(), instanceOf(Number.class));
        assertThat(add.getRight(), instanceOf(Mul.class));
        Mul mul = (Mul) add.getRight();
        assertThat(mul.getLeft(), instanceOf(Sub.class));
        assertThat(mul.getRight(), instanceOf(Mod.class));
    }

    @Test
    public void constantWithBooleanExpression() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; CONST a = (10 > 3) OR (3 # 5-1) & ~(4 <= 5); END Sample.");
        Variable constant = (Variable) module.getDeclarations().get(0);
        assertThat(constant.getExpression(), instanceOf(Or.class));
        Or or = (Or) constant.getExpression();
        assertThat(or.getLeft(), instanceOf(Greater.class));
        assertThat(or.getRight(), instanceOf(And.class));
        And and = (And) or.getRight();
        assertThat(and.getLeft(), instanceOf(NotEquals.class));
        assertThat(and.getRight(), instanceOf(Not.class));
    }

    @Test
    public void singleAssignment() throws ParseException {
        Module module = parser.parse("MODULE Single; VAR x: INTEGER; BEGIN x := 5 END Single.");
        assertEquals(1, module.getStatements().size());
        assertThat(module.getStatements().get(0), instanceOf(Assignment.class));
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getVariable().getName(), is("x"));
    }

    @Test
    public void variableReference() throws ParseException {
        Module module = parser.parse("MODULE Single; VAR x: INTEGER; BEGIN x := x END Single.");
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getExpression(), instanceOf(Reference.class));
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getVariable().isConstant(), is(false));
        assertThat(ref.getVariable().getName(), is("x"));
    }

    @Test
    public void constantReference() throws ParseException {
        Module module = parser.parse("MODULE Single; CONST a = 3; VAR x: INTEGER; BEGIN x := a END Single.");
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getExpression(), instanceOf(Reference.class));
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getVariable().isConstant(), is(true));
        assertThat(ref.getVariable().getName(), is("a"));
    }

    @Test
    public void ifStatement() throws ParseException {
        Module module = parser.parse(
                "MODULE Single; CONST a = 3; VAR x: INTEGER; BEGIN\n"
              + "  IF a = 3 THEN x := 1 END\n"
              + "END Single.");
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(ifStmt.getCondition(), instanceOf(Equals.class));
        assertThat(ifStmt.getThenBranch().get(0), instanceOf(Assignment.class));
    }

    @Test
    public void ifWithElse() throws ParseException {
        Module module = parser.parse(
                "MODULE Single; CONST a = 3; VAR x: INTEGER; BEGIN\n"
              + "  IF a = 3 THEN x := 1 ELSE x := 2 END\n"
              + "END Single.");
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(ifStmt.getCondition(), instanceOf(Equals.class));
        assertThat(ifStmt.getThenBranch().get(0), instanceOf(Assignment.class));
        assertThat(ifStmt.getElseBranch().get(0), instanceOf(Assignment.class));
    }
}
