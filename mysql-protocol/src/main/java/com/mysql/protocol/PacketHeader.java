package com.mysql.protocol;

import com.mysql.protocol.util.MysqlNumberUtil;
import lombok.Data;

/**
 * 与mysql进行交互的数据包的头，msyql的由固定4个字节组成：
 * <ul>
 * <li>前三个字节是payloadLen</li>
 * <li>最后一个字节是sequenceId</li>
 * </ul>
 *
 * @author hexiufeng
 *
 */
@Data
public class PacketHeader extends AbstractResponse implements Response,Request  {
    private int payloadLen;
    private int sequenceId;



    @Override
    public byte[] toBytes() {
        byte[] bs = MysqlNumberUtil.writeInt2Byte(payloadLen, 4);
        bs[3] = (byte) sequenceId;
        return bs;

    }

    @Override
    public void parse(byte[] bs) {
        Position pos = Position.factory();
        payloadLen= MysqlNumberUtil.readByte2Int(bs, pos, 3);
        sequenceId = MysqlNumberUtil.readByte2Int(bs, pos, 1);
    }
}
