package org.example.service.converter;

import java.math.BigDecimal;

/**
 * Конвертер для единиц длины
 */
public class LengthConverter extends UnitConverter {
    public LengthConverter() {
        super();
        conversionValue.put("м", BigDecimal.valueOf(1.0));
        conversionValue.put("см", BigDecimal.valueOf(100.0));
        conversionValue.put("км", BigDecimal.valueOf(0.001));
    }
}
