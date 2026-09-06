package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureConverterTest {

    private final TemperatureConverter converter = new TemperatureConverter();

    @Test
    void testFahrenheitToCelsius() {
        assertEquals(0, converter.fahrenheitToCelsius(32), 0.0001);
        assertEquals(100, converter.fahrenheitToCelsius(212), 0.0001);
        assertEquals(-40, converter.fahrenheitToCelsius(-40), 0.0001);
        assertEquals(37, converter.fahrenheitToCelsius(98.6), 0.0001);
    }

    @Test
    void testCelsiusToFahrenheit() {
        assertEquals(32, converter.celsiusToFahrenheit(0), 0.0001);
        assertEquals(212, converter.celsiusToFahrenheit(100), 0.0001);
        assertEquals(-40, converter.celsiusToFahrenheit(-40), 0.0001);
        assertEquals(98.6, converter.celsiusToFahrenheit(37), 0.0001);
    }

    @Test
    void testIsExtremeTemperature() {
        assertTrue(converter.isExtremeTemperature(-41));
        assertTrue(converter.isExtremeTemperature(51));
        assertFalse(converter.isExtremeTemperature(-40));
        assertFalse(converter.isExtremeTemperature(50));
        assertFalse(converter.isExtremeTemperature(0));
    }
}
