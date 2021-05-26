package com.mysql.protocol.util;

import com.mysql.TransportConfig;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 00:18
 */
public class SocketUtil {
    @SneakyThrows
    public static SocketHolder open(String host, int port, TransportConfig config) {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(host, port);
        socket.connect(address,config.getConnectTimeout());

        socket.setKeepAlive(config.isKeepAlive());
        socket.setSoTimeout(config.getSoTimeout());
        if (config.getReceiveBuffSize()>0)
            socket.setReceiveBufferSize(config.getReceiveBuffSize());
        if (config.getSendBuffSize()>0)
            socket.setSendBufferSize(config.getSendBuffSize());

        return new SocketHolder(socket, socket.getInputStream(), socket.getOutputStream());
    }

    public static void doHandShake(SocketHolder socketHolder) {
        HandShakeV10 v10 = new HandShakeV10();
    }
}
