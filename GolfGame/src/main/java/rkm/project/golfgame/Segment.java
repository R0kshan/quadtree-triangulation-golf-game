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
 * Segment class
 * 
 * @author InesDt
 */
public class Segment extends Line {

    // MEMBERS

    /**
     * Starting point
     */
    private Point<Float> p1;

    /**
     * Ending point
     */
    private Point<Float> p2;

    // CONSTRUCTORS

    /**
     * Empty constructor
     */
    public Segment() {
    }

    /**
     * Segment constructor
     * 
     * @param p1 starting point
     * @param p2 ending point
     */
    public Segment(Point<Float> p1, Point<Float> p2) {
        super(p1, p2);
        this.p1 = p1;
        this.p2 = p2;
    }

    // GETTERS

    /**
     * Retrives x coordinate of point p1
     * 
     * @return x coordinate of p1
     */
    public float p1X() {
        return p1.x();
    }

    /**
     * Retrives y coordinate of point p1
     * 
     * @return y coordinate of p1
     */
    public float p1Y() {
        return p1.y();
    }

    /**
     * Retrives x coordinate of point p2
     * 
     * @return x coordinate of p2
     */
    public float p2X() {
        return p2.x();
    }

    /**
     * Retrives y coordinate of point p2
     * 
     * @return y coordinate of p2
     */
    public float p2Y() {
        return p2.y();
    }

    // METHODS

    /**
     * Test intersection between 2 segments
     * 
     * @param seg segment
     * @return boolean true, if there's en intersection between the 2 segments and
     *         else returns false
     */
    public boolean intersectionBetween2Segments(Segment seg) {

        // MEMBERS
        Point pt = super.intersectionPoint(seg);

        // PROCEDURES

        // Check if there's an intersecting point between the 2 segments
        if (pt != null && this.containsPoint(pt) && seg.containsPoint(pt)) {
            // Check if this point belongs to the either 1 of the extremities of the 2
            // segments
            if (pt.equals(this.p1) || pt.equals(this.p2) || pt.equals(seg.p1) || pt.equals(seg.p2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Check if this segment intersects another
     * 
     * @param other other segment to test intersection with
     * @return true if this segment intersects other segment, else returns false
     */
    public boolean intersectsSegment(Segment other) {
        // VARIABLES
        Point p = super.intersectionPoint(other);

        // PROCEDURES
        if (p != null && this.containsPoint(p) && other.containsPoint(p)) {
            return true;
        } else
            return false;
    }

    /**
     * Check if this segment contains the given point
     * 
     * @param pt point
     * @return true if this segment contains the given point, false otherwise
     */
    public boolean containsPoint(Point pt) {
        return (Math.min(p1.x(), p2.x()) <= (Float) pt.x()) && ((Float) pt.x() <= Math.max(p1.x(), p2.x()))
                && (Math.min(p1.y(), p2.y()) <= (Float) pt.y()) && ((Float) pt.y() <= Math.max(p1.y(), p2.y()));
    }

}
