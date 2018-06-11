package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.annotation.Token;

public class FieldSelector implements Selector {
    private String fieldName;

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
}
