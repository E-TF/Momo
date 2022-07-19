package com.project.momo.common.lock;

import java.sql.Connection;

public class ThreadLocalConnectionHolder {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public static void save(Connection connection) {
        connectionHolder.set(connection);
    }

    public static Connection get() {
        return connectionHolder.get();
    }

    public static void clear() {
        connectionHolder.remove();
    }
}
