package com.mysql.protocol.util;

import com.mysql.protocol.AbstractResponse;
import lombok.Data;

/**
 * Initial Handshake Packet。<br>
 *
 * <a href="http://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::Handshake"> http://dev.
 * mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::Handshake </a>
 *
 * @author hexiufeng
 *
 */
@Data
public class HandShakeV10 extends AbstractResponse {
    private int protocolVersion;
    private String serverVer;
    private long connectionId;
    private byte[] authPart1;
    private int filler;
    private int capabilityLow;

    private int charset;
    private int status;

    private int capabilityHigh;

    private int capability;
    private int authDataLen;

    private byte[] reserved;
    private byte[] authPart2;

    private String authPluginName;

    @Override
    public void parse(byte[] bs) {
        //TODO
    }
}
