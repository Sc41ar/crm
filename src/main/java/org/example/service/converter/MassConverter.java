package org.example.service.converter;

import java.math.BigDecimal;

/**
 * Конвертер для единиц массы
 */
public class MassConverter extends UnitConverter {
    public MassConverter() {
        super();
        conversionValue.put("мг", BigDecimal.valueOf(1000000.0));
        conversionValue.put("гр", BigDecimal.valueOf(1000.0));
        conversionValue.put("кг", BigDecimal.valueOf(1.0));
        conversionValue.put("т", BigDecimal.valueOf(0.001));
    }
}
