package yajco.oberon0.model.l3;

import yajco.annotation.Range;
import yajco.oberon0.model.ConstantDeclarations;
import yajco.oberon0.model.Declarations;
import yajco.oberon0.model.TypeDeclarations;
import yajco.oberon0.model.VariableDeclarations;

import java.util.List;

public class DeclarationsWithProcedures extends Declarations {

    public DeclarationsWithProcedures(
            ConstantDeclarations constants,
            TypeDeclarations types,
            VariableDeclarations variables,
            @Range(minOccurs = 1) List<Procedure> procedures) {
        super(constants, types, variables);
        this.addDeclarations(procedures);
    }

    public List<Procedure> getProcedures() {
        return this.symbolsByType(Procedure.class);
    }
}
