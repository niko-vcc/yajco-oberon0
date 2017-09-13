package yajco.oberon0;

import yajco.annotation.Before;

import java.util.Collections;
import java.util.List;

public class ConstantsDeclaration {
    private final List<Constant> declarations;

    public ConstantsDeclaration() {
        declarations = Collections.emptyList();
    }

    @Before("CONST")
    public ConstantsDeclaration(List<Constant> declarations) {

        this.declarations = declarations;
    }

    public List<Constant> getDeclarations() {
        return declarations;
    }
}
