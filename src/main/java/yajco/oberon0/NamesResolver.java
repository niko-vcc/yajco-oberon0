package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

import static yajco.oberon0.model.Boolean.FALSE;
import static yajco.oberon0.model.Boolean.TRUE;

@Exclude
public class NamesResolver extends Visitor<SymbolTable> {
    protected List<ParserError> errors = new ArrayList<>();

    protected NamesResolver() {
    }

    public static List<ParserError> resolve(Module module) {
        NamesResolver resolver = new NamesResolver();
        resolver.visit(module, null);
        return resolver.errors;
    }

    @Override
    protected void visitModule(Module module, SymbolTable __) {
        super.visitModule(module, module.getDeclarations());
    }

    @Override
    protected void visitReference(Reference reference, SymbolTable declarations) {
        String name = reference.getName();
        Constant constant = checkBuiltinConstants(name);
        if (constant != null) {
            reference.setDeclaration(constant);
            return;
        }
        Declaration declaration = declarations.get(name);
        if (declaration == null) {
            errors.add(new ParserError(String.format("Undefined symbol '%s'", name)));
        }
        reference.setDeclaration(declaration);
        super.visitReference(reference, declarations);
    }

    protected Constant checkBuiltinConstants(String name) {
        switch (name) {
            case "TRUE":
                return new Constant(name, TRUE);
            case "FALSE":
                return new Constant(name, FALSE);
            default:
                return null;
        }
    }

    @Override
    protected void visitTypeReference(TypeReference reference, SymbolTable declarations) {
        String name = reference.getName();
        if (name.equals("INTEGER")) {
            reference.setReferencedType(PrimitiveType.INTEGER);
        } else if (name.equals("BOOLEAN")) {
            reference.setReferencedType(PrimitiveType.BOOLEAN);
        } else {
            Declaration declaration = declarations.get(name);
            if (!(declaration instanceof TypeDeclaration)) {
                errors.add(new ParserError(String.format("'%s' is not a type", name)));
            } else {
                reference.setReferencedType(declaration.getType());
            }
        }
    }
}
