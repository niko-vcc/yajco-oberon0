package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Exclude;

public class Assignment extends Statement {
    private Reference reference;
    private Expression expression;

    public Assignment(
            Reference reference,
            @Before(":=") Expression expression) {
        this.reference = reference;
        this.expression = expression;
    }

    @Exclude
    public Assignment(
            Declaration variable,
            Expression expression) {
        this.reference = new Reference(variable);
        this.expression = expression;
    }

    public Reference getReference() {
        return reference;
    }

    public Declaration getVariable() {
        return reference.getDeclaration();
    }

    public Expression getExpression() {
        return expression;
    }
}
