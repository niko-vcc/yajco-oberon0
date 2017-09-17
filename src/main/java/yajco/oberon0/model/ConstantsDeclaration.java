package yajco.oberon0.model;

import yajco.annotation.Before;

import java.util.*;

public class ConstantsDeclaration extends ArrayList<Constant> {
    public ConstantsDeclaration() {
    }

    @Before("CONST")
    public ConstantsDeclaration(List<Constant> declarations) {
        addAll(declarations);
    }

    public List<Constant> getDeclarations() {
        return this;
    }
}
