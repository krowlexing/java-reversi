package fun.krowlexing.reversi.client.styles;

import javafx.geometry.Pos;
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

    public StyleBuilder borderRadius(int borderRadius) {
        style.borderRadius = borderRadius;
        return this;
    }

    public StyleBuilder borderColor(String borderColor) {
        style.borderColor = Paint.valueOf(borderColor);
        return this;
    }



    public StyleBuilder color(String color) {
        style.color = Paint.valueOf(color);
        return this;
    }

    public StyleBuilder fontSize(double size) {
        style.fontSize = size;
        return this;
    }

    public StyleBuilder padding(double padding) {
        style.paddingTop = padding;
        style.paddingBottom = padding;
        style.paddingLeft = padding;
        style.paddingRight = padding;
        return this;
    }

    public StyleBuilder align(Pos pos) {
        style.align = pos;
        return this;
    }

    public StyleBuilder gap(double gap) {
        style.gap = gap;
        return this;
    }

    public Style build() {
        return style;
    }


}
