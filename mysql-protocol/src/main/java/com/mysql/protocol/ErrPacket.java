package com.mysql.protocol;

import com.mysql.protocol.util.MysqlNumberUtil;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 20:47
 */

/**
 * 错误数据包。<br>
 *
 * see
 * <a href="http://dev.mysql.com/doc/internals/en/packet-ERR_Packet.html" > http://dev.mysql.com/doc/internals/en/packet
 * -ERR_Packet.html </a>
 *
 * @author hexiufeng
 *
 */
@Getter
public class ErrPacket extends AbstractResponse {
    /**
     * 0xff
     */
    private int header;
    private int errCode;
    private String errMsg;


    @SneakyThrows
    @Override
    public void parse(byte[] bs) {
        Position pos = Position.factory();
        header = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        errCode = MysqlNumberUtil.readByte2Int(bs, pos, 2);
        errMsg = new String(MysqlNumberUtil.readEOF(bs, pos, super.isCheckSum()), "UTF-8");

    }
}
