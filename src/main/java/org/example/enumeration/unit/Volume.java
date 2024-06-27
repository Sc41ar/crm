package org.example.enumeration.unit;

public enum Volume {
    MILLILITER("ml"),
    LITER("l"),
    CUBIC_METER("cu m"),
    CUBIC_KM("cu km");

    private String title;

    Volume(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
