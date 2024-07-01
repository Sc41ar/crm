package org.example.service.converter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UnitConverter {
    protected Map<String, BigDecimal> conversionValue;

    public UnitConverter() {
        conversionValue = new HashMap<>();
    }

    public BigDecimal convert(String fromUnit, String toUnit, BigDecimal value) {
        BigDecimal fromCoefficient = conversionValue.get(fromUnit);
        BigDecimal toCoefficient = conversionValue.get(toUnit);
        return value.multiply(fromCoefficient).divide(toCoefficient);
    }

    public boolean isOneTypeUnits(String fromUnit, String toUnit) {
        return (conversionValue.containsKey(fromUnit) && conversionValue.containsKey(toUnit));
    }

}
