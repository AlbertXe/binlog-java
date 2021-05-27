package com.mysql.protocol.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 继承{@link ByteArrayOutputStream}，输出数据时不抛出异常。
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 22:29
 */
public class SafeByteArrayOutputStream extends ByteArrayOutputStream {
    public SafeByteArrayOutputStream(int size) {
        super(size);
    }

    public void safeWrite(byte[] bs) {
        try {
            super.write(bs);
        } catch (IOException e) {
        }
    }

    public void setValue(int pos, byte value) {
        super.buf[pos] = value;
    }
}
