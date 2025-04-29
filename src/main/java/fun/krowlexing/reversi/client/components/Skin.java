package fun.krowlexing.reversi.client.components;

import javafx.scene.paint.Paint;

public class Skin {

    public final static Paint red = Paint.valueOf("#FFAB91");
    public final static Paint green = Paint.valueOf("#A5D6A7");
    public final static Paint blue = Paint.valueOf("#81D4FA");
    public final static Paint yellow = Paint.valueOf("#CE93D8");

    public final static Paint error = Paint.valueOf("#B22222");

    public Paint map(int color) {
        if (color == 0) return red;
        if (color == 1) return green;
        if (color == 2) return blue;
        if (color == 3) return yellow;
        return error;
    }
}
