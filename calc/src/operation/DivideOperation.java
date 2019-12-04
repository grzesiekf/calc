package operation;

public class DivideOperation implements Operation {

    DivideOperation(){}

    @Override
    public char getOperand() {
        return '/';
    }

    @Override
    public double execute(double a, double b) {
        return a/b;
    }
}
