package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.Declarations;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.l3.DeclarationsWithProcedures;
import yajco.oberon0.model.l3.Procedure;
import yajco.oberon0.model.visitor.Visitor;

import java.util.List;

@Exclude
class L3Transformation extends Visitor<DeclarationsWithProcedures> {
    public static void liftProcedures(Module module) {
        new L3Transformation().visit(module, null);
    }

    @Override
    protected void visitDeclarationsWithProcedures(DeclarationsWithProcedures current, DeclarationsWithProcedures parent) {
        super.visitDeclarationsWithProcedures(current, current);
    }

    @Override
    protected void visitProcedure(Procedure procedure, DeclarationsWithProcedures parentDeclarations) {
        super.visitProcedure(procedure, parentDeclarations);
        Declarations declarations = procedure.getDeclarations();
        if (!(declarations instanceof DeclarationsWithProcedures))
            return;

        List<Procedure> nestedProcedures = ((DeclarationsWithProcedures) declarations).getProcedures();
        for (Procedure nestedProcedure : nestedProcedures) {
            declarations.remove(nestedProcedure.getName());
            mangleName(nestedProcedure, procedure);
            parentDeclarations.put(nestedProcedure.getName(), nestedProcedure);
        }
    }

    private void mangleName(Procedure nestedProcedure, Procedure parentProcedure) {
        String newName = parentProcedure.getName() + nestedProcedure.getName();
        nestedProcedure.setName(newName);
    }
}
