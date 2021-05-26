package com.mysql.protocol.util;

import com.mysql.protocol.Position;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:53
 */
public class MysqlNumberUtil {

    public static int readByte2Int(byte[] bs, Position pos, int len) {
        int start = pos.getPos();
        int value = 0;
        for (int i = start; i < (start+len); i++) {
            value |= bs[i] << 8 * (i - start);
        }
        pos.getAndForward(len);
        return value;
    }

    public static byte[] writeInt2Byte(int value, int len) {
        byte[] bs = new byte[len];
        for (int i = 0; i < len; i++) {
            bs[i] = (byte) (value >> (8 * i));
        }
        return bs;
    }
}
