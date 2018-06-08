package yajco.oberon0;

import yajco.oberon0.model.*;
import yajco.oberon0.model.l3.*;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class L3CodeGenerator extends CCodeGenerator {
    public static void generate(Module module, PrintWriter writer) {
        new L3CodeGenerator().visit(module, writer);
    }

    @Override
    protected void visitModule(Module module, PrintWriter writer) {
        writer.println("#include \"oberon.h\";\n");

        List<Procedure> procedures;
        if (module.getDeclarations() instanceof DeclarationsWithProcedures) {
            procedures = ((DeclarationsWithProcedures) module.getDeclarations()).getProcedures();
        } else {
            procedures = Collections.emptyList();
        }

        for (Procedure procedure: procedures) {
            visit(procedure, writer);
        }

        writer.printf("\nvoid main() {\n");
        visit(module.getDeclarations(), writer);
        if (module.getStatements() != null)
            visit(module.getStatements(), writer);
        writer.printf("}\n");
    }

    @Override
    protected void visitProcedure(Procedure procedure, PrintWriter writer) {
        writer.printf("void %s(", procedure.getName());
        visit(procedure.getParameters(), writer);
        writer.print(") {\n");
        visit(procedure.getDeclarations(), writer);
        if (procedure.getStatements() != null)
            visit(procedure.getStatements(), writer);
        writer.print("}\n");
    }

    protected void visitFormalParameters(FormalParameters formalParameters, PrintWriter writer) {
        for (int i = 0; i < formalParameters.size(); i++) {
            Parameter parameter = formalParameters.get(i);
            writer.printf("%s %s%s",
                    "int",
                    parameter.isVariable() ? "*" : "",
                    parameter.getName());
            if (i < formalParameters.size() - 1) {
                writer.print(", ");
            }
        }
    }

    @Override
    protected void visitDeclarationsWithProcedures(DeclarationsWithProcedures declarationsWithProcedures, PrintWriter printWriter) {
        // pass
    }

    @Override
    protected void visitProceduresInDeclarationsWithProcedures(
            List<Procedure> procedures, PrintWriter writer) {
        // Do not visit procedures
    }

    @Override
    protected void visitProcedureCall(ProcedureCall procedureCall, PrintWriter writer) {
        writer.printf("%s(", procedureCall.getProcedure().getName());
        List<Expression> actualParameters = procedureCall.getActualParameters();
        FormalParameters formalParameters = procedureCall.getProcedure().getParameters();
        for (int i = 0; i < actualParameters.size(); i++) {
            if (formalParameters.get(i).isVariable()) {
                writer.write("&");
            }
            visit(actualParameters.get(i), writer);
            if (i < actualParameters.size() - 1) {
                writer.print(", ");
            }
        }
        writer.printf(");\n");
    }

    @Override
    protected void visitAssignment(Assignment assignment, PrintWriter writer) {
        dereferencePointer(assignment.getVariable(), writer);
        super.visitAssignment(assignment, writer);
    }

    @Override
    protected void visitReference(Reference reference, PrintWriter writer) {
        dereferencePointer(reference.getDeclaration(), writer);
        super.visitReference(reference, writer);
    }

    private void dereferencePointer(Declaration variable, PrintWriter writer) {
        if (variable instanceof Parameter && ((Parameter) variable).isVariable()) {
            writer.print("*");
        }
    }
}
