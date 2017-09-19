package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.operators.*;
import yajco.oberon0.model.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

@Exclude
public class TypeChecker extends Visitor<Object> {
    private List<ParserError> errors = new ArrayList<>();

    private TypeChecker() {
    }

    public static List<ParserError> check(Module module) {
        TypeChecker checker = new TypeChecker();
        checker.visit(module, null);
        return checker.errors;
    }

    @Override
    protected void visitAssignment(Assignment assignment, Object o) {
        super.visitAssignment(assignment, o);
        if (!assignment.getVariable().getType().equals(
                assignment.getExpression().getType())) {
            errors.add(new ParserError("Not matching types in assignment."));
        }
    }

    @Override
    protected void visitIfStatement(IfStatement ifStatement, Object o) {
        super.visitIfStatement(ifStatement, o);
        if (!ifStatement.getCondition().getType().equals(Type.BOOLEAN)) {
            errors.add(new ParserError("Condition in IF must be boolean."));
        }
    }

    @Override
    protected void visitWhileStatement(WhileStatement whileStatement, Object o) {
        super.visitWhileStatement(whileStatement, o);
        if (!whileStatement.getCondition().getType().equals(Type.BOOLEAN)) {
            errors.add(new ParserError("Condition in WHILE must be boolean."));
        }
    }

    @Override
    protected void visitAdd(Add add, Object o) {
        super.visitAdd(add, o);
        checkBinaryOperator(add, Type.INTEGER, "+");
    }

    @Override
    protected void visitSub(Sub sub, Object o) {
        super.visitSub(sub, o);
        checkBinaryOperator(sub, Type.INTEGER, "-");
    }

    @Override
    protected void visitMul(Mul mul, Object o) {
        super.visitMul(mul, o);
        checkBinaryOperator(mul, Type.INTEGER, "*");
    }

    @Override
    protected void visitDiv(Div div, Object o) {
        super.visitDiv(div, o);
        checkBinaryOperator(div, Type.INTEGER, "DIV");
    }

    @Override
    protected void visitMod(Mod mod, Object o) {
        super.visitMod(mod, o);
        checkBinaryOperator(mod, Type.INTEGER, "MOD");
    }

    @Override
    protected void visitAnd(And and, Object o) {
        super.visitAnd(and, o);
        checkBinaryOperator(and, Type.BOOLEAN, "&");
    }

    @Override
    protected void visitOr(Or or, Object o) {
        super.visitOr(or, o);
        checkBinaryOperator(or, Type.BOOLEAN, "OR");
    }

    @Override
    protected void visitLess(Less less, Object o) {
        super.visitLess(less, o);
        checkBinaryOperator(less, Type.INTEGER, "<");
    }

    @Override
    protected void visitLessEquals(LessEquals lessEquals, Object o) {
        super.visitLessEquals(lessEquals, o);
        checkBinaryOperator(lessEquals, Type.INTEGER, "<=");
    }

    @Override
    protected void visitGreater(Greater greater, Object o) {
        super.visitGreater(greater, o);
        checkBinaryOperator(greater, Type.INTEGER, ">");
    }

    @Override
    protected void visitGreaterEquals(GreaterEquals greaterEquals, Object o) {
        super.visitGreaterEquals(greaterEquals, o);
        checkBinaryOperator(greaterEquals, Type.INTEGER, ">=");
    }

    @Override
    protected void visitNot(Not not, Object o) {
        super.visitNot(not, o);
        if (!not.getOperand().getType().equals(Type.BOOLEAN)) {
            errors.add(new ParserError("Invalid type of operand of ~"));
        }
    }

    private void checkBinaryOperator(BinaryOperation and, Type type, String name) {
        if (!and.getLeft().getType().equals(type)
                || !and.getRight().getType().equals(type)) {
            errors.add(new ParserError("Invalid type of operand of " + name));
        }
    }
}
