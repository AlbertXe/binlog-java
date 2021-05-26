package com.mysql;

import com.mysql.protocol.Request;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:28
 */
public class TextProtocolTransportImpl extends AbstractTransport implements BlockingTransport {
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public byte[] read() {
        return new byte[0];
    }

    @Override
    public void write(Request request) {

    }
}
