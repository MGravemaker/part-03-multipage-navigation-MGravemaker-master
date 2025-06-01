package org.adainf.javapro1uipart03;

import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;

/**
 * @author  Thomas Hopstaken
 * @version 2.4
 * @since   2018-09-15
 */
public class MySQLConnection {
    private Connection connection;

    private final Properties properties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException ex) {
            System.err.println("MySQL Driver not found ( More info: https://dev.mysql.com/downloads/connector/j/ )");
        }
    }

    /**
     * Default constructor for MySQLConnection
     */
    MySQLConnection() {
        properties.setProperty("hostname", "localhost");
        properties.setProperty("port", "3306");
        properties.setProperty("user", "");
        properties.setProperty("password", "");
        properties.setProperty("database", "");
    }

    /**
     * Constructor for MySQLConnection
     * @param username The username of the MySQL server
     * @param password The password of the MySQL server
     * @param database The database of the MySQL server
     */
    MySQLConnection(String username, String password, String database) {
        properties.setProperty("hostname", "localhost");
        properties.setProperty("port", "3306");
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        properties.setProperty("database", database);
    }

    /**
     * Constructor for MySQLConnection
     * @param hostname The hostname of the MySQL server
     * @param username The username of the MySQL server
     * @param password The password of the MySQL server
     * @param database The database of the MySQL server
     */
    MySQLConnection(String hostname, String username, String password, String database) {
        properties.setProperty("hostname", hostname);
        properties.setProperty("port", "3306");
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        properties.setProperty("database", database);
    }

    /**
     * Constructor for MySQLConnection
     * @param hostname The hostname of the MySQL server
     * @param port The port of the MySQL server
     * @param username The username of the MySQL server
     * @param password The password of the MySQL server
     * @param database The database of the MySQL server
     */
    MySQLConnection(String hostname, String port, String username, String password, String database) {
        properties.setProperty("hostname", hostname);
        properties.setProperty("port", port);
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        properties.setProperty("database", database);
    }

    /**
     * Method to connect to the MySQL database
     */
    private void connect() {
        String url = "jdbc:mysql://%s:%s/%s?allowMultiQueries=true".formatted(
                this.properties.getProperty("hostname"),
                this.properties.getProperty("port"),
                this.properties.getProperty("database")
        );

        try {
            this.connection = DriverManager.getConnection(url, this.properties);
        } catch(SQLException ex) {
            this.connection = null;
        }
    }

    /**
     * Method to disconnect the connection to the database
     */
    public void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            this.connection = null;
        }
        this.connection = null;
    }

    /**
     * Method to execute a query via a prepared statement
     * @param query The query to execute
     * @return The result of the query
     */
    public ResultSet preparedStatement(String query, ArrayList<Object> values) throws SQLException {
        if(this.isConnected()) { this.connect(); }

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        for(int i = 1; i <= values.size(); i++) {
            preparedStatement.setObject(i, values.get(i-1));
        }
        preparedStatement.execute();

        return preparedStatement.getResultSet();
    }

    /**
     * Method to execute a single query
     * @param query The query to execute
     * @return The result of the query
     */
    public ResultSet query(String query) throws SQLException {
        if(this.isConnected()) { this.connect(); }

        Statement statement = this.connection.createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Method to execute an update query
     * @param query The query to execute
     * @return The result of the query
     */
    public int updateQuery(String query) throws SQLException {
        if(this.isConnected()) { this.connect(); }

        Statement statement = this.connection.createStatement();

        return statement.executeUpdate(query);
    }

    /**
     * Method to check if the connection is still active
     * @return True if the connection is active, false if not
     */
    public boolean isConnected() {
        try {
            return this.connection == null || this.connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    /**
     * Method to get the connection
     * @return The connection
     */
    public java.sql.Connection getConnection() {
        return this.connection;
    }
}