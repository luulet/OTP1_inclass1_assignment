package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {

    public void save(int fromUnitId, int toUnitId, double input, double result, String lang) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO conversion_record (from_unit_id, to_unit_id, input_value, result, lang) "
                             + "VALUES (?, ?, ?, ?, ?)")) {
            ps.setInt(1, fromUnitId);
            ps.setInt(2, toUnitId);
            ps.setDouble(3, input);
            ps.setDouble(4, result);
            ps.setString(5, lang);
            ps.executeUpdate();
        }
    }

    private static String name(String column) {
        return "COALESCE((SELECT name FROM unit_translation WHERE unit_id = r." + column + " AND lang = ?), "
                + "(SELECT name FROM unit_translation WHERE unit_id = r." + column + " AND lang = 'en'))";
    }

    // Last 5 conversions, unit names shown in the given language.
    public List<String> findLast5(String lang) throws SQLException {
        String sql = "SELECT r.input_value, " + name("from_unit_id") + ", r.result, " + name("to_unit_id")
                + " FROM conversion_record r ORDER BY r.id DESC LIMIT 5";
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lang);
            ps.setString(2, lang);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getDouble(1) + " " + rs.getString(2) + " = "
                            + rs.getDouble(3) + " " + rs.getString(4));
                }
            }
        }
        return list;
    }
}
