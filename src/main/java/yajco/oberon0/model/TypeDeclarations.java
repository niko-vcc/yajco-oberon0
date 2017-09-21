package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Range;

import java.util.ArrayList;
import java.util.List;

public class TypeDeclarations extends ArrayList<TypeDeclaration> {
    public TypeDeclarations() {
    }

    @Before("TYPE")
    public TypeDeclarations(@Range(minOccurs = 1) List<TypeDeclaration> declarations) {
        addAll(declarations);
    }

    public List<TypeDeclaration> getDeclarations() {
        return this;
    }
}
