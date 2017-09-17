package yajco.oberon0.model;

import yajco.annotation.Before;

import java.util.ArrayList;
import java.util.List;

public class TypesDeclaration extends ArrayList<DeclaredType> {
    public TypesDeclaration() {
    }

    @Before("TYPE")
    public TypesDeclaration(List<DeclaredType> declarations) {
        addAll(declarations);
    }

    public List<DeclaredType> getDeclarations() {
        return this;
    }
}
