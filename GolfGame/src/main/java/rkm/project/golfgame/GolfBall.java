/*******************************************************************************
 *
 * Contributors:
 *     Røkshan              -   Graphical implementation of Ball class
 *                          -   Variables and comments translation to english
 *                          -   Code refactoring
 *                          -   Doxygen documentation
 *     InesDt               -   GolfBall movement calculation functions
 *
 ******************************************************************************/
package rkm.project.golfgame;

import java.awt.Color;
import java.util.Random;

/**
 * GolfBall class
 *
 * @author RoxaneKM, InesDt
 */
public class GolfBall {

    // MEMBERS ----------------------------------------------

    /**
     * Radius of the golf ball
     */
    private float radius;

    /**
     * Colour of the golf ball
     */
    private Color colour;

    /**
     * X Coordinate position of the golf ball on the graphical interface
     */
    private int xGraphicCoord;

    /**
     * Y coordinate position of the golf ball on the graphical interface
     */
    private int yGraphicCoord;

    /**
     * Real x coordinate
     */
    private float xCoord;

    /**
     * Real y coordinate
     */
    private float yCoord;

    /**
     * Point containing both x and y coordinate of landing position
     */
    private Point<Float> landingPoint;

    /**
     * distance between between the golf ball and the landing position
     */
    private double distance;

    // CONSTRUCTORS

    /**
     * Empty constructor
     * 
     * @author RoxaneKM
     */
    public GolfBall() {

    }

    /**
     * GolfBall constructor
     * 
     * @param xCoord x coordinate position
     * @param yCoord y coordinate position
     * @param radius radius of the golf ball
     * @param colour colour of the golf ball
     * @author RoxaneKM
     */
    public GolfBall(float xCoord, float yCoord, float radius, Color colour) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.radius = radius;
        this.colour = colour;
        xGraphicCoord = Math.round((xCoord) * 10 * 4);
        yGraphicCoord = Math.round((yCoord) * 10 * 4);
    }

    // GETTERS

    /**
     * Retrieves the colour of the golf ball
     * 
     * @return the colour of the golf ball
     */
    public Color colour() {
        return colour;
    }

    /**
     * Retrieves the x coordinate of the golf ball on the graphical interface
     * 
     * @return an int value containing the x coordinate of the golf ball on the
     *         graphical interface
     */
    public int xGraphicCoord() {
        return xGraphicCoord;
    }

    /**
     * Retrieves the y coordinate of the golf ball on the graphical interface
     * 
     * @return an int value containing the y coordinate of the golf ball on the
     *         graphical interface
     */
    public int yGraphicCoord() {
        return yGraphicCoord;
    }

    /**
     * Retrieves the graphical radius of the golf ball
     * 
     * @return an int value containing radius of the golf ball
     */
    public int radius() {
        return Math.round(radius);
    }

    /**
     * Retrieves the x coordinate
     * 
     * @return
     */
    public float xCoord() {
        return xCoord;
    }

    /**
     * Retrieves the y coordinate
     * 
     * @return
     */
    public float yCoord() {
        return yCoord;
    }

    /**
     * Retrieves the landing point
     * 
     * @return
     */
    public Point<Float> landingPoint() {
        return landingPoint;
    }

    // METHODS

    /**
     * Calculate the distance between 2 points
     * 
     * @param targetX x coordinate of target point
     * @param targetY y coordinate of target point
     */
    public void distance(float targetX, float targetY) {
        distance = Math.sqrt(Math.pow(xCoord - targetX, 2) + Math.pow(targetY - yCoord, 2));
    }

    /**
     * Calculate the landing position of the golf ball
     * 
     * @param startingPoint starting point
     * @param target        target position of the golf ball
     */
    public void calculateNextLandingPoint(Point<Float> startingPoint, Point<Float> target) {

        // Calculate the euclidien distance between the 2 points

        distance(target.x(), target.y());

        Line line = new Line(startingPoint, target);
        Point<Float> a1 = new Point<Float>(1.0f, 0.0f);
        Point<Float> a2 = new Point<Float>(2.0f, 0.0f);
        Line abscisse;
        abscisse = new Line(a1, a2);
        Point<Float> inter = line.intersectionPoint(abscisse);

        // Calculate the slope of the line passing thrown the starting point and the
        // golf ball
        double slope = (Math.max(startingPoint.y(), target.y()) - Math.min(startingPoint.y(), target.y()))
                / (Math.max(startingPoint.x(), target.x()) - Math.min(startingPoint.x(), target.x()));
        double angle;
        if ((startingPoint.x() >= inter.x()) && (target.x() >= inter.x())) {

            angle = Math.toDegrees(Math.atan(slope));
        } else {
            angle = (90.0 + (90.0 - Math.toDegrees(Math.atan(slope))));
        }

        float degrees[] = { -40, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 40 };
        float percentages[] = { -0.4f, -0.1f, -0.09f, -0.08f, -0.07f, -0.06f, -0.05f, -0.04f, -0.03f, -0.02f, -0.01f,
                0f, 0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.06f, 0.07f, 0.08f, 0.09f, 0.10f, 0.4f };
        Random rand = new Random();
        angle = angle + degrees[rand.nextInt(degrees.length)];
        distance = distance + distance * percentages[rand.nextInt(degrees.length)];

        Point<Float> newTarget = new Point();

        angle = Math.toRadians(angle);
        newTarget.setX(startingPoint.x() + (float) distance * (float) Math.rint(Math.cos((angle))));
        newTarget.setY(startingPoint.y() + (float) distance * (float) Math.rint(Math.sin((angle))));
        landingPoint = newTarget;

        xCoord = landingPoint.x();
        yCoord = landingPoint.y();

        xGraphicCoord = Math.round((xCoord) * 10 * 4);
        yGraphicCoord = Math.round((yCoord) * 10 * 4);
    }
}
