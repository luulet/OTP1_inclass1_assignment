package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static String url = "jdbc:h2:./tempdb";

    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS temperature_unit ("
                    + "id INT PRIMARY KEY, name VARCHAR(20) NOT NULL)");
            st.execute("CREATE TABLE IF NOT EXISTS temp_record ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "unit_id INT NOT NULL, "
                    + "input_value DOUBLE NOT NULL, "
                    + "celsius DOUBLE NOT NULL, "
                    + "FOREIGN KEY (unit_id) REFERENCES temperature_unit(id))");
            st.execute("MERGE INTO temperature_unit KEY(id) VALUES (1, 'Fahrenheit')");
            st.execute("MERGE INTO temperature_unit KEY(id) VALUES (2, 'Kelvin')");
        }
        return conn;
    }
}
