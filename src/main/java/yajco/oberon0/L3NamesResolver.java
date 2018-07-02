package yajco.oberon0;

import yajco.oberon0.model.*;
import yajco.oberon0.model.l3.Parameter;
import yajco.oberon0.model.l3.Procedure;
import yajco.oberon0.model.l3.ProcedureCall;

import java.util.List;

public class L3NamesResolver extends NamesResolver {

    public static List<ParserError> resolve(Module module) {
        NamesResolver resolver = new L3NamesResolver();
        resolver.visit(module, null);
        return resolver.errors;
    }

    @Override
    protected void visitModule(Module module, SymbolTable __) {
        SymbolTable declarations = new StackedDeclarations(
                module.getDeclarations(), new StandardDeclarations());
        if (module.getDeclarations() != null && enterVisit(module.getDeclarations())) {
            visitDeclarations(module.getDeclarations(), declarations);
            exitVisit(module.getDeclarations());
        }
        if (module.getStatements() != null && enterVisit(module.getStatements())) {
            visitStatementSequence(module.getStatements(), declarations);
            exitVisit(module.getStatements());
        }
    }

    @Override
    protected void visitProcedureCall(ProcedureCall procedureCall,
                                      SymbolTable declarations) {
        Declaration declaration = declarations.get(procedureCall.getName());
        if (!(declaration instanceof Procedure)) {
            errors.add(new ParserError(
                    String.format("Undefined procedure '%s'", procedureCall.getName())));
        } else {
            procedureCall.setProcedure((Procedure) declaration);
        }
        super.visitProcedureCall(procedureCall, declarations);
    }

    @Override
    protected void visitProcedure(Procedure procedure, SymbolTable declarations) {
        SymbolTable procedureDeclarations =
            mergeParametersWithDeclarations(procedure);
        super.visitProcedure(procedure,
                new StackedDeclarations(procedureDeclarations, declarations));
    }

    private SymbolTable mergeParametersWithDeclarations(Procedure procedure) {
        Declarations declarations = procedure.getDeclarations();
        for (Parameter parameter : procedure.getParameters()) {
            declarations.put(parameter.getName(), parameter);
        }
        return declarations;
    }
}
