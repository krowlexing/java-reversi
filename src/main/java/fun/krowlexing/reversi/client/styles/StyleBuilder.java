package fun.krowlexing.reversi.client.styles;

import javafx.scene.paint.Paint;

public class StyleBuilder {
    private Style style = new Style();
    public StyleBuilder() {

    }

    public StyleBuilder(Style style) {
        this.style = style;
    }

    public StyleBuilder width(int width) {
        style.width = width;
        return this;
    }

    public StyleBuilder maxWidth(int maxWidth) {
        style.maxWidth = maxWidth;
        return this;
    }
    public StyleBuilder setWidth(int width) {
        this.width(width);
        this.maxWidth(width);
        return this;
    }

    public StyleBuilder setHeight(int height) {
        this.height(height);
        this.maxHeight(height);
        return this;
    }

    public StyleBuilder height(int height) {
        style.height = height;
        return this;
    }

    public StyleBuilder maxHeight(int maxHeight) {
        style.maxHeight = maxHeight;
        return this;
    }

    public StyleBuilder background(String color) {
        var paint = Paint.valueOf(color);
        return this.background(paint);
    }

    public StyleBuilder background(Paint paint) {
        style.paint = paint;
        return this;
    }

    public StyleBuilder border(int borderWidth) {
        style.borderTop = borderWidth;
        style.borderRight = borderWidth;
        style.borderBottom = borderWidth;
        style.borderLeft = borderWidth;
        return this;
    }

    public StyleBuilder borderColor(String borderColor) {
        style.borderColor = Paint.valueOf(borderColor);
        return this;
    }

    public Style build() {
        return style;
    }


}
