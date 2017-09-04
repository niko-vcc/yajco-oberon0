package yajco.oberon0.expressions;

public class BinaryOperation {
    private Expression left, right;

    public Expression getLeft() {
        return left;
    }

    protected void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    protected void setRight(Expression right) {
        this.right = right;
    }
}
