package com.mysql.protocol;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 21:26
 */
public interface CapabilityFlows {
    int CLIENT_LONG_PASSWORD = 0x00000001;
    int CLIENT_PLUGIN_AUTH = 0x00080000;
    int CLIENT_SECURE_CONNECTION = 0x00008000;
    int CLIENT_PROTOCOL_41 = 0x00000200;
    int CLIENT_CONNECT_WITH_DB = 0x00000008;
    int CLIENT_CONNECT_ATTRS = 0x00100000;
    int CLIENT_TRANSACTIONS = 0x00002000;
    int CLIENT_LONG_FLAG = 0x00000004;
    int CLIENT_MULTI_RESULTS = 0x00020000;
    int CLIENT_FOUND_ROWS = 0x00000002;
    int CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA = 0x00200000;
    int CLIENT_LOCAL_FILES = 0x00000080;
    int CLIENT_INTERACTIVE = 0x00000400;
    int CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS = 0x00400000;

}
