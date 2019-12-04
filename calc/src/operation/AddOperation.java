package operation;

public class AddOperation implements Operation {

    AddOperation(){}

    @Override
    public char getOperand() {
        return '+';
    }

    @Override
    public double execute(double a, double b) {
        return a+b;
    }

}
