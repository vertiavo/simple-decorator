package com.vertiavo;

import java.awt.Graphics2D;

public interface Piece {

    void draw(Graphics2D g);

    int getX();

    int getY();

    void moveTo(int xx, int yy);

    Piece reverseDecoration();

}
