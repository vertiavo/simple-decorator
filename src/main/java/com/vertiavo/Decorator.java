package com.vertiavo;

import java.awt.Graphics2D;

public abstract class Decorator implements Piece {

    protected Piece piece;

    public Decorator(Piece piece) {
        this.piece = piece;
    }

    @Override
    public void draw(Graphics2D g) {
        this.piece.draw(g);
    }

    @Override
    public int getX() {
        return this.piece.getX();
    }

    @Override
    public int getY() {
        return this.piece.getY();
    }

    @Override
    public void moveTo(int xx, int yy) {
        this.piece.moveTo(xx, yy);
    }

    @Override
    public Piece reverseDecoration() {
        return this.piece;
    }
}
