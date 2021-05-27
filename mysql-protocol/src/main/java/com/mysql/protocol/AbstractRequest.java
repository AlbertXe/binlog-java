package com.mysql.protocol;

import com.mysql.protocol.tool.SafeByteArrayOutputStream;
import com.mysql.protocol.util.MysqlNumberUtil;
import lombok.Data;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 22:27
 */
@Data
public abstract class AbstractRequest implements Request {
    private int sequenceId;


    @Override
    public byte[] toBytes() {
        SafeByteArrayOutputStream os = new SafeByteArrayOutputStream(256);
        os.safeWrite(MysqlNumberUtil.writeInt2Byte(0, 4));
        fillPayload(os);

        byte[] bs = os.toByteArray();
        PacketHeader header = new PacketHeader();
        header.setPayloadLen(bs.length - 4);
        header.setSequenceId(sequenceId);

        //TODO
        System.arraycopy(header.toBytes(), 0, bs, 0, 4);
        return bs;
    }

    protected abstract void fillPayload(SafeByteArrayOutputStream os);
}
