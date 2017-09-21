package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Range;

import java.util.*;

public class ConstantDeclarations extends ArrayList<Constant> {
    public ConstantDeclarations() {
    }

    @Before("CONST")
    public ConstantDeclarations(@Range(minOccurs = 1) List<Constant> declarations) {
        addAll(declarations);
    }

    public List<Constant> getDeclarations() {
        return this;
    }
}
