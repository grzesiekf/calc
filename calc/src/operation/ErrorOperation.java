package operation;

public class ErrorOperation implements Operation {
    @Override
    public char getOperand() {
        return 0;
    }

    @Override
    public double execute(double a, double b) {
        return Double.NaN;
    }
}
