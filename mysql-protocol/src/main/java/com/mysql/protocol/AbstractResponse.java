package com.mysql.protocol;


import lombok.Data;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-26 23:40
 */
@Data
public abstract class AbstractResponse implements Response {
    private boolean checkSum;

    @Override
    public void parse(byte[] bs, Position pos) {
        throw new RuntimeException();
    }
}
