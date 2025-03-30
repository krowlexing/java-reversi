package fun.krowlexing.reversi.server.services;

import fun.krowlexing.reversi.client.components.Skin;
import fun.krowlexing.reversi.client.data.Point;
import fun.krowlexing.reversi.client.data.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorService {
    private int[] colors;

    private Skin skin;
    private Size boardSize;

    public ColorService(Skin skin) {
        this.skin = skin;
    }

    public void init(Size boardSize) {
        this.boardSize = boardSize;
        int size = getCellCount(boardSize);

        var list = generateCells(size);
        colors = list.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getCellCount(Size boardSize) {
        if (boardSize.width * boardSize.height % 2 == 1) {
            return boardSize.width * boardSize.height - 1;
        } else {
            return boardSize.width * boardSize.height;
        }
    }



    public List<Integer> generateCells(int total) {
        var list = new ArrayList<Integer>(total);

        int added = 0;
        int color = 0;
        int maxColors = 4;
        while (added < total) {
            list.add(color);
            list.add(color);
            color = (color + 1) % maxColors;
            added += 2;
        }

        Collections.shuffle(list);
        return list;
    }

    public boolean isMatch(Point a, Point b) {
        return colors[cellIndex(a.x, a.y)] == colors[cellIndex(b.x, b.y)];
    }


    public int color(Point point) {
        int colorId = colors[cellIndex(
            point.x,
            point.y
        )];

        return colorId;
    }

    private boolean hasHole(Size size) {
        return size.height * size.width % 2 == 1;
    }

    public int cellIndex(int x, int y) {
        int idx = x + boardSize.width * y;
        if (hasHole(boardSize) && idx > (boardSize.width) * (boardSize.height) / 2) {
            return idx - 1;
        }

        return idx;
    }
}
