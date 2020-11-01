package rkm.project.golfgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Point class
 * @author RoxaneKM
 */
public class PointTest {

    // MEMBERS ------------------------------------------------
    private Line line;
    private Segment segment;
    private Point<Float> point;

    // TEST INITIALISER ---------------------------------------
    @BeforeEach
    void setUp() {
        line = new Line(new Point(0f,2f), new Point(5f,2f));
        segment = new Segment(new Point(0f,2f), new Point(5f,2f));
        point = new Point<>();
    }

    // TESTS --------------------------------------------------

    @Test
    public void testBelongsToLine() {
        point.setX(2f);
        point.setY(2f);
        assertEquals(0,point.halfSpace(line));
    }

    @Test
    public void testBelongsToSegment() {
        point.setX(2f);
        point.setY(2f);
        assertEquals(0,point.halfSpace(segment));
    }

    @Test
    public void testDoesNotBelongToLine() {
        point.setX(2f);
        point.setY(3f);
        assertFalse(point.halfSpace(line)== 0);
    }

    @Test
    public void testDoesNotBelongToSegment() {
        point.setX(-0f);
        point.setY(3f);
        assertFalse(point.halfSpace(line)== 0);
    }

    @Test
    public void testLowerLineHalfSpace() {
        point.setX(-1f);
        point.setY(1f);
        assertEquals(1,point.halfSpace(line));
    }

    @Test
    public void testLowerSegmentHalfSpace() {
        point.setX(0f);
        point.setY(1f);
        assertEquals(1,point.halfSpace(line));
    }

    @Test
    public void testUpperLineHalfSpace() {
        point.setX(-1f);
        point.setY(3f);
        assertEquals(2,point.halfSpace(line));
    }

    @Test
    public void testUpperSegmentHalfSpace() {
        point.setX(0f);
        point.setY(3f);
        assertEquals(2,point.halfSpace(line));
    }


}
