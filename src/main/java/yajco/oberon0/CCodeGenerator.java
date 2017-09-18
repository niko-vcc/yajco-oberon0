package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.Number;
import yajco.oberon0.model.operators.*;
import yajco.oberon0.model.visitor.Visitor;

@Exclude
public class CCodeGenerator extends Visitor<StringBuilder> {
    public static String generate(Module module) {
        StringBuilder builder = new StringBuilder();
        new CCodeGenerator().visit(module, builder);
        return builder.toString();
    }

    @Override
    protected void visitModule(Module module, StringBuilder builder) {
        builder.append("void main() {\n");
        visit(module.getDeclarations(), builder);
        if (module.getStatements() != null)
            visit(module.getStatements(), builder);
        builder.append("}\n");
    }

    @Override
    protected void visitVariable(Variable variable, StringBuilder builder) {
        builder.append(String.format("%s %s;\n", "int", variable.getName()));
    }

    @Override
    protected void visitAssignment(Assignment assignment, StringBuilder builder) {
        builder.append(String.format("%s = ", assignment.getVariable().getName()));
        visit(assignment.getExpression(), builder);
        builder.append(";\n");
    }

    @Override
    protected void visitWhileStatement(WhileStatement whileStatement, StringBuilder builder) {
        builder.append("while (");
        visit(whileStatement.getCondition(), builder);
        builder.append(") {\n");
        visit(whileStatement.getBody(), builder);
        builder.append("}\n");
    }

    @Override
    protected void visitIfStatement(IfStatement ifStatement, StringBuilder builder) {
        builder.append("if (");
        visit(ifStatement.getCondition(), builder);
        builder.append(") {\n");
        visit(ifStatement.getThenBranch(), builder);
        builder.append("}");
        if (ifStatement.getElseBranch() != null) {
            builder.append(" else {\n");
            visit(ifStatement.getElseBranch(), builder);
            builder.append("}");
        }
        builder.append("\n");
    }

    @Override
    protected void visitNumber(Number number, StringBuilder builder) {
        builder.append(number.getValue());
    }

    @Override
    protected void visitReference(Reference reference, StringBuilder builder) {
        builder.append(reference.getDeclaration().getName());
    }

    @Override
    protected void visitAdd(Add add, StringBuilder builder) {
        translateBinaryOperator(add, "+", builder);
    }

    @Override
    protected void visitSub(Sub sub, StringBuilder builder) {
        translateBinaryOperator(sub, "-", builder);
    }

    @Override
    protected void visitMul(Mul mul, StringBuilder builder) {
        translateBinaryOperator(mul, "*", builder);
    }

    @Override
    protected void visitDiv(Div div, StringBuilder builder) {
        translateBinaryOperator(div, "/", builder);
    }

    @Override
    protected void visitMod(Mod mod, StringBuilder builder) {
        translateBinaryOperator(mod, "%", builder);
    }

    @Override
    protected void visitNotEquals(NotEquals notEquals, StringBuilder builder) {
        translateBinaryOperator(notEquals, "!=", builder);
    }

    @Override
    protected void visitEquals(Equals equals, StringBuilder builder) {
        translateBinaryOperator(equals, "==", builder);
    }

    @Override
    protected void visitAnd(And and, StringBuilder builder) {
        translateBinaryOperator(and, "&&", builder);
    }

    @Override
    protected void visitOr(Or or, StringBuilder builder) {
        translateBinaryOperator(or, "||", builder);
    }

    @Override
    protected void visitGreaterEquals(GreaterEquals greaterEquals, StringBuilder builder) {
        translateBinaryOperator(greaterEquals, ">=", builder);
    }

    @Override
    protected void visitLessEquals(LessEquals lessEquals, StringBuilder builder) {
        translateBinaryOperator(lessEquals, "<=", builder);
    }

    @Override
    protected void visitLess(Less less, StringBuilder builder) {
        translateBinaryOperator(less, "<", builder);
    }

    @Override
    protected void visitGreater(Greater greater, StringBuilder builder) {
        translateBinaryOperator(greater, ">", builder);
    }

    private void translateBinaryOperator(BinaryOperation operator,
                                         String symbol, StringBuilder builder) {
        builder.append("(");
        visit(operator.getLeft(), builder);
        builder.append(" ").append(symbol).append(" ");
        visit(operator.getRight(), builder);
        builder.append(")");
    }
}
