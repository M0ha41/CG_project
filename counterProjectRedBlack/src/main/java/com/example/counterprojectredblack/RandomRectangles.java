package com.example.counterprojectredblack;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class RandomRectangles extends JComponent {
    private static final long serialVersionUID = 1L;
    private static final int NUM_RECTANGLES = 20;
    private static final int MIN_WIDTH = 10;
    private static final int MAX_WIDTH = 50;
    private static final int MIN_HEIGHT = 10;
    private static final int MAX_HEIGHT = 50;
    private static final int MIN_X = 0;
    private static final int MAX_X = 300;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 300;
    private static final Random rand = new Random();
    private Rectangle[] rectangles = new Rectangle[NUM_RECTANGLES];

    public RandomRectangles() {
        for (int i = 0; i < NUM_RECTANGLES; i++) {
            int width = MIN_WIDTH + rand.nextInt(MAX_WIDTH - MIN_WIDTH + 1);
            int height = MIN_HEIGHT + rand.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);
            int x = MIN_X + rand.nextInt(MAX_X - MIN_X + 1);
            int y = MIN_Y + rand.nextInt(MAX_Y - MIN_Y + 1);
            rectangles[i] = new Rectangle(x, y, width, height);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int overlappingRectangles = 0;
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = i + 1; j < rectangles.length; j++) {
                if (rectangles[i].intersects(rectangles[j])) {
                    overlappingRectangles++;
                }
            }
        }
        for (Rectangle rectangle : rectangles) {
            g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g.setColor(Color.BLACK);
        g.drawString("Number of rectangles: " + rectangles.length, 10, 20);
        g.drawString("Number of overlapping rectangles: " + overlappingRectangles / 2, 10, 40);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Rectangles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new RandomRectangles());
        frame.setVisible(true);
    }
}
