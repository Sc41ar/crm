package org.example.enumeration.unit;

public enum Piece {
    PIECE("pcs");
    private String title;

    Piece(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
