package yajco.oberon0.model;

import yajco.annotation.Before;

import java.util.ArrayList;
import java.util.List;

public class TypeDeclarations extends ArrayList<TypeDeclaration> {
    public TypeDeclarations() {
    }

    @Before("TYPE")
    public TypeDeclarations(List<TypeDeclaration> declarations) {
        addAll(declarations);
    }

    public List<TypeDeclaration> getDeclarations() {
        return this;
    }
}
