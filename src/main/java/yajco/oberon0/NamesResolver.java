package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

import static yajco.oberon0.model.Boolean.FALSE;
import static yajco.oberon0.model.Boolean.TRUE;

@Exclude
public class NamesResolver extends Visitor<Declarations> {
    private List<ParserError> errors = new ArrayList<>();

    private NamesResolver() {
    }

    public static List<ParserError> resolve(Module module) {
        NamesResolver resolver = new NamesResolver();
        resolver.visit(module, null);
        return resolver.errors;
    }

    @Override
    protected void visitModule(Module module, Declarations __) {
        super.visitModule(module, module.getDeclarations());
    }

    @Override
    protected void visitReference(Reference reference, Declarations declarations) {
        String name = reference.getName();
        Constant constant = checkBuiltinConstants(name);
        if (constant != null) {
            reference.setDeclaration(constant);
            return;
        }
        Declaration declaration = declarations.getDeclaration(name);
        if (declaration == null) {
            errors.add(new ParserError(String.format("Undefined symbol '%s'", name)));
        }
        reference.setDeclaration(declaration);
    }

    private Constant checkBuiltinConstants(String name) {
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
    protected void visitAssignment(Assignment assignment, Declarations declarations) {
        String name = assignment.getName();
        Declaration declaration = declarations.getDeclaration(name);
        if (declaration == null) {
            errors.add(new ParserError(String.format("Undefined symbol '%s'", name)));
        } else if (!(declaration instanceof Variable)) {
            errors.add(new ParserError(String.format("Assignment to nonvariable '%s'", name)));
        } else {
            assignment.setVariable((Variable) declaration);
        }
        super.visitAssignment(assignment, declarations);
    }

    @Override
    protected void visitTypeReference(TypeReference reference, Declarations declarations) {
        String name = reference.getName();
        if (name.equals("INTEGER")) {
            reference.setReferencedType(PrimitiveType.INTEGER);
        } else if (name.equals("BOOLEAN")) {
            reference.setReferencedType(PrimitiveType.BOOLEAN);
        } else {
            Declaration declaration = declarations.getDeclaration(name);
            if (!(declaration instanceof TypeDeclaration)) {
                errors.add(new ParserError(String.format("'%s' is not a type", name)));
            } else {
                reference.setReferencedType(declaration.getType());
            }
        }
    }
}
