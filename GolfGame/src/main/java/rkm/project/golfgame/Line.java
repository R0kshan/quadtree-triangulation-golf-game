/*******************************************************************************
 *
 * Contributors:
 *     Røkshan              -   Code refactoring
 *                          -   Comments and variables translation to english
 *                          -   Doxygen documentation
 *     InesDt               -   Initial implementation
 *
 ******************************************************************************/
package rkm.project.golfgame;

/**
 * Line class calculated from 2 points
 * 
 * @author InesDt
 */
public class Line {

    // MEMBERS

    /**
     * a coefficient use to calculate line equation
     */
    private float a;

    /**
     * b coefficient use to calculate line equation
     */
    private float b;

    /**
     * c coefficient use to calculate line equation
     */
    private float c;

    // CONSTRUCTORS

    /**
     * Empty constructor
     */
    public Line() {
    }

    /**
     * Line constructor
     * 
     * @param p1 starting point
     * @param p2 ending point
     */
    public Line(Point<Float> p1, Point<Float> p2) {
        Line d = calcLineCoefficients(p1, p2);
        setA(d.a());
        setB(d.b());
        setC(d.c());
    }

    /**
     * Line class constructor
     * 
     * @param a float coefficient value
     * @param b float coefficient value
     * @param c float coefficient value
     */
    public Line(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // GETTERS

    /**
     * Retrieves the a coefficient value of the line equation
     * 
     * @return a float containing the coefficient value of a coefficient
     */
    public float a() {
        return this.a;
    }

    /**
     * Retrieves the b coefficient value of the line equation
     * 
     * @return b float containing the coefficient value of b coefficient
     */
    public float b() {
        return this.b;
    }

    /**
     * Retrieves the c coefficient value of the line equation
     * 
     * @return c float containing the coefficient value of c coefficient
     */
    public float c() {
        return this.c;
    }

    // SETTERS

    /**
     * Set a new value for the a coefficient value of the line equation
     * 
     * @param a new float value for the a coefficient of the line equation
     */
    public void setA(float a) {
        this.a = a;
    }

    /**
     * Set a new value for the b coefficient value of the line equation
     * 
     * @param b new float value for the b coefficient of the line equation
     */
    public void setB(float b) {
        this.b = b;
    }

    /**
     * Set a new value for the c coefficient value of the line equation
     * 
     * @param c new float value for the c coefficient of the line equation
     */
    public void setC(float c) {
        this.c = c;
    }

    // METHODS

    /**
     * Calculate the coefficients a Line using 2 points
     * 
     * @param p1 starting point
     * @param p2 ending point
     * @return new line calculated from 2 points
     */
    public Line calcLineCoefficients(Point<Float> p1, Point<Float> p2) {
        float a, b, c;
        a = p1.y() - p2.y();
        b = p2.x() - p1.x();
        c = -a * p1.x() - b * p1.y();
        Line newLine = new Line(a, b, c);
        return newLine;
    }

    /**
     * Determine point of intersection between 2 lines
     * 
     * @param otherLine other line
     * @return point of intersection between this.Line and otherLine
     */
    public Point intersectionPoint(Line otherLine) {
        float x, y;
        if ((this.a * otherLine.b != otherLine.a * this.b) && (this.a != 0)) {
            y = (this.c * otherLine.a - otherLine.c * this.a) / (this.a * otherLine.b - otherLine.a * this.b);
            x = (-this.b * y - this.c) / this.a;
            Point<Float> intersectionPoint = new Point<Float>(x, y);
            return intersectionPoint;
        } else {
            // There is no intersection
            return null;
        }
    }

    /**
     * Print the line's equation
     */
    public void printEquation() {
        System.out.println(a + "x + " + b + "y + " + c + " = 0");
    }

}
