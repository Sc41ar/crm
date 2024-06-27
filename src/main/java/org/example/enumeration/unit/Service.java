package org.example.enumeration.unit;

public enum Service {
    NO_UNIT("-");
    private String title;

    Service(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
