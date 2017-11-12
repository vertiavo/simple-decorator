package com.vertiavo;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

public class Chessboard extends JPanel {

    public static final int ZEROX = 23;
    public static final int ZEROY = 7;

    private HashMap<Point, Piece> board = new HashMap<>();

    private Image image;
    private Piece dragged = null;
    private Point mouse = null;

    Chessboard() {

        AffineTransform drawTransform = new AffineTransform();
        drawTransform.translate(ZEROX, ZEROY);
        drawTransform.scale(PieceImpl.TILESIZE, PieceImpl.TILESIZE);

        board.put(new Point(0, 2), new TransformDecorator(new PieceImpl(11, 0, 2), drawTransform));
        board.put(new Point(0, 6), new TransformDecorator(new PieceImpl(0, 0, 6), drawTransform));
        board.put(new Point(1, 4), new TransformDecorator(new PieceImpl(6, 1, 4), drawTransform));
        board.put(new Point(1, 5), new TransformDecorator(new PieceImpl(5, 1, 5), drawTransform));
        board.put(new Point(3, 7), new TransformDecorator(new PieceImpl(1, 3, 7), drawTransform));
        board.put(new Point(4, 3), new TransformDecorator(new PieceImpl(6, 4, 3), drawTransform));
        board.put(new Point(4, 4), new TransformDecorator(new PieceImpl(7, 4, 4), drawTransform));
        board.put(new Point(5, 4), new TransformDecorator(new PieceImpl(6, 5, 4), drawTransform));
        board.put(new Point(5, 6), new TransformDecorator(new PieceImpl(0, 5, 6), drawTransform));
        board.put(new Point(6, 5), new TransformDecorator(new PieceImpl(0, 6, 5), drawTransform));
        board.put(new Point(7, 4), new TransformDecorator(new PieceImpl(0, 7, 4), drawTransform));


        image = new ImageIcon("src/main/resources/board3.png").getImage();
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent ev) {
                dragged = take((ev.getX() - ZEROX) / PieceImpl.TILESIZE, (ev.getY() - ZEROY) / PieceImpl.TILESIZE);
                mouse = ev.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent ev) {
                drop(dragged, (ev.getX() - ZEROX) / PieceImpl.TILESIZE, (ev.getY() - ZEROY) / PieceImpl.TILESIZE);
                dragged = null;
                undo.setEnabled(true);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent ev) {
                mouse = ev.getPoint();
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
        for (Map.Entry<Point, Piece> e : board.entrySet()) {
            Point pt = e.getKey();
            Piece pc = e.getValue();
            pc.draw((Graphics2D) g);
        }
        if (mouse != null && dragged != null) {
            dragged.draw((Graphics2D) g);
        }
    }

    public void drop(Piece p, int x, int y) {
        repaint();
        p.moveTo(x, y);
        board.put(new Point(x, y), p); // jeśli na tych współrzędnych
        //jest już jakaś figura, znika ona z planszy
        //(HashMap nie dopuszcza powtórzeń)
    }

    public Piece take(int x, int y) {
        repaint();
        return board.remove(new Point(x, y));
    }

    class UndoButton implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            System.out.println("UNDO");
            redo.setEnabled(true);
        }
    }

    class RedoButton implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            System.out.println("REDO");
        }
    }

    private JButton undo, redo;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Chessboard board = new Chessboard();

        JToolBar bar = new JToolBar();
        board.undo = new JButton(new ImageIcon("src/main/resources/undo.png"));
        board.redo = new JButton(new ImageIcon("src/main/resources/redo.png"));
        board.undo.addActionListener(board.new UndoButton());
        board.redo.addActionListener(board.new RedoButton());
        board.undo.setEnabled(false);
        board.redo.setEnabled(false);
        bar.add(board.undo);
        bar.add(board.redo);

        frame.add(bar, BorderLayout.PAGE_START);
        frame.add(board);

        frame.pack();
        frame.setVisible(true);
    }
}