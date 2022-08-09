package com.project.momo.common.lock;

import java.sql.Connection;
import java.util.Stack;

public class ThreadLocalConnectionHolder {
    private static final ThreadLocal<Stack<Connection>> connectionHolder = new ThreadLocal<>();

    public static void save(Connection connection) {
        Stack<Connection> stack = connectionHolder.get();
        if (stack == null) {
            stack = createEmptyStack();
        }
        stack.push(connection);
        connectionHolder.set(stack);
    }

    public static Connection get() {
        Stack<Connection> stack = connectionHolder.get();
        Connection con = stack.pop();
        connectionHolder.set(stack);

        return con;
    }

    public static void clear() {
        Stack<Connection> stack = connectionHolder.get();
        if (stack.isEmpty())
            connectionHolder.remove();
    }

    private static Stack<Connection> createEmptyStack() {
        return new Stack<>();
    }
}
