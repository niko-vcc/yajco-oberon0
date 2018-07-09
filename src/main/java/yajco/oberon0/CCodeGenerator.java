package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.Number;
import yajco.oberon0.model.operators.*;
import yajco.oberon0.model.visitor.Visitor;

import java.io.PrintWriter;

@Exclude
public class CCodeGenerator extends Visitor<PrintWriter> {
    public static void generate(Module module, PrintWriter printWriter) {
        new CCodeGenerator().visit(module, printWriter);
    }

    @Override
    protected void visitModule(Module module, PrintWriter writer) {
        writer.printf("void main() {\n");
        visit(module.getDeclarations(), writer);
        if (module.getStatements() != null)
            visit(module.getStatements(), writer);
        writer.printf("}\n");
    }

    protected void visitVariablesGroup(VariablesGroup variablesGroup, PrintWriter writer) {
        // Visit only variables in group, not the type
        // Type would be printed with the variables
        if (variablesGroup != null) {
            visitVariablesInVariablesGroup(variablesGroup.getVariables(), writer);
        }
    }

    @Override
    protected void visitVariable(Variable variable, PrintWriter writer) {
        visit(variable.getType(), writer);
        writer.printf(String.format(" %s;\n", variable.getName()));
    }

    @Override
    protected void visitConstant(Constant constant, PrintWriter writer) {
        writer.print("const ");
        visit(constant.getType(), writer);
        writer.printf(String.format(" %s = ", constant.getName()));
        visit(constant.getExpression(), writer);
        writer.print(";\n");
    }

    @Override
    protected void visitType(Type type, PrintWriter writer) {
        if (type instanceof PrimitiveType)
            visitPrimitiveType((PrimitiveType) type, writer);
        else
            super.visitType(type, writer);
    }

    @Override
    protected void visitTypeReference(TypeReference typeReference, PrintWriter writer) {
        if (typeReference.getRealType() instanceof PrimitiveType) {
            visit(typeReference.getRealType(), writer);
        } else {
            writer.print(typeReference.getName());
        }
    }

    protected void visitPrimitiveType(PrimitiveType type, PrintWriter writer) {
        switch (type.getName()) {
            case "INTEGER":
                writer.print("int");
                break;
            case "BOOLEAN":
                writer.print("bool");
                break;
        }
    }

    @Override
    protected void visitAssignment(Assignment assignment, PrintWriter writer) {
        visit(assignment.getReference(), writer);
        writer.print(" = ");
        visit(assignment.getExpression(), writer);
        writer.print(";\n");
    }

    @Override
    protected void visitWhileStatement(WhileStatement whileStatement, PrintWriter writer) {
        writer.printf("while (");
        visit(whileStatement.getCondition(), writer);
        writer.printf(") {\n");
        visit(whileStatement.getBody(), writer);
        writer.printf("}\n");
    }

    @Override
    protected void visitIfStatement(IfStatement ifStatement, PrintWriter writer) {
        writer.printf("if (");
        visit(ifStatement.getCondition(), writer);
        writer.printf(") {\n");
        visit(ifStatement.getThenBranch(), writer);
        writer.printf("}");
        if (ifStatement.getElseBranch() != null) {
            writer.printf(" else {\n");
            visit(ifStatement.getElseBranch(), writer);
            writer.printf("}");
        }
        writer.printf("\n");
    }

    @Override
    protected void visitNumber(Number number, PrintWriter writer) {
        writer.printf("%d", number.getValue());
    }

    @Override
    protected void visitReference(Reference reference, PrintWriter writer) {
        writer.printf(reference.getDeclaration().getName());
    }

    @Override
    protected void visitAdd(Add add, PrintWriter writer) {
        translateBinaryOperator(add, "+", writer);
    }

    @Override
    protected void visitSub(Sub sub, PrintWriter writer) {
        translateBinaryOperator(sub, "-", writer);
    }

    @Override
    protected void visitMul(Mul mul, PrintWriter writer) {
        translateBinaryOperator(mul, "*", writer);
    }

    @Override
    protected void visitDiv(Div div, PrintWriter writer) {
        translateBinaryOperator(div, "/", writer);
    }

    @Override
    protected void visitMod(Mod mod, PrintWriter writer) {
        translateBinaryOperator(mod, "%", writer);
    }

    @Override
    protected void visitNotEquals(NotEquals notEquals, PrintWriter writer) {
        translateBinaryOperator(notEquals, "!=", writer);
    }

    @Override
    protected void visitEquals(Equals equals, PrintWriter writer) {
        translateBinaryOperator(equals, "==", writer);
    }

    @Override
    protected void visitAnd(And and, PrintWriter writer) {
        translateBinaryOperator(and, "&&", writer);
    }

    @Override
    protected void visitOr(Or or, PrintWriter writer) {
        translateBinaryOperator(or, "||", writer);
    }

    @Override
    protected void visitGreaterEquals(GreaterEquals greaterEquals, PrintWriter writer) {
        translateBinaryOperator(greaterEquals, ">=", writer);
    }

    @Override
    protected void visitLessEquals(LessEquals lessEquals, PrintWriter writer) {
        translateBinaryOperator(lessEquals, "<=", writer);
    }

    @Override
    protected void visitLess(Less less, PrintWriter writer) {
        translateBinaryOperator(less, "<", writer);
    }

    @Override
    protected void visitGreater(Greater greater, PrintWriter writer) {
        translateBinaryOperator(greater, ">", writer);
    }

    private void translateBinaryOperator(BinaryOperation operator,
                                         String symbol, PrintWriter writer) {
        writer.printf("(");
        visit(operator.getLeft(), writer);
        writer.printf(" %s ", symbol);
        visit(operator.getRight(), writer);
        writer.printf(")");
    }
}
