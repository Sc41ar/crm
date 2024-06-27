package org.example.enumeration;

public enum Stage {
    OPEN("Open"),
    OFFER("Offer"),
    PREPARATION("Preparation of documents"),
    PREPAYMENT("Prepayment invoice"),
    WORK("At work"),
    INVOICE("Invoice"),
    WON("Won"),
    LOST("Lost");

    private String title;

    Stage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
