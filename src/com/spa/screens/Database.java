package com.spa.screens;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {

    private static final AtomicInteger connectionCount = new AtomicInteger(0);
    private final String url = "jdbc:mysql://localhost:3306/spa?useSSL=true";
    private final String username = "root";
    private final String password = "root";
    private Connection connection;
    private int connectionId;

    public Database() {
        this.connectionId = connectionCount.incrementAndGet();
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the database successfully. Connection ID: " + this.connectionId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnect() {
        new Thread(() -> {
            try {
                Thread.sleep(60000); //60000ms or 1min
                if (connection != null) {
                    connection.close();
                    System.out.println("Disconnected from the database. Connection ID: " + this.connectionId);
                }
            } catch (InterruptedException e) {
                System.out.println("Disconnection thread was interrupted. Connection ID: " + this.connectionId);
            } catch (SQLException e) {
                System.out.println("Could not disconnect from the database. Connection ID: " + this.connectionId);
            }
        }).start();
    }

    public ResultSet executeQuery(String query, Object... params) {
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public int executeUpdate(String updateQuery, Object... params) {
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }
}
