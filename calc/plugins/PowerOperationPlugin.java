import operation.Operation;

public class PowerOperationPlugin implements Operation {

    @Override
    public char getOperand() {
        return '^';
    }

    @Override
    public double execute(double a, double b) {
        return Math.pow(a,b);
    }
}
