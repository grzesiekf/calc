package operation;

public class SubtractOperation implements Operation {

    SubtractOperation(){}

    @Override
    public char getOperand() {
        return '-';
    }

    @Override
    public double execute(double a, double b) {
        return a-b;
    }
}
