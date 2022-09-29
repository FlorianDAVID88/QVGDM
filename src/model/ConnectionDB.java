package model;

import java.sql.*;
public class ConnectionDB {
    Connection con;
    Statement stmt;
    ResultSet rs;

    public ConnectionDB(String user, String passwd, String database) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database,user,passwd);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SHOW DATABASES;");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        if(rs.getString(1).contains(database)) {
            System.out.println("Connected to the database " + database);
        } else {
            System.err.println("Impossible to connect to the database " + database);
        }
    }

    public Connection getConnection() {
        return this.con;
    }
}
