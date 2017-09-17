package yajco.oberon0.model;

public abstract class BinaryOperation extends Expression {
    private Expression left, right;

    protected BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
