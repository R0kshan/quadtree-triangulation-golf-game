/*******************************************************************************
 *
 * Contributors:
 *     Røkshan              -   Initial implementation
 *                          -   Code refactoring
 *                          -   Comments and variables translation to english
 *                          -   Doxygen documentation
 *
 ******************************************************************************/
package rkm.project.golfgame;

import java.awt.Color;

/**
 * Triangle class
 * 
 * @author RoxaneKang
 */

public class Triangle {

    // MEMBERS

    /**
     * Array of 3 points composing the triangles
     */
    private Point[] points = new Point[3];

    /**
     * Array of 3 segments representing the sides of the trangles
     */
    private Segment[] segments = new Segment[3];

    /**
     * Color of the triangle
     */
    private Color color;

    // CONSTRUCTORS

    /**
     * Empty constructor
     */
    public Triangle() {

    }

    /**
     * Triangle constructor using 3 points
     * 
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Point p1, Point p2, Point p3) {

        points[0] = p1;
        points[1] = p2;
        points[2] = p3;

        segments[0] = new Segment(p1, p2);
        segments[1] = new Segment(p2, p3);
        segments[2] = new Segment(p3, p1);
    }

    /**
     * Triangle construcor using 3 points as well as setting the colour
     * 
     * @param p1  point 1
     * @param p2  point 2
     * @param p3  point 3
     * @param clr colour of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3, Color clr) {

        points[0] = p1;
        points[1] = p2;
        points[2] = p3;

        segments[0] = new Segment(p1, p2);
        segments[1] = new Segment(p2, p3);
        segments[2] = new Segment(p3, p1);

        color = clr;
    }

    // GETTERS

    /**
     * Retrieve the array of segments composing the triangle
     * 
     * @return array of segments
     */
    public Segment[] segments() {
        return segments;
    }

    /**
     * Retrieve the array of points composing the triangle
     * 
     * @return array of points
     */
    public Point[] points() {
        return points;
    }

    // METHODS

    /**
     * Checks if the triangle contains the given point
     * 
     * @param p point to check whether belongs or not the the triangle
     * @return true if triangle contains given point, false otherwise
     */
    public Boolean testTriangleContainsPoint(Point<Float> p) {
        // VARIABLES
        float area, area1, area2, area3;
        Triangle triangle1, triangle2, triangle3;

        // PROCEDURES
        triangle1 = new Triangle(points[0], points[1], p);
        triangle2 = new Triangle(points[1], points[2], p);
        triangle3 = new Triangle(points[0], points[2], p);

        area = area();
        area1 = triangle1.area();
        area2 = triangle2.area();
        area3 = triangle3.area();

        // Return true if the sum of all the areas of the triangle is equal to the total
        // area
        return (area == area1 + area2 + area3);
    }

    /**
     * Calculate area of the triangle
     * 
     * @return area of the triangle
     */
    public float area() {
        // VARIABLES
        float area;

        // PROCEDURES
        float p1X = (Float) points[0].x();
        float p1Y = (Float) points[0].y();
        float p2X = (Float) points[1].x();
        float p2Y = (Float) points[1].y();
        float p3X = (Float) points[2].x();
        float p3Y = (Float) points[2].y();

        area = Math.abs((p1X * (p2Y - p3Y) + p2X * (p3Y - p1Y) + p3X * (p1Y - p2Y)) / 2);

        // RETURN
        return area;
    }

    /**
     * Retrieve an array of x coordinates of each the points composing the triangle
     * 
     * @return array of x coordinates
     */
    public int[] tabCoordsX() {

        // Modify *4 later to be able to adapt automatically to the size of the window
        int xP1 = Math.round((Float) points[0].x() * 10 * 4);
        int xP2 = Math.round((Float) points[1].x() * 10 * 4);
        int xP3 = Math.round((Float) points[2].x() * 10 * 4);

        int[] tabCoordsX = { xP1, xP2, xP3 };
        return tabCoordsX;
    }

    /**
     * Retrieve an array of y coordinates of each the points composing the triangle
     * 
     * @return array of y coordinates
     */
    public int[] tabCoordsY() {
        // Modify *4 later to be able to adapt automatically to the size of the window
        int yP1 = Math.round((10 - (Float) points[0].y()) * 10 * 4);
        int yP2 = Math.round((10 - (Float) points[1].y()) * 10 * 4);
        int yP3 = Math.round((10 - (Float) points[2].y()) * 10 * 4);
        int[] tabCoordsY = { yP1, yP2, yP3 };
        return tabCoordsY;
    }

    /**
     * Print the x coordinates of the points composing the triangle
     */
    public void printCoords() {
        System.out.println("Sum: (" + points[0].x() + "," + points[0].y() + ") (" + points[1].x() + "," + points[1].y()
                + ")(" + points[2].x() + "," + points[2].y() + ")");
    }

}
