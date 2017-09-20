package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.PrimitiveType;
import yajco.oberon0.model.l2.ForStatement;

import java.util.List;

public class L2TypeChecker extends TypeChecker {

    public static List<ParserError> check(Module module) {
        TypeChecker checker = new L2TypeChecker();
        checker.visit(module, null);
        return checker.errors;
    }

    @Override
    protected void visitForStatement(ForStatement forStatement, Object o) {
        super.visitForStatement(forStatement, o);
        if (!forStatement.getTarget().getType().matches(PrimitiveType.INTEGER)) {
            errors.add(new ParserError("Target in FOR statement must be a number"));
        }
    }
}
