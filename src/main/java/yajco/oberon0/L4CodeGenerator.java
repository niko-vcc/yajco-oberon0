package yajco.oberon0;

import yajco.oberon0.model.*;
import yajco.oberon0.model.l4.ArrayType;
import yajco.oberon0.model.l4.FieldSelector;
import yajco.oberon0.model.l4.IndexSelector;
import yajco.oberon0.model.l4.RecordType;

import java.io.PrintWriter;

public class L4CodeGenerator extends L3CodeGenerator {
    public static void generate(Module module, PrintWriter writer) {
        new L4CodeGenerator().visit(module, writer);
    }

    @Override
    protected void visitArrayType(ArrayType arrayType, PrintWriter writer) {
        visit(arrayType.getElementType(), writer);
        // Array size is a special case, it would be printed after variable name
    }


    @Override
    protected void visitVariable(Variable variable, PrintWriter writer) {
        visit(variable.getType(), writer);
        writer.printf(String.format(" %s", variable.getName()));
        if (variable.getType() instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) variable.getType();
            writer.print("[");
            visit(arrayType.getSize(), writer);
            writer.print("]");
        }
        writer.print(";\n");
    }

    @Override
    protected void visitRecordType(RecordType recordType, PrintWriter writer) {
        writer.print("struct {\n");
        for (Variable field: recordType.getFields().values()) {
            visit(field, writer);
            writer.println();
        }
        writer.print("}");
    }

    @Override
    protected void visitReference(Reference reference, PrintWriter writer) {
        if (reference instanceof IndexSelector) {
            visitIndexSelector((IndexSelector) reference, writer);
        } else if (reference instanceof FieldSelector) {
            visitFieldSelector((FieldSelector) reference, writer);
        } else {
            super.visitReference(reference, writer);
        }
    }

    @Override
    protected void visitIndexSelector(IndexSelector indexSelector, PrintWriter writer) {
        visit(indexSelector.getBase(), writer);
        writer.print("[");
        visit(indexSelector.getIndex(), writer);
        writer.print(" - 1]");
    }

    @Override
    protected void visitFieldSelector(FieldSelector fieldSelector, PrintWriter writer) {
        visit(fieldSelector.getBase(), writer);
        writer.printf(".%s", fieldSelector.getField().getName());
    }
}
