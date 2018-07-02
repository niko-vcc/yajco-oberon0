package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.annotation.Token;
import yajco.oberon0.model.Reference;
import yajco.oberon0.model.Type;
import yajco.oberon0.model.Variable;

public class FieldSelector extends Reference {
    private final Reference base;
    private String fieldName;


    public FieldSelector(Reference base, @Before(".") @Token("name") String fieldName) {
        super(base.getName());
        this.base = base;
        this.fieldName = fieldName;
    }

    public Reference getBase() {
        return base;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Variable getField() {
        RecordType recordType = (RecordType) base.getType();
        return recordType.fields.get(getFieldName());
    }

    @Override
    public Type getType() {
        return getField().getType();
    }
}
