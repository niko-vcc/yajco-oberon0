package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.Type;
import yajco.oberon0.model.Variable;
import yajco.oberon0.model.l4.*;

import java.util.List;

public class L4NamesResolver extends NamesResolver {

    public static List<ParserError> resolve(Module module) {
        NamesResolver resolver = new L4NamesResolver();
        resolver.visit(module, null);
        return resolver.errors;
    }

    @Override
    protected void visitAssignmentWithSelector(AssignmentWithSelector assignment, SymbolTable symbolTable) {
        super.visitAssignmentWithSelector(assignment, symbolTable);
        Type baseType = assignment.getVariable().getType().getRealType();
        List<Selector> selectors = assignment.getSelectors();
        resolveSelectors(baseType, selectors);
    }

    @Override
    protected void visitReferenceWithSelector(ReferenceWithSelector reference, SymbolTable symbolTable) {
        super.visitReferenceWithSelector(reference, symbolTable);
        Type baseType = reference.getDeclaration().getType().getRealType();
        List<Selector> selectors = reference.getSelectors();
        resolveSelectors(baseType, selectors);
    }

    private void resolveSelectors(Type baseType, List<Selector> selectors) {
        for (Selector selector : selectors) {
            if (selector instanceof FieldSelector) {
                baseType = resolveFieldSelector(baseType, (FieldSelector) selector);
                if (baseType == null)
                    return;
            }
            if (selector instanceof IndexSelector) {
                if (!(baseType instanceof ArrayType)) {
                    errors.add(new ParserError("Indexing non-array variable"));
                    return;
                }
                baseType = ((ArrayType) baseType).getElementType();
            }
        }
    }

    private Type resolveFieldSelector(Type baseType, FieldSelector fieldSelector) {
        if (!(baseType instanceof RecordType)) {
            errors.add(new ParserError("Trying to access field '%s' of variable that is not a record",
                    fieldSelector.getFieldName()));
            return null;
        }

        RecordType record = (RecordType) baseType;
        if (!record.getFields().containsKey(fieldSelector.getFieldName())) {
            errors.add(new ParserError("Field '%s' is not declared",
                    fieldSelector.getFieldName()));
            return null;
        }

        Variable field = record.getFields().get(fieldSelector.getFieldName());
        fieldSelector.setField(field);
        return field.getType().getRealType();
    }
}
