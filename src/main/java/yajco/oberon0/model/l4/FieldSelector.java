package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.annotation.Token;
import yajco.oberon0.model.Variable;

public class FieldSelector implements Selector {
    private String fieldName;
    private Variable field;

    @Before(".")
    public FieldSelector(@Token("name") String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Variable getField() {
        return field;
    }

    public void setField(Variable field) {
        this.field = field;
    }
}
