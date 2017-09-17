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
        assertThat(module.getName(), is("Empty"));
    }

    @Test(expected = RuntimeException.class)
    public void unmatchingModuleName() throws ParseException {
        parser.parse("MODULE First; END Second.");
    }

    @Test
    public void variableDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; VAR x: INTEGER; END Sample.");
        List<Variable> variables = module.getDeclarations().getVariables();
        assertThat(variables.size(), is(1));
        assertThat(variables.get(0).getName(), is("x"));
        assertThat(variables.get(0), instanceOf(Variable.class));
        Type type = variables.get(0).getType();
        assertThat(type.getName(), is("INTEGER"));
    }

    @Test
    public void multipleVariableDeclarations() throws ParseException {
        Module module = parser.parse("MODULE Test; VAR x, y: INTEGER; END Test.");
        List<Variable> variables = module.getDeclarations().getVariables();
        assertThat(variables.size(), is(2));
        assertThat(variables.get(0).getName(), is("x"));
        assertThat(variables.get(1).getName(), is("y"));
    }

    @Test
    public void constantDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = 3; END Sample.");
        List<Constant> constants = module.getDeclarations().getConstants();
        assertThat(constants.size(), is(1));
        assertThat(constants.get(0), instanceOf(Constant.class));
        Constant constant = constants.get(0);
        assertThat(constant.getName(), is("n"));
        assertThat(constant.getExpression(), instanceOf(Number.class));
        assertThat(((Number) constant.getExpression()).getValue(), is(3));
    }

    @Test
    public void typeDeclaration() throws ParseException {
        Module module = parser.parse("MODULE Sample; TYPE T = INTEGER; END Sample.");
        List<DeclaredType> types = module.getDeclarations().getTypes();
        assertThat(types.size(), is(1));
        DeclaredType declaredType = types.get(0);
        assertThat(declaredType.getName(), is("T"));
        assertThat(declaredType.getType().getName(), is("INTEGER"));
    }

    @Test
    public void constantWithExpression() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; CONST n = 10 + (-5 - 2) * (3 DIV 2 MOD 4); END Sample.");
        Constant constant = module.getDeclarations().getConstants().get(0);
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
        Constant constant = module.getDeclarations().getConstants().get(0);
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
        NamesResolver.resolve(module);
        assertThat(module.getStatements().size(), is(1));
        assertThat(module.getStatements().get(0), instanceOf(Assignment.class));
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getVariable().getName(), is("x"));
    }

    @Test
    public void variableReference() throws ParseException {
        Module module = parser.parse("MODULE Single; VAR x: INTEGER; BEGIN x := x END Single.");
        NamesResolver.resolve(module);
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getExpression(), instanceOf(Reference.class));
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getEntity(), instanceOf(Variable.class));
        assertThat(ref.getEntity().getName(), is("x"));
    }

    @Test
    public void constantReference() throws ParseException {
        Module module = parser.parse("MODULE Single; CONST a = 3; VAR x: INTEGER; BEGIN x := a END Single.");
        NamesResolver.resolve(module);
        Assignment assignment = (Assignment) module.getStatements().get(0);
        assertThat(assignment.getExpression(), instanceOf(Reference.class));
        Reference ref = (Reference) assignment.getExpression();
        assertThat(ref.getEntity(), instanceOf(Constant.class));
        assertThat(ref.getEntity().getName(), is("a"));
    }

    @Test
    public void ifStatement() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; CONST a = 3; VAR x: INTEGER; BEGIN\n"
              + "  IF a = 3 THEN x := 1 END\n"
              + "END Test.");
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(ifStmt.getCondition(), instanceOf(Equals.class));
        assertThat(ifStmt.getThenBranch().get(0), instanceOf(Assignment.class));
    }

    @Test
    public void ifWithElse() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; CONST a = 3; VAR x: INTEGER; BEGIN\n"
              + "  IF a = 3 THEN x := 1 ELSIF a < 5 THEN x := 2 ELSE x := 3 END\n"
              + "END Test.");
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(ifStmt.getCondition(), instanceOf(Equals.class));
        assertThat(ifStmt.getThenBranch().get(0), instanceOf(Assignment.class));
        assertThat(ifStmt.getElseBranch().get(0), instanceOf(IfStatement.class));
        IfStatement secondIf = (IfStatement) ifStmt.getElseBranch().get(0);
        assertThat(secondIf.getCondition(), instanceOf(Less.class));
        assertThat(secondIf.getThenBranch().get(0), instanceOf(Assignment.class));
        assertThat(secondIf.getElseBranch().get(0), instanceOf(Assignment.class));
    }

    @Test
    public void ifWithElsif() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; CONST a = 3; VAR x: INTEGER; BEGIN\n"
              + "  IF a = 3 THEN x := 1 ELSE x := 2 END\n"
              + "END Test.");
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(ifStmt.getCondition(), instanceOf(Equals.class));
        assertThat(ifStmt.getThenBranch().get(0), instanceOf(Assignment.class));
        assertThat(ifStmt.getElseBranch().get(0), instanceOf(Assignment.class));
    }

    @Test
    public void whileStatement() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: INTEGER; BEGIN\n"
              + "  x := 2;\n"
              + "  WHILE x < 100 DO x := x * 3 END\n"
              + "END Test.");
        assertThat(module.getStatements().get(1), instanceOf(WhileStatement.class));
        WhileStatement whileStmt = (WhileStatement) module.getStatements().get(1);
        assertThat(whileStmt.getCondition(), instanceOf(Less.class));
        assertThat(whileStmt.getBody().get(0), instanceOf(Assignment.class));
    }
}
