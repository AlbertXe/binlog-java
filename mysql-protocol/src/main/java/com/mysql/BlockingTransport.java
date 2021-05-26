package com.mysql;

import com.mysql.protocol.Request;

public interface BlockingTransport {
    void open();

    void close();

    byte[] read();

    void write(Request request);


}
