package fun.krowlexing.reversi.client.components;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Field extends VBox {

    TextField textField;
    ErrorLabel error;

    Validator validator;

    boolean touched = false;

    public Field() {
        textField = new TextField();
        textField.setOnKeyTyped(this::onKeyTyped);
        textField.setOnMouseClicked(this::onClicked);
        error = new ErrorLabel();
        this.getChildren().addAll(
            textField,
            error
        );
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

    public Field validator(Validator validator) {
        this.validator = validator;
        return this;
    }
    public void setError(String errorText) {
        this.error.setText(errorText);
    }

    public static Builder field() {
        return new Builder();
    }

    public static class Builder {
        public Builder() {

        }

        public Field ok() {
            return new Field();
        }
    }

    public interface Validator {
        public String validate(String text);
    }
}
