package com.yosoyvillaa.vutilities.connection;

public interface Connection {

    void connect();

    void close();

    java.sql.Connection get();
}
