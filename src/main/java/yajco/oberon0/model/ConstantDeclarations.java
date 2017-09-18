package yajco.oberon0.model;

import yajco.annotation.Before;

import java.util.*;

public class ConstantDeclarations extends ArrayList<Constant> {
    public ConstantDeclarations() {
    }

    @Before("CONST")
    public ConstantDeclarations(List<Constant> declarations) {
        addAll(declarations);
    }

    public List<Constant> getDeclarations() {
        return this;
    }
}
