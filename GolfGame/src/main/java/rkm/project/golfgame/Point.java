/*******************************************************************************
 *
 * Contributors:
 *     Røkshan              -   Code refactoring
 *                          -   Comments and variables translation to english
 *                          -   Doxygen documentation
 *                          -   Added function halfSpaceSeg()
 *     InesDt               -   Initial implementation
 *
 ******************************************************************************/
package rkm.project.golfgame;

/**
 * Generic Point class
 *
 * @author InesDt
 */
public class Point<T> {
    // MEMBERS
    private T x;
    private T y;

    // CONSTRUCTORS

    /**
     * Empty constructor
     */
    public Point() {
    }

    /**
     * Point constructor
     * 
     * @param x coordinate position
     * @param y coordinate position
     */
    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    // GETTERS

    /**
     * Retrieve value of x coordinate
     * 
     * @return x coordinate value
     */
    public T x() {
        return this.x;
    }

    /**
     * Retrieve value of y coordinate
     * 
     * @return y coordinate value
     */
    public T y() {
        return this.y;
    }

    // SETTERS

    /**
     * Set a new value for x coordinate
     * 
     * @param x new value for x coordinate
     */
    public void setX(T x) {
        this.x = x;
    }

    /**
     * Set a new value for y coordinate
     * 
     * @param y new value for y coordinate
     */
    public void setY(T y) {
        this.y = y;
    }

    // METHODS

    /**
     * Determine if point belongs to a half space of a line
     * 
     * @param line that allows to check whether this point belongs to one of it's
     *             half space
     * @return 0 if this point is on the line, 1 if point is in lower half space of
     *         the line and 2 if it's in the upper half space of the line
     */
    public int halfSpace(Line line) {

        if (((Float) x * line.a() + (Float) y * line.b() + line.c()) == 0) {
            // The point belongs to the line
            return 0;
        } else {
            // The point doesn't belong to the line
            if (((Float) x * line.a() + (Float) y * line.b() + line.c()) < 0) {
                // The point belongs to the lower half space of the line
                return 1;
            } else {
                // The point belongs the upper half space of the line
                return 2;
            }
        }
    }

    /**
     * Determine if point belongs to a half space of a segment
     * 
     * @param segment that allows to check whether this point belongs to one of it's
     *                half space
     * @return 0 if this point is on the segment, 1 if point is in lower half space
     *         of the segment and 2 if it's in the upper half space of the segment
     */
    public int halfSpaceSeg(Segment segment) {

        // This point is on the segment
        if (((Float) x * segment.a() + (Float) y * segment.b() + segment.c()) == 0) {
            return 0;
        }
        // Else if point doesn't belong the segment, check if it's in lower half or
        // upper half space
        else {
            // Check if point belongs to lower half space
            if (((Float) x * segment.a() + (Float) y * segment.b() + segment.c()) < 0) {
                // Check if the x coordinate of this points is on the segment's extremities
                if ((Float) x > segment.p1X() && (Float) x < segment.p2X()) {
                    return 1;
                } else
                    return 0;
            }
            // Else the points belongs to the lower half space
            else {
                // Check if the x coordinate of this points is on the segment's extremities
                if ((Float) x > segment.p1X() && (Float) x < segment.p2X()) {
                    return 1;
                } else
                    return 2;
            }
        }
    }

    /**
     * Check if this point equals another point
     * 
     * @param obj to compare with this point
     * @return boolean true if this point equals obj else return false
     */
    public boolean equals(Object obj) {
        return (obj instanceof Point) && (((Point) obj).x == this.x) && (((Point) obj).y == this.y);
    }

}
