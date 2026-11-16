package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitDAO {

    public record Unit(int id, String key, String name, String description) {
        @Override
        public String toString() {
            return name;
        }
    }

    // Returns units of a kind in the given language, falling back to English.
    public List<Unit> findByKind(String kind, String lang) throws SQLException {
        String sql = "SELECT u.id, u.unit_key, COALESCE(t.name, e.name), COALESCE(t.description, e.description) "
                + "FROM unit u "
                + "LEFT JOIN unit_translation t ON t.unit_id = u.id AND t.lang = ? "
                + "JOIN unit_translation e ON e.unit_id = u.id AND e.lang = 'en' "
                + "WHERE u.kind = ? ORDER BY u.id";
        List<Unit> units = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lang);
            ps.setString(2, kind);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(new Unit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
            }
        }
        return units;
    }
}
