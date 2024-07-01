package org.example.service.converter;

import java.math.BigDecimal;

public class TimeConverter extends UnitConverter {
    public TimeConverter() {
        super();
        conversionValue.put("мин", BigDecimal.valueOf(525600.0));
        conversionValue.put("ч", BigDecimal.valueOf(8760.0));
        conversionValue.put("д", BigDecimal.valueOf(365.0));
        conversionValue.put("мес", BigDecimal.valueOf(12.0));
        conversionValue.put("г", BigDecimal.valueOf(1.0));
    }
}
