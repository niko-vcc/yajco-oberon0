package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.operators.*;
import yajco.oberon0.model.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

@Exclude
public class TypeChecker extends Visitor<Object> {
    protected List<ParserError> errors = new ArrayList<>();

    public static List<ParserError> check(Module module) {
        TypeChecker checker = new TypeChecker();
        checker.visit(module, null);
        return checker.errors;
    }

    @Override
    protected void visitAssignment(Assignment assignment, Object o) {
        super.visitAssignment(assignment, o);
        if (!assignment.getReference().getType().matches(
                assignment.getExpression().getType())) {
            errors.add(new ParserError("Not matching types in assignment."));
        }
    }

    @Override
    protected void visitIfStatement(IfStatement ifStatement, Object o) {
        super.visitIfStatement(ifStatement, o);
        if (!ifStatement.getCondition().getType().equals(PrimitiveType.BOOLEAN)) {
            errors.add(new ParserError("Condition in IF must be boolean."));
        }
    }

    @Override
    protected void visitWhileStatement(WhileStatement whileStatement, Object o) {
        super.visitWhileStatement(whileStatement, o);
        if (!whileStatement.getCondition().getType().equals(PrimitiveType.BOOLEAN)) {
            errors.add(new ParserError("Condition in WHILE must be boolean."));
        }
    }

    @Override
    protected void visitAdd(Add add, Object o) {
        super.visitAdd(add, o);
        checkBinaryOperator(add, PrimitiveType.INTEGER, "+");
    }

    @Override
    protected void visitSub(Sub sub, Object o) {
        super.visitSub(sub, o);
        checkBinaryOperator(sub, PrimitiveType.INTEGER, "-");
    }

    @Override
    protected void visitMul(Mul mul, Object o) {
        super.visitMul(mul, o);
        checkBinaryOperator(mul, PrimitiveType.INTEGER, "*");
    }

    @Override
    protected void visitDiv(Div div, Object o) {
        super.visitDiv(div, o);
        checkBinaryOperator(div, PrimitiveType.INTEGER, "DIV");
    }

    @Override
    protected void visitMod(Mod mod, Object o) {
        super.visitMod(mod, o);
        checkBinaryOperator(mod, PrimitiveType.INTEGER, "MOD");
    }

    @Override
    protected void visitAnd(And and, Object o) {
        super.visitAnd(and, o);
        checkBinaryOperator(and, PrimitiveType.BOOLEAN, "&");
    }

    @Override
    protected void visitOr(Or or, Object o) {
        super.visitOr(or, o);
        checkBinaryOperator(or, PrimitiveType.BOOLEAN, "OR");
    }

    @Override
    protected void visitLess(Less less, Object o) {
        super.visitLess(less, o);
        checkBinaryOperator(less, PrimitiveType.INTEGER, "<");
    }

    @Override
    protected void visitLessEquals(LessEquals lessEquals, Object o) {
        super.visitLessEquals(lessEquals, o);
        checkBinaryOperator(lessEquals, PrimitiveType.INTEGER, "<=");
    }

    @Override
    protected void visitGreater(Greater greater, Object o) {
        super.visitGreater(greater, o);
        checkBinaryOperator(greater, PrimitiveType.INTEGER, ">");
    }

    @Override
    protected void visitGreaterEquals(GreaterEquals greaterEquals, Object o) {
        super.visitGreaterEquals(greaterEquals, o);
        checkBinaryOperator(greaterEquals, PrimitiveType.INTEGER, ">=");
    }

    @Override
    protected void visitNot(Not not, Object o) {
        super.visitNot(not, o);
        if (!not.getOperand().getType().matches(PrimitiveType.BOOLEAN)) {
            errors.add(new ParserError("Invalid type of operand of ~"));
        }
    }

    private void checkBinaryOperator(BinaryOperation and, Type type, String name) {
        if (!and.getLeft().getType().matches(type)
                || !and.getRight().getType().matches(type)) {
            errors.add(new ParserError("Invalid type of operand of " + name));
        }
    }
}
