package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TempRecordDAOTest {

    @Test
    void savedRecordIsFoundWithUnitName() throws Exception {
        DBConnection.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        TempRecordDAO dao = new TempRecordDAO();
        dao.save(2, 300, 26.85);
        List<String> all = dao.findAll();
        assertEquals(1, all.size());
        assertEquals("300.0 Kelvin = 26.85 C", all.get(0));
    }
}
