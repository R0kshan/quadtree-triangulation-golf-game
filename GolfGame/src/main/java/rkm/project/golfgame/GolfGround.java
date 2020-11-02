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
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Golf ground graphical implementation
 * 
 * @author RoxaneKM
 */
public class GolfGround extends JPanel {

    // MEMBERS

    // For graphical implementation :

    /**
     * List of polygons that makes up the golf game
     */
    private ArrayList<PolygonSurface> listOfPolygons;

    /**
     * Quadtree for cutting the ground into regions
     */
    private QuadTree quadTree;

    /**
     * Region that contains the point
     */
    private QuadTree regionContainingPoint;

    /**
     * Triangle that contains the point
     */
    private Triangle triangleContainingPoint;

    /**
     * Golf ball to drawn on the golf ground
     */
    private GolfBall golfBall;

    /**
     * An arbitrary point for testing functions
     */
    private Point<Float> point;

    // States of the graphical representation

    /**
     * State: draw triangles
     */
    private Boolean stateDrawTriangles = false;

    /**
     * State: draw quarter cuts
     */
    private Boolean stateDrawQuarterCuts = false;

    /**
     * State: draw golf ball
     */
    private Boolean stateDrawGolfBall = true;

    /**
     * State: draw first triangle in list created by triangulation
     */
    private Boolean stateDrawFirstTriangle = false;

    /**
     * State: draw region containing point
     */
    private Boolean stateDrawRegionContainingPoint = false;

    /**
     * State: draw triangle containing point
     */
    private Boolean stateDrawTriangleContainingPoint = false;

    /**
     * State: draw point
     */
    private Boolean stateDrawPoint = false;

    // CONSTRUCTORS

    /**
     * Empty constructor
     */
    public GolfGround() {
    }

    /**
     * GolfGround constructor
     * 
     * @param liste list of polygons representing each surface of the golf ground
     * @param qt    quad tree
     */
    public GolfGround(ArrayList<PolygonSurface> liste, QuadTree qt) {
        listOfPolygons = liste;
        quadTree = qt;
    }

    // GETTERS

    /**
     * Get current state : whether quarter cuts are drawn on interface or not
     * 
     * @return boolean true if quarter cuts are drawn on interface, false otherwise
     */
    public boolean stateDrawQuarterCuts() {
        return stateDrawQuarterCuts;
    }

    /**
     * Get current state : whether first triangle is drawn on interface or not
     * 
     * @return boolean true if first triangle is drawn on interface, false otherwise
     */
    public boolean stateDrawFirstTriangle() {
        return stateDrawFirstTriangle;
    }

    /**
     * Get current state : whether triangles are drawn on interface or not
     * 
     * @return boolean true if triangles are drawn on interface, false otherwise
     */
    public boolean stateDrawTriangles() {
        return stateDrawTriangles;
    }

    /**
     * Get current state : whether region containing point is drawn on interface or
     * not
     * 
     * @return boolean true if region containing point is drawn on interface, false
     *         otherwise
     */
    public boolean stateDrawRegionContainingPoint() {
        return stateDrawRegionContainingPoint;
    }

    /**
     * Get current state : whether triangle containing point is drawn on interface
     * or not
     * 
     * @return boolean true if triangle containing point is drawn on interface,
     *         false otherwise
     */
    public boolean stateDrawTriangleContainingPoint() {
        return stateDrawTriangleContainingPoint;
    }

    /**
     * Get current state : whether points are drawn on interface or not
     * 
     * @return boolean true if points are drawn on interface, false otherwise
     */
    public boolean stateDrawPoint() {
        return stateDrawPoint;
    }

    /**
     * Retrieves region containing the given point
     * 
     * @return region containing the given point
     */
    public QuadTree regionContainingPoint() {
        return regionContainingPoint;
    }

    /**
     * Retrieves the triangle containing the given point
     * 
     * @return triangle containing the given point
     */
    public Triangle triangleContainingPoint() {
        return triangleContainingPoint;
    }

    // SETTERS

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawQuarterCuts(boolean bool) {
        stateDrawQuarterCuts = bool;
    }

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawFirstTriangle(boolean bool) {
        stateDrawFirstTriangle = bool;
    }

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawTriangles(boolean bool) {
        stateDrawTriangles = bool;
    }

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawRegionContainingPoint(boolean bool) {
        stateDrawRegionContainingPoint = bool;
    }

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawTriangleContainingPoint(boolean bool) {
        stateDrawTriangleContainingPoint = bool;
    }

    /**
     * Set state to true or false
     * 
     * @param bool true or false
     */
    public void setStateDrawPoint(boolean bool) {
        stateDrawPoint = bool;
    }

    /**
     * Set the region containing the point
     * 
     * @param region region containing the point
     */
    public void setRegionContainingPoint(QuadTree region) {
        regionContainingPoint = region;
    }

    /**
     * Set the triangle containing the point
     * 
     * @param triangle containing the point
     */
    public void setTriangleContainingPoint(Triangle triangle) {
        triangleContainingPoint = triangle;
    }

    /**
     * Set a new list of surface polygons
     * 
     * @param liste list of surface polygons
     */
    public void setListOfPolygons(ArrayList<PolygonSurface> liste) {
        listOfPolygons = liste;
    }

    /**
     * Set a new quadtree
     * 
     * @param qt new quad tree to be set
     */
    public void setQuadTree(QuadTree qt) {
        quadTree = qt;
    }

    /**
     * Set a new golf ball
     * 
     * @param gb golf ball
     */
    public void setGolfBall(GolfBall gb) {
        golfBall = gb;
    }

    /**
     * Set a new point
     * 
     * @param pt new point to be set
     */
    public void setPoint(Point<Float> pt) {
        point = pt;
    }

    // METHODS

    /**
     * Reset the golf ground to initial state when program is launched
     */
    public void reset() {
        listOfPolygons = null;
        quadTree = null;
        golfBall = null;
    }

    /**
     * Paint on the ground interface
     * 
     * @param g instance of Graphics clas
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If no ground file has been loaded colour the golf ground panel white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // If the list of polygons is not empty than draw the golf ground
        if (listOfPolygons != null) {
            drawGolfGround(g);

            if (stateDrawFirstTriangle) {
                drawFirstTriangle(g);
            }
        }

        if (stateDrawQuarterCuts) {
            drawQuarterCuts(g, quadTree);
        }

        if (stateDrawRegionContainingPoint && regionContainingPoint != null) {
            drawRegionContainingPoint(g);
        }

        if (stateDrawTriangleContainingPoint && triangleContainingPoint != null) {
            drawTriangleContainingPoint(g);
        }

        if (stateDrawGolfBall == true && golfBall != null) {
            drawGolfBall(g);
        }

        if (stateDrawPoint && point != null) {
            drawPoint(g);
            System.out.println("draw point");
        }
    }

    /**
     * Draw the golf ground in JPannel
     * 
     * @param g instance of Graphics class
     */
    public void drawGolfGround(Graphics g) {
        // Draw all surface polygons

        // Go through whole liste of surface polygons
        for (int indexCurrPoly = 0; indexCurrPoly < listOfPolygons.size(); indexCurrPoly++) {
            int xpoints[] = listOfPolygons.get(indexCurrPoly).tabCoordsX();
            int ypoints[] = listOfPolygons.get(indexCurrPoly).tabCoordsY();
            int npoints = listOfPolygons.get(indexCurrPoly).nbPoints();

            // Draw the current polygon
            g.setColor(listOfPolygons.get(indexCurrPoly).color());
            g.fillPolygon(xpoints, ypoints, npoints);

            // Draw black border of the current polygon
            // g.setColor(Color.black);
            // g.drawPolygon(xpoints, ypoints, npoints);

            // Draw the triangle (generated by triangulation function) for the current
            // polygon
            if (stateDrawTriangles == true) {
                g.setColor(Color.black);
                for (int indTriangle = 0; indTriangle < listOfPolygons.get(indexCurrPoly).listOfTriangles()
                        .size(); indTriangle++) {
                    int xpointsTriangle[] = listOfPolygons.get(indexCurrPoly).listOfTriangles().get(indTriangle)
                            .tabCoordsX();
                    int ypointsTriangle[] = listOfPolygons.get(indexCurrPoly).listOfTriangles().get(indTriangle)
                            .tabCoordsY();
                    g.drawPolygon(xpointsTriangle, ypointsTriangle, 3);
                }
            }
        }
    }

    /**
     * Draw the region containing the 'arbitrary point' (for testing functions)
     * 
     * @param g instance of Graphics class
     */
    public void drawRegionContainingPoint(Graphics g) {
        g.setColor(Color.blue);
        int xpointsQuarter[] = regionContainingPoint.xCoords();
        int ypointsQuarter[] = regionContainingPoint.yCoords();
        g.drawPolygon(xpointsQuarter, ypointsQuarter, 4);
    }

    /**
     * Draw the triangle containing the 'arbitrary' point (for testing functions)
     * 
     * @param g instance of Graphics class
     */
    public void drawTriangleContainingPoint(Graphics g) {
        g.setColor(Color.blue);
        int xpointsTriangle[] = triangleContainingPoint.tabCoordsX();
        int ypointsTriangle[] = triangleContainingPoint.tabCoordsY();
        g.drawPolygon(xpointsTriangle, ypointsTriangle, 3);
    }

    /**
     * Draw all the quarter cuts from quad tree
     * 
     * @param g        instance of Graphics class
     * @param quadTree quad tree (recursively contains the quarter cuts of the golf
     *                 ground)
     */
    public void drawQuarterCuts(Graphics g, QuadTree quadTree) {
        // Set the color to red
        g.setColor(Color.red);

        // Draw all the regions of the current quad tree
        for (int filsCourant = 0; filsCourant < quadTree.children().length; filsCourant++) {
            int xpointsQuarter[] = quadTree.children()[filsCourant].xCoords();
            int ypointsQuarter[] = quadTree.children()[filsCourant].yCoords();
            g.drawPolygon(xpointsQuarter, ypointsQuarter, 4);
        }

        // Recursively call the function to draw the quad trees
        for (int currentChild = 0; currentChild < quadTree.children().length; currentChild++) {
            if (quadTree.children()[currentChild].nbOfChildren() > 0)
                drawQuarterCuts(g, quadTree.children()[currentChild]);
        }
    }

    /**
     * Draw the golf ball
     * 
     * @param g instance of Graphics class
     */
    public void drawGolfBall(Graphics g) {
        g.setColor(golfBall.colour());
        int x = golfBall.xGraphicCoord();
        int y = golfBall.yGraphicCoord();
        g.fillOval(x - (golfBall.radius() / 2), y - (golfBall.radius() / 2), golfBall.radius(), golfBall.radius());
        g.setColor(Color.black);
        g.drawOval(x - (golfBall.radius() / 2), y - (golfBall.radius() / 2), golfBall.radius(), golfBall.radius());
    }

    /**
     * Draw the first triangle (generated from triangulation)
     * 
     * @param g instance of Graphics class
     */
    public void drawFirstTriangle(Graphics g) {
        g.setColor(Color.blue);
        int xpointsTriangle[] = listOfPolygons.get(0).listOfTriangles().get(0).tabCoordsX();
        int ypointsTriangle[] = listOfPolygons.get(0).listOfTriangles().get(0).tabCoordsY();
        g.drawPolygon(xpointsTriangle, ypointsTriangle, 3);
    }

    /**
     * Draw the 'arbitrary' point for testing functions
     * 
     * @param g instance of Graphics class
     */
    public void drawPoint(Graphics g) {
        g.setColor(Color.red);
        int x = Math.round(point.x() * 10 * 4);
        int y = Math.round((10 - point.y()) * 10 * 4);
        g.fillOval(x - 2, y - 2, 4, 4);
    }
}
