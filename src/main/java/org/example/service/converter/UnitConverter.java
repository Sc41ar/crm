package org.example.service.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс ковертера
 */
public class UnitConverter {
    /**
     * Мапа единица измерения - значение конвертации
     */
    protected Map<String, BigDecimal> conversionValue;

    public UnitConverter() {
        conversionValue = new HashMap<>();
    }

    /**
     * Перевод значения из одной единицы измерения в другую
     *
     * @param fromUnit единица измерения, из которой переводим
     * @param toUnit   единица измерения, в которую переводим
     * @param value    переводимое значение
     * @return значение после конвертации
     */
    public BigDecimal convert(String fromUnit, String toUnit, BigDecimal value) {
        BigDecimal fromCoefficient = conversionValue.get(fromUnit);
        BigDecimal toCoefficient = conversionValue.get(toUnit);
        return value.multiply(fromCoefficient).divide(toCoefficient, 4, RoundingMode.HALF_EVEN);
    }

    /**
     * Проверка, принадлежат ли единицы измерения данному конвертеру
     *
     * @param fromUnit единица измерения
     * @param toUnit   единица измерения
     * @return true - обе единицы принадлежат конвертеру, иначе - false
     */
    public boolean isOneTypeUnits(String fromUnit, String toUnit) {
        return (conversionValue.containsKey(fromUnit) && conversionValue.containsKey(toUnit));
    }

}
