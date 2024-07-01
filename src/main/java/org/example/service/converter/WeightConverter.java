package org.example.service.converter;

import java.math.BigDecimal;

public class WeightConverter extends UnitConverter {
    public WeightConverter() {
        super();
        conversionValue.put("мг", BigDecimal.valueOf(1000000.0));
        conversionValue.put("г", BigDecimal.valueOf(1000.0));
        conversionValue.put("кг", BigDecimal.valueOf(1.0));
        conversionValue.put("т", BigDecimal.valueOf(0.001));
    }
}
