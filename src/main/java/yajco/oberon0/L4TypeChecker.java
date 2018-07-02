package yajco.oberon0;

import yajco.oberon0.model.Module;

import java.util.List;

public class L4TypeChecker extends L2TypeChecker {

    public static List<ParserError> check(Module module) {
        TypeChecker checker = new L4TypeChecker();
        checker.visit(module, null);
        return checker.errors;
    }


}
