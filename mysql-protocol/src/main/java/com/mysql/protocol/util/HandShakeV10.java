package com.mysql.protocol.util;

import com.mysql.protocol.AbstractResponse;
import com.mysql.protocol.CapabilityFlows;
import com.mysql.protocol.ErrPacket;
import com.mysql.protocol.Position;
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
        Position pos = Position.factory();
        if (PacketTool.isErrPacket(bs)) {
            ErrPacket errPacket = new ErrPacket();
            errPacket.parse(bs);
            throw new RuntimeException(errPacket.getErrMsg());
        }
        protocolVersion = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        serverVer = new String(MysqlNumberUtil.readNul(bs, pos));
        connectionId = MysqlNumberUtil.readByte2Int(bs, pos, 4);
        authPart1 = MysqlNumberUtil.readFixByte(bs, pos, 8);
        filler = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        capabilityLow = MysqlNumberUtil.readByte2Int(bs, pos, 2);
        if (bs.length == pos.getPos()) {
            return;
        }

        charset = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        status = MysqlNumberUtil.readByte2Int(bs, pos, 2);
        capabilityHigh = MysqlNumberUtil.readByte2Int(bs, pos, 2);
        capability = capabilityHigh << 16 | capabilityLow;

        int len = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        if (needAuth()) {
            authDataLen = len;
        }
        reserved = MysqlNumberUtil.readFixByte(bs, pos, 10);
        if (hasCapability(CapabilityFlows.CLIENT_SECURE_CONNECTION)) {
            len = Math.max(13, len);
            authPart2 = MysqlNumberUtil.readFixByte(bs, pos, len);
        }
        if (needAuth()) {
            if (bs[bs.length - 1] != 0) {
                authPluginName = new String(MysqlNumberUtil.readFixByte(bs, pos, bs.length - pos.getPos()));
            }else {
                authPluginName = new String(MysqlNumberUtil.readNul(bs, pos));
            }
        }

    }

    private boolean needAuth() {
        return hasCapability(CapabilityFlows.CLIENT_PLUGIN_AUTH);
    }

    private boolean hasCapability(int cap) {
        return (capability & cap) != 0;
    }

    public byte[] getAuthData() {
        if (hasCapability(CapabilityFlows.CLIENT_SECURE_CONNECTION)) {
            byte[] bs = new byte[20];
            System.arraycopy(authPart1, 0, bs, 0, 8);
            System.arraycopy(authPart2, 0, bs, 8, 12);
            return bs;
        }
        return authPart1;
    }
}
