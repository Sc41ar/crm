package org.example.enumeration.unit;

public enum Length {
    CENTIMETER("cm"),
    METER("m"),
    KILOMETER("km");

    private String title;

    Length(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
