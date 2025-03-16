package fun.krowlexing.reversi.client.styles;

import javafx.scene.Parent;
import javafx.scene.control.Control;
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


    public double borderTop = 0.0;
    public double borderBottom = 0.0;
    public double borderLeft = 0.0;
    public double borderRight = 0.0;

    public Paint borderColor = Paint.valueOf("#ffffff");

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
