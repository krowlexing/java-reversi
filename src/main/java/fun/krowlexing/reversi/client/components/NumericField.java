package fun.krowlexing.reversi.client.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.logger.Logger.print;

public class NumericField extends VBox {

    TextField textField;
    ErrorLabel error;

    Validator validator;

    boolean touched = false;

    public NumericField() {
        textField = new TextField();
        textField.getStyleClass().add("form-field");
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change : null;
        }));
        this.setMaxWidth(200);

        textField.setOnKeyReleased(this::onKeyTyped);
        textField.setOnMouseClicked(this::onClicked);
        error = new ErrorLabel();
        this.getChildren().addAll(textField, error);
    }

    void onKeyTyped(KeyEvent e) {
        touched = true;
        validate();
    }

    void onClicked(MouseEvent e) {
        touched = true;
        validate();
    }

    public void validate() {
        var text = textField.getText();
        if (this.validator != null) {
            var error = this.validator.validate(text);
            if (error != null) {
                setError(error);
            } else {
                setError("");
            }
        }
    }

    public void touch() {
        this.touched = true;
        validate();
    }

    public String text() {
        return this.textField.getText();
    }

    public NumericField validator(Validator validator) {
        this.validator = validator;
        return this;
    }

    public boolean isValid() {
        if (this.validator == null) return true;
        this.validate();
        return this.error.getText().equals("");

    }

    public void setError(String errorText) {
        this.error.setText(errorText);
    }

    public NumericField promptText(String promptText) {
        this.textField.setPromptText(promptText);
        return this;
    }

    public static Builder field() {
        return new Builder();
    }

    public static class Builder {
        public NumericField ok() {
            return new NumericField();
        }
    }

    public interface Validator {
        String validate(String text);
    }
}
