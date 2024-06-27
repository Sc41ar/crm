package org.example.enumeration;

public enum Type {
    GOOD("Sale of goods"),
    SERVICE("Sale of services");

    private String title;

    Type(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
