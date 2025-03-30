package fun.krowlexing.reversi.client.components;

import java.io.IOException;

public interface OnCellClick {
    void onClick(int x, int y) throws IOException;
}
