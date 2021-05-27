package com.mysql.protocol.util;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 20:45
 */
public class PacketTool {

    public static boolean isErrPacket(byte[] bs) {
        return (bs[0] & 0xff) == 0;
    }
}
