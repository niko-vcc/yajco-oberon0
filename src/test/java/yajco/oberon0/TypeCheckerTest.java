package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.Constant;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.PrimitiveType;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TypeCheckerTest {
    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Test
    public void literalInConstant() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = 3; END Sample.");
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, empty());
        Constant constant = module.getDeclarations().getConstants().get(0);
        assertThat(constant.getType(), is(PrimitiveType.INTEGER));
    }

    @Test
    public void expressionInConstant() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = 1 + 2 - 3 * 4 DIV 5 MOD 6; END Sample.");
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, empty());
        Constant constant = module.getDeclarations().getConstants().get(0);
        assertThat(constant.getType(), is(PrimitiveType.INTEGER));
    }

    @Test
    public void booleanExpressionInConstant() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = (1 > 2) & (3 < 4) OR ~(5 = 6); END Sample.");
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, empty());
        Constant constant = module.getDeclarations().getConstants().get(0);
        assertThat(constant.getType(), is(PrimitiveType.BOOLEAN));
    }

    @Test
    public void invalidOperandOfAnd() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = (1 > 2) & 3; END Sample.");
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void multipleInvalidOperands() throws ParseException {
        Module module = parser.parse("MODULE Sample; CONST n = (1 > 2 > 3) OR (3 + (3 - (5 # 6))); END Sample.");
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(3));
    }

    @Test
    public void validIntegerAssignment() throws ParseException {
        Module module = parser.parse("MODULE Sample; VAR x: INTEGER; BEGIN x := 1 END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(0));
    }

    @Test
    public void invalidIntegerAssignment() throws ParseException {
        Module module = parser.parse("MODULE Sample; VAR x: INTEGER; BEGIN x := 1 <= 2 END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void customTypeAssignment() throws ParseException {
        Module module = parser.parse("MODULE Sample; TYPE T = INTEGER; VAR x: T; BEGIN x := 1 END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(0));
    }

    @Test
    public void validIfCondition() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; VAR x: INTEGER; BEGIN IF 1 > 2 THEN x := 1 END END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(0));
    }

    @Test
    public void invalidIfCondition() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; VAR x: INTEGER; BEGIN IF 1 THEN x := 1 END END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void validWhileCondition() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; VAR x: INTEGER; BEGIN WHILE 1 > 2 DO x := 1 END END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(0));
    }

    @Test
    public void invalidWhileCondition() throws ParseException {
        Module module = parser.parse(
                "MODULE Sample; VAR x: INTEGER; BEGIN WHILE 1 DO x := 1 END END Sample.");
        NamesResolver.resolve(module);
        List<ParserError> errors = TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }
}
