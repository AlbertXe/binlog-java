package com.mysql.protocol.util;

import static com.mysql.protocol.CapabilityFlows.*;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 22:18
 */

import com.mysql.protocol.AbstractRequest;
import com.mysql.protocol.CapabilityFlows;
import com.mysql.protocol.tool.PassSecure;
import com.mysql.protocol.tool.SafeByteArrayOutputStream;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 握手协议请求<br>
 *
 * <a href= "http://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::HandshakeResponse" >
 * http://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet- Protocol::HandshakeResponse </a>
 *
 * @author hexiufeng
 */
@Data
public class HandShakeResponseV41 extends AbstractRequest {
    private static final int DEF_PACKET_SIZE = 1 << 24;
    private final HandShakeV10 v10;
    private int maxSendPacketSize = DEF_PACKET_SIZE;
    private int charset = 33; //utf8_general_ci
    private byte[] reserved = new byte[23];
    private String username;
    private String password;
    private String dbName;
    private Map<String, String> propMap = new LinkedHashMap<>();
    private int capability;

    {
        propMap.put("_runtime_version", System.getProperty("java.version"));
    }

    public HandShakeResponseV41(HandShakeV10 v10,int sequenceId, int size) {
        this.v10 = v10;
        this.maxSendPacketSize = Math.max(maxSendPacketSize,size);
        super.setSequenceId(sequenceId);
    }

    @SneakyThrows
    @Override
    protected void fillPayload(SafeByteArrayOutputStream os) {
        if (StringUtils.isNotBlank(dbName)) {
            capability |= CLIENT_CONNECT_WITH_DB;
        }
        capability |= CLIENT_FOUND_ROWS;
        capability |= CLIENT_LOCAL_FILES;
        capability |= CLIENT_LONG_PASSWORD;
        capability |= CLIENT_LONG_FLAG;
        capability |= CLIENT_PROTOCOL_41;
        capability |= CLIENT_TRANSACTIONS;
        capability |= CLIENT_SECURE_CONNECTION;
        capability |= CLIENT_CONNECT_ATTRS;

        os.safeWrite(MysqlNumberUtil.writeInt2Byte(capability, 4));
        os.safeWrite(MysqlNumberUtil.writeInt2Byte(maxSendPacketSize, 4));
        os.write(charset);
        os.safeWrite(reserved);
        os.safeWrite(username.getBytes("UTF-8"));
        os.write(0);
        if (StringUtils.isBlank(password)) {
            os.write(0x00);
        }else {
            // mysql server可能支持native password plugin方式，但客户端使用非插件方式
            // capabilities & CLIENT_SECURE_CONNECTION !=0 && capabilities & CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA=0
            // 参见http://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::HandshakeResponse
            byte[] bs = PassSecure.nativeSecure(password, v10.getAuthData());
        }
        os.safeWrite(MysqlNumberUtil.writeInt2Byte(capability, 4));
        os.safeWrite(MysqlNumberUtil.writeInt2Byte(capability, 4));

    }
}
