package com.mysql.protocol;

import lombok.Data;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:42
 */
@Data
public class Position {
    private int pos;

    private Position(int pos) {
        this.pos = pos;
    }

    public static Position factory() {
        return new Position(0);
    }
    public static Position factory(int pos) {
        return new Position(pos);
    }

    public int getAndForward() {
        return pos++;
    }

    public int getAndForward(int step) {
        int cur = pos;
        pos += step;
        return cur;
    }
}
