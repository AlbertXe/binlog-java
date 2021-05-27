package com.mysql.protocol.util;

import com.mysql.protocol.Position;

import java.util.Arrays;

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

    public static byte[] readEOF(byte[] bs, Position pos, boolean checkSum) {
        int len = bs.length - pos.getPos();
        if (checkSum) {
            len -= 4;
        }
        return readFixByte(bs, pos, len);
    }

    public static byte[] readFixByte(byte[] bs, Position pos, int len) {
        int start = pos.getPos();
        pos.getAndForward(len);
        return Arrays.copyOfRange(bs, start, start + len);
    }

    public static byte[] readNul(byte[] bs, Position pos) {
        int end = -1;
        int start = pos.getPos();
        for (int i = start; i < bs.length; i++) {
            if (bs[i] == 0) {
                end = i;
                break;
            }
        }
        pos.getAndForward(end+1-start);
        return Arrays.copyOfRange(bs, start, end);
    }
}
