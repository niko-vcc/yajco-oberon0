package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.visitor.Visitor;

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
        Entity entity = declarations.getEntity(reference.getName());
        reference.setEntity(entity);
    }

    @Override
    protected void visitAssignment(Assignment assignment, Declarations declarations) {
        Entity entity = declarations.getEntity(assignment.getName());
        assignment.setVariable((Variable) entity);
        super.visitAssignment(assignment, declarations);
    }
}
