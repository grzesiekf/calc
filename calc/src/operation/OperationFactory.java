package operation;

import plugin.PluginLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationFactory {

    private Map<Character, Operation> operandMap = new HashMap<>();

    public OperationFactory() {
        addOperation(new AddOperation());
        addOperation(new SubtractOperation());
        addOperation(new MultiplyOperation());
        addOperation(new DivideOperation());

        PluginLoader loader = new PluginLoader();
        List<Operation> operationsFromPlugins = loader.loadPlugins();
        operationsFromPlugins.forEach(this::addOperation);
    }

    private void addOperation(Operation operation) {
        operandMap.put(operation.getOperand(), operation);
        System.out.println("Added operation " + operation.getClass().getName());
    }


    public Operation createOperation(char operand) {
        if(operandMap.containsKey(operand)) {
            return operandMap.get(operand);
        }
        return new ErrorOperation();
    }
}
