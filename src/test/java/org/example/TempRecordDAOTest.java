package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TempRecordDAOTest {

    @BeforeAll
    static void setUp() {
        DBConnection.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    }

    @Test
    void unitNamesAreLocalized() throws Exception {
        List<UnitDAO.Unit> units = new UnitDAO().findByKind("temperature", "ja");
        assertEquals(3, units.size());
        assertEquals("ケルビン", units.get(2).name());
    }

    @Test
    void unknownLanguageFallsBackToEnglish() throws Exception {
        List<UnitDAO.Unit> units = new UnitDAO().findByKind("temperature", "xx");
        assertEquals("Kelvin", units.get(2).name());
    }

    @Test
    void savedRecordIsFoundInSelectedLanguage() throws Exception {
        TempRecordDAO dao = new TempRecordDAO();
        dao.save(1, 3, 26.85, 300.0, "en");
        assertEquals("26.85 Celsius = 300.0 Kelvin", dao.findLast5("en").get(0));
        assertEquals("26.85 Celsius = 300.0 Kelvin", dao.findLast5("xx").get(0));
    }

    @Test
    void onlyLastFiveAreReturned() throws Exception {
        TempRecordDAO dao = new TempRecordDAO();
        for (int i = 0; i < 7; i++) {
            dao.save(1, 2, i, i, "en");
        }
        assertEquals(5, dao.findLast5("en").size());
    }
}
