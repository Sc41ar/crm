package org.example.enumeration.unit;

public enum Weight {
    MILLIGRAM("mg"),
    GRAM("g"),
    KILOGRAM("kg"),
    TON("t");
    private String title;

    Weight(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
