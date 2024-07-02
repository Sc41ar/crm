package org.example.service.converter;

import java.math.BigDecimal;

/**
 * Конвертер для единиц объема
 */
public class VolumeConverter extends UnitConverter {
    public VolumeConverter() {
        super();
        conversionValue.put("мл", BigDecimal.valueOf(1000000.0));
        conversionValue.put("л", BigDecimal.valueOf(1000.0));
        conversionValue.put("куб м", BigDecimal.valueOf(1.0));
        conversionValue.put("куб км", BigDecimal.valueOf(0.0000000001));
    }
}
