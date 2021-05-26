package com.mysql;

import com.mysql.protocol.PacketHeader;
import com.mysql.protocol.util.SocketHolder;
import com.mysql.protocol.util.SocketUtil;
import lombok.Data;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:30
 */
@Data
public abstract class AbstractTransport implements BlockingTransport {
    private String host;
    private int port;
    private String username;
    private String password;
    private String dbName;

    private TransportConfig transportConfig = new TransportConfig();
    private int maxPacketSize = 0;

    protected volatile SocketHolder socketHolder;
    private volatile boolean isOpened =false;
    private volatile boolean isOpeningPhase = false;

    private final PacketHeader packetHeader = new PacketHeader();

    public AbstractTransport(String host, int port, String username, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    protected abstract void afterOpen();

    protected abstract void initTranport();

    @Override
    public void open() {
        SocketHolder socketHolder = SocketUtil.open(host, port, transportConfig);
        SocketUtil.doHandShake(socketHolder);
    }
}
