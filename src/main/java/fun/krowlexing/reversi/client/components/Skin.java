package fun.krowlexing.reversi.client.components;

import javafx.scene.paint.Paint;

public class Skin {

    public final static Paint red = Paint.valueOf("#DC143C");
    public final static Paint green = Paint.valueOf("#228B22");
    public final static Paint blue = Paint.valueOf("#4169E1");
    public final static Paint yellow = Paint.valueOf("#B8860B");

    public final static Paint error = Paint.valueOf("#B22222");

    public Paint map(int color) {
        if (color == 0) return red;
        if (color == 1) return green;
        if (color == 2) return blue;
        if (color == 3) return yellow;
        return error;
    }
}
