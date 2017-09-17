package yajco.oberon0.model;

public abstract class UnaryOperation extends Expression {
    private Expression operand;

    protected UnaryOperation(Expression operand) {
        this.operand = operand;
    }

    public Expression getOperand() {
        return operand;
    }
}
