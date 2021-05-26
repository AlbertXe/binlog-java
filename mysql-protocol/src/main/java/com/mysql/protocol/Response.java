package com.mysql.protocol;

public interface Response {
    void parse(byte[] bs);

    void parse(byte[] bs,Position pos);

}
