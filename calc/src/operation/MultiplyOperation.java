package operation;

public class MultiplyOperation implements Operation {

    MultiplyOperation(){}

    @Override
    public char getOperand() {
        return '*';
    }

    @Override
    public double execute(double a, double b) {
        return a*b;
    }
}
