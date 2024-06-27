package org.example.enumeration.unit;

public enum Time {
    MINUTE("min"),
    HOUR("hr"),
    DAY("d"),
    MONTH("mo"),
    YEAR("yr");

    private String title;

    Time(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
