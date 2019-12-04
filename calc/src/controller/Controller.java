package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import operation.Operation;
import operation.OperationFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private OperationFactory operationFactory = new OperationFactory();

    @FXML
    private TextField inputView;

    @FXML
    private TextField resultView;

    @FXML
    private void calculateClick() {
        String input = inputView.getText();

        String regex = "(\\d+(?:\\.\\d+)?)([^0-9\\.])(\\d+(?:\\.\\d+)?)";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(input);

        if (m.matches()) {
            try {
                double a = Double.parseDouble(m.group(1));
                char operand = m.group(2).charAt(0);
                double b = Double.parseDouble(m.group(3));

                Operation operation = operationFactory.createOperation(operand);
                double result = operation.execute(a, b);

                resultView.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                resultView.setText("NaN");
            }
        } else {
            resultView.setText("NaN");
        }
    }
}
