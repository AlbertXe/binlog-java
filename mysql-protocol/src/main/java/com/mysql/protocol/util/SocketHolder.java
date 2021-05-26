package com.mysql.protocol.util;

import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:36
 */
@Data
public class SocketHolder {
    final Socket socket;
    final InputStream is;
    final OutputStream os;
    long connectionId;

    public SocketHolder(Socket socket, InputStream is, OutputStream os) {
        this.socket = socket;
        this.is = is;
        this.os = os;
    }
}
