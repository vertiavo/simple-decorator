package com.vertiavo;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;

public class PieceImpl implements Piece {

    public static final int TILESIZE = 32;
    private static Image image = new ImageIcon("src/main/resources/pieces4.png").getImage();

    private int index;
    private int x;
    private int y;

    public PieceImpl(int idx, int xx, int yy) {
        index = idx;
        x=xx;
        y=yy;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, x+1, y+1,
                index*TILESIZE, 0, (index+1)*TILESIZE, TILESIZE, null);
    }

    public int getX() { return x; }

    public int getY() {return y; }

    public void moveTo(int xx, int yy) { x=xx; y=yy; }

    @Override
    public Piece reverseDecoration() {
        return null;
    }
}
