package com.example.demo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Contour {

    public static List<Point> computeContour(List<Rectangle> rectangles) {
        List<Point> vertices = new ArrayList<>();
        for (Rectangle rectangle : rectangles) {
            vertices.add(rectangle.topLeft);
            vertices.add(rectangle.topRight);
            vertices.add(rectangle.bottomLeft);
            vertices.add(rectangle.bottomRight);
        }

        // Sort the vertices by their x-coordinate
        Collections.sort(vertices, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Integer.compare(p1.x, p2.x);
            }
        });

        // Find the lower and upper tangent points of the convex hull
        Point[] lowerTangents = lowerTangents(vertices);
        Point[] upperTangents = upperTangents(vertices);

        List<Point> contour = new ArrayList<>();
        for (int i = 0; i < lowerTangents.length; i++) {
            contour.add(lowerTangents[i]);
        }
        for (int i = upperTangents.length - 1; i >= 0; i--) {
            contour.add(upperTangents[i]);
        }
        return contour;
    }

    private static Point[] lowerTangents(List<Point> points) {
        int n = points.size();
        Point[] tangents = new Point[n];
        int k = 0;

        // Build lower tangents
        for (int i = 0; i < n; i++) {
            while (k >= 2 && crossProduct(tangents[k - 2], tangents[k - 1], points.get(i)) <= 0) {
                k--;
            }
            tangents[k++] = points.get(i);
        }

        return tangents;
    }

    private static Point[] upperTangents(List<Point> points) {
        int n = points.size();
        Point[] tangents = new Point[n];
        int k = 0;

        // Build upper tangents
        for (int i = n - 1; i >= 0; i--) {
            while (k >= 2 && crossProduct(tangents[k - 2], tangents[k - 1], points.get(i)) <= 0) {
                k--;
            }
            tangents[k++] = points.get(i);
        }

        return tangents;
    }

    private static int crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    public static class Rectangle {
        public Point topLeft;
        public Point topRight;
        public Point bottomLeft;
        public Point bottomRight;

        public Rectangle(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    public static List<Point> computeContour(List<Rectangle> rectangles) {
        List<Point> vertices = new ArrayList<>();
        for (Rectangle rectangle : rectangles) {
            vertices.add(rectangle.topLeft);
            vertices.add(rectangle.topRight);
            vertices.add(rectangle.bottomLeft);
            vertices.add(rectangle.bottomRight);
        }

// Sort the vertices by their x-coordinate
        Collections.sort(vertices, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Integer.compare(p1.x, p2.x);
            }
        });

// Find the lower and upper tangent points of the convex hull
        Point[] lowerTangents = lowerTangents(vertices);
        Point[] upperTangents = upperTangents(vertices);

        List<Point> contour = new ArrayList<>();
        for (int i = 0; i < lowerTangents.length; i++) {
            contour.add(lowerTangents[i]);
        }
        for (int i = upperTangents.length - 1; i >= 0; i--) {
            contour.add(upperTangents[i]);
        }
        return contour;
    }

    private static Point[] lowerTangents(List<Point> points) {
        int n = points.size();
        int k = 0;
        Point[] hull = new Point[2 * n];

// Builds the lower hull
        for (Point point : points) {
            while (k >= 2 && crossProduct(hull[k - 2], hull[k - 1], point) <= 0) {
                k--;
            }
            hull[k++] = point;
        }

// Removes the points on the horizontal line at the bottom
        while (k > 1 && hull[k - 1].y == hull[k - 2].y) {
            k--;
        }

        return Arrays.copyOfRange(hull, 0, k);
    }

    private static Point[] upperTangents(List<Point> points) {
        int n = points.size();
        int k = 0;
        Point[] hull = new Point[2 * n];

// Builds the upper hull
        for (int i = n - 1; i >= 0; i--) {
            Point point = points.get(i);
            while (k >= 2 && crossProduct(hull[k - 2], hull[k - 1], point) <= 0) {
                k--;
            }
            hull[k++] = point;
        }

// Removes the points on the horizontal line at the top
        public static class Rectangle {
            public Point topLeft;
            public Point topRight;
            public Point bottomLeft;
            public Point bottomRight;

            public Rectangle(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
                this.topLeft = topLeft;
                this.topRight = topRight;
                this.bottomLeft = bottomLeft;
                this.bottomRight = bottomRight;
            }
        }

        private static Point[] lowerTangents(List<Point> points) {
            List<Point> lowerTangents = new ArrayList<>();
            int n = points.size();
            int[] next = new int[n];
            for (int i = 0; i < n; i++) {
                next[i] = (i + 1) % n;
            }

            int left = 0;
            for (int i = 0; i < n; i++) {
                if (points.get(i).x < points.get(left).x) {
                    left = i;
                }
            }

            int p = left;
            do {
                lowerTangents.add(points.get(p));
                int q = next[p];
                for (int i = 0; i < n; i++) {
                    if (crossProduct(points.get(p), points.get(i), points.get(q)) < 0) {
                        q = i;
                    }
                }
                for (int i = 0; i < n; i++) {
                    if (i != p && i != q && crossProduct(points.get(p), points.get(i), points.get(q)) == 0) {
                        if (points.get(i).y < points.get(p).y) {
                            q = i;
                        }
                    }
                }
                for (int i = 0; i < n; i++) {
                    if (i != p && crossProduct(points.get(p), points.get(i), points.get(q)) < 0) {
                        next[i] = q;
                    }
                }
                p = q;
            } while (p != left);

            return lowerTangents.toArray(new Point[lowerTangents.size()]);
        }

        private static Point[] upperTangents(List<Point> points) {
            List<Point> upperTangents = new ArrayList<>();
            int n = points.size();
            int[] next = new int[n];
            for (int i = 0; i < n; i++) {
                next[i] = (i + 1) % n;
            }

            int left = 0;
            for (int i = 0; i < n; i++) {
                if (points.get(i).x < points.get(left).x) {
                    left = i;
                }
            }

            int p = left;
            do {
                upperTangents.add(points.get(p));
                int q = next[p];
                for (int i = 0; i < n; i++) {
                    if (crossProduct(points.get(p), points.get(i), points.get(q)) < 0) {
                        p = i;
                    }
                }
                upperTangents.add(points.get(p));

                // Construct the upper tangent
                for (int i = n - 2, t = p; i >= 0; i--) {
                    while (i < n - 2 && crossProduct(points.get(i), points.get(i + 1), points.get(t)) < crossProduct(points.get(i), points.get(i + 1), points.get(t + 1))) {
                        t = (t + 1) % n;
                        upperTangents.add(points.get(t));
                    }
                    upperTangents.add(points.get(i));
                }
                return upperTangents.toArray(new Point[upperTangents.size()]);
            }

