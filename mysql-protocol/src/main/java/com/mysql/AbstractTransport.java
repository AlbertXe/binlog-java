package com.mysql;

import com.mysql.protocol.PacketHeader;
import com.mysql.protocol.Request;
import com.mysql.protocol.util.SocketHolder;
import com.mysql.protocol.util.SocketUtil;
import lombok.Data;
import lombok.SneakyThrows;

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

    private final PacketHeader header = new PacketHeader();

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
        isOpeningPhase = true;
        SocketUtil.doHandShake(socketHolder,this);

    }

    @Override
    public void close() {

    }

    @Override
    public byte[] read() {
        checkOpen();
        byte[] bs  = readBytes(4);
        // 初始化header
        header.parse(bs);
        return readBytes(header.getPayloadLen());
    }

    @SneakyThrows
    private byte[] readBytes(int len){
        byte[] bs = new byte[len];
        int count = 0;
        while (count < len) {
            socketHolder.getIs().read(bs, count, len);
            count += len;
        }
        return bs;
    }

    protected  void checkOpen(){
        if (!isOpened && !isOpeningPhase) {
            throw new RuntimeException();
        }
    }

    @Override
    public void write(Request request) {

    }
}
