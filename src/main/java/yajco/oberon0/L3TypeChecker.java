package yajco.oberon0;

import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.l3.Parameter;
import yajco.oberon0.model.l3.Procedure;
import yajco.oberon0.model.l3.ProcedureCall;

import java.util.List;

public class L3TypeChecker extends L2TypeChecker {

    public static List<ParserError> check(Module module) {
        TypeChecker checker = new L3TypeChecker();
        checker.visit(module, null);
        return checker.errors;
    }

    @Override
    protected void visitProcedureCall(ProcedureCall procedureCall, Object o) {
        super.visitProcedureCall(procedureCall, o);
        Procedure procedure = procedureCall.getProcedure();
        List<Expression> arguments = procedureCall.getActualParameters();
        if (procedure.getParameters().size() != arguments.size()) {
            errors.add(new ParserError("Invalid number of arguments: expected %d, got %d.",
                    procedure.getParameters().size(), arguments.size()));
        }
        for (int i = 0; i < procedure.getParameters().size(); i++) {
            Parameter parameter = procedure.getParameters().get(i);
            Expression argument = arguments.get(i);
            if (!(parameter.getType().matches(argument.getType()))) {
                errors.add(new ParserError("Incompatible type of parameter %s in call of %s: expected %s, got %s.",
                        parameter.getName(), procedure.getName(), parameter.getType(), argument.getType()));
            }
        }
    }
}
