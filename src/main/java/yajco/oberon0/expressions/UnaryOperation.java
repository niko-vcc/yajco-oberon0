package yajco.oberon0.expressions;

public abstract class UnaryOperation extends Expression {
    private Expression operand;

    protected UnaryOperation(Expression operand) {
        this.operand = operand;
    }

    public Expression getOperand() {
        return operand;
    }
}
