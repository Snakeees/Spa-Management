import java.sql.*;
import java.util.logging.Logger;

public class Database {

    private final String url = "jdbc:mysql://localhost:3306/spa?useSSL=true";
    private final String username = "root";
    private final String password = "root";
    private Connection connection;

    public Database() {
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the database successfully.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.out.println("Could not disconnect from the database");
            }
        }
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
        }
    }
}
