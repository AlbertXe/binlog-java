package com.mysql;

import lombok.Data;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:26
 */
@Data
public class TransportConfig {
    private int connectTimeout = 1500;
    private int soTimeout =1500;
    private int receiveBuffSize;
    private int sendBuffSize;
    private boolean keepAlive = true;

}
