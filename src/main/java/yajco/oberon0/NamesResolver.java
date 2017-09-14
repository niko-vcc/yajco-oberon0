package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.expressions.Reference;
import yajco.oberon0.visitor.Visitor;

@Exclude
public class NamesResolver extends Visitor<Declarations> {
    private NamesResolver() {
    }

    public static void resolve(Module module) {
        new NamesResolver().visit(module, null);
    }

    @Override
    protected void visitModule(Module module, Declarations __) {
        super.visitModule(module, module.getDeclarations());
    }

    @Override
    protected void visitReference(Reference reference, Declarations declarations) {
        Declaration declaration = declarations.getDeclaration(reference.getName());
        reference.setVariable((Memory) declaration);
    }

    @Override
    protected void visitAssignment(Assignment assignment, Declarations declarations) {
        Declaration declaration = declarations.getDeclaration(assignment.getName());
        assignment.setVariable((Variable) declaration);
        super.visitAssignment(assignment, declarations);
    }
}
