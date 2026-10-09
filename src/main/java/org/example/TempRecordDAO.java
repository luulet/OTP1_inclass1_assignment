package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {

    public void save(int unitId, double inputValue, double celsius) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO temp_record (unit_id, input_value, celsius) VALUES (?, ?, ?)")) {
            ps.setInt(1, unitId);
            ps.setDouble(2, inputValue);
            ps.setDouble(3, celsius);
            ps.executeUpdate();
        }
    }

    public List<String> findAll() throws SQLException {
        List<String> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT u.name, r.input_value, r.celsius FROM temp_record r "
                             + "JOIN temperature_unit u ON r.unit_id = u.id ORDER BY r.id")) {
            while (rs.next()) {
                result.add(rs.getDouble(2) + " " + rs.getString(1) + " = " + rs.getDouble(3) + " C");
            }
        }
        return result;
    }
}
