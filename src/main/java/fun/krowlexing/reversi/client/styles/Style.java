package fun.krowlexing.reversi.client.styles;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class Style implements Cloneable{
    public Paint paint;
    public float width;
    public float height;
    public float maxWidth;
    public float maxHeight;

    public Paint color = Paint.valueOf("#000000");


    public double borderTop = 0.0;
    public double borderBottom = 0.0;
    public double borderLeft = 0.0;
    public double borderRight = 0.0;

    public Paint borderColor = Paint.valueOf("#ffffff");

    public double gap;


    public double paddingTop;
    public double paddingBottom;
    public double paddingLeft;
    public double paddingRight;

    public static StyleBuilder create() {
        return new StyleBuilder();
    }

    public StyleBuilder edit() {
        return new StyleBuilder(this);
    }

    public StyleBuilder extend() {
        return new StyleBuilder(this.clone());
    }

    public Background background() {
        if (paint == null) return null;
        var backgroundFill = new BackgroundFill(paint, null, null);
        return new Background(backgroundFill);
    }

    public Border border() {
        var borderWidth = new BorderWidths(borderTop, borderBottom, borderLeft, borderRight);

        var strokeStyle = BorderStrokeStyle.SOLID;
        var stroke = new BorderStroke(borderColor, strokeStyle, null, borderWidth);
        return new Border(stroke);
    }

    public Insets padding() {
        return new Insets(paddingTop, paddingRight, paddingBottom, paddingLeft);
    }

    public Label apply(Label label) {
        return LabelStyler.apply(label, this);
    }

    public Control apply(Control control) {
        var background = this.background();
        if (background != null) {
            control.setBackground(background);
        }

        if (width != 0) {
            control.setMinWidth(width);
        }

        if (height != 0) {
            control.setMinHeight(height);
        }

        return control;
    }

    public static StyleBuilder style() {
        return new StyleBuilder();
    }

    public Pane apply(Pane parent) {
        var styler = new PaneStyler(this);
        return styler.apply(parent);
    }

    @Override
    public Style clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Style) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
