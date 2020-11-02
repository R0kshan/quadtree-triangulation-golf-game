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

import java.util.ArrayList;

/**
 * QuadTree class
 * 
 * @author RoxaneKM
 */
public class QuadTree {

    // MEMBERS

    /**
     * List of polygon surfaces
     */
    private ArrayList<PolygonSurface> surfaceList;

    /**
     * List of intersected triangles
     */
    private ArrayList<Triangle> intersectedTriangles = new ArrayList<Triangle>();

    /**
     * Dimension of a region dimension (a region is a quarter cut)
     */
    private float regionDimension = 0;

    /**
     * Limit number number of triangles that can intersect a quarter cut
     */
    private int limitNbOfIntersectingTriangles = 4;

    /**
     * Array of child nodes of type QuadTree
     */
    private QuadTree[] children = new QuadTree[4];

    /**
     * Array of 4 points
     */
    private Point<Float>[] points = new Point[4];

    /**
     * Array of 4 segments
     */
    private Segment[] segments = new Segment[4];

    // CONSTRUCTOR

    /**
     * Empty constructor
     */
    public QuadTree() {
    }

    /**
     * QuadTree constructor
     * 
     * @param regionDimension                dimension of a quad tree quarter
     * @param limitNbOfIntersectingTriangles limit number number of triangles
     *                                       intersecting a quarter cut
     * @param xStart                         starting x coordinate
     * @param yStart                         starting y coordinate
     */
    public QuadTree(float regionDimension, int limitNbOfIntersectingTriangles, float xStart, float yStart) {
        this.regionDimension = regionDimension;
        this.limitNbOfIntersectingTriangles = limitNbOfIntersectingTriangles;
        initPointsAndSegments(xStart, yStart);
    }

    /**
     * QuadTree constructor
     * 
     * @param regionDimension                dimension of a quad tree quarter
     * @param limitNbOfIntersectingTriangles limit number number of triangles
     *                                       intersecting a quarter cut
     * @param xStart                         starting x coordinate
     * @param yStart                         starting y coordinate
     * @param childNW                        north west child node
     * @param childNE                        north east child node
     * @param childSE                        south east child node
     * @param childSW                        south west child node
     */
    public QuadTree(int regionDimension, int limitNbOfIntersectingTriangles, float xStart, float yStart,
            QuadTree childNW, QuadTree childNE, QuadTree childSE, QuadTree childSW) {
        this.regionDimension = regionDimension;
        this.limitNbOfIntersectingTriangles = limitNbOfIntersectingTriangles;
        children[0] = childNW;
        children[1] = childNE;
        children[2] = childSE;
        children[3] = childSW;
        initPointsAndSegments(xStart, yStart);
    }

    // GETTERS

    /**
     * Retrieves the list of intersected triangles
     * 
     * @return list containing the intersected triangles
     */
    public ArrayList<Triangle> intersectedTriangles() {
        return intersectedTriangles;
    }

    /**
     * Array of quadtree child nodes
     * 
     * @return array of quadtree child nodes
     */
    public QuadTree[] children() {
        return children;
    }

    // METHODS

    /**
     * Reset the quad tree by emptying the list of polygon surfaces
     */
    public void reset() {
        surfaceList = null;
    }

    /**
     * Set a new list of surface polygon
     * 
     * @param surfaces list of polygon surfaces
     */
    public void setSurfaceList(ArrayList<PolygonSurface> surfaces) {
        surfaceList = surfaces;
    }

    /**
     * Set a new limit to number of intersected triangles per region
     * 
     * @param limit limit number of intersections between triangles and the region
     */
    public void setLimiteIntersectionsTriangles(int limit) {
        limitNbOfIntersectingTriangles = limit;
    }

    /**
     * Initialise points and segments according to starting x and y coordinates
     * 
     * @param xStart starting x coordinate
     * @param yStart starting y coordinate
     */
    private void initPointsAndSegments(float xStart, float yStart) {
        Point<Float> p1 = new Point<Float>(xStart, yStart);
        Point<Float> p2 = new Point<Float>(p1.x() + (float) regionDimension, p1.y());
        Point<Float> p3 = new Point<Float>(p1.x() + (float) regionDimension, p1.y() + (float) regionDimension);
        Point<Float> p4 = new Point<Float>(p1.x(), p1.y() + (float) regionDimension);

        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;

        segments[0] = new Segment(p1, p2);
        segments[1] = new Segment(p2, p3);
        segments[2] = new Segment(p3, p4);
        segments[3] = new Segment(p4, p1);
    }

    /**
     * Build a QuadTree
     */
    public void build() {
        // VARIABLES
        float lenghtOfRegionSide = 0;

        // PROCEDURES

        // Check if the current quarter intersects more triangles than the limitation
        if (countNbIntersectionsRegionTriangle(surfaceList) > limitNbOfIntersectingTriangles) {

            // Cut the ground into quarter (since it's a square and not a rectangle all we
            // need to do is divide the dimension by 2
            lenghtOfRegionSide = regionDimension / 2;

            // Create child nodes that also quad trees
            children[0] = new QuadTree(lenghtOfRegionSide, limitNbOfIntersectingTriangles, points[0].x(),
                    points[0].y());
            children[0].setSurfaceList(surfaceList);
            children[1] = new QuadTree(lenghtOfRegionSide, limitNbOfIntersectingTriangles,
                    points[0].x() + (float) lenghtOfRegionSide, points[0].y());
            children[1].setSurfaceList(surfaceList);
            children[2] = new QuadTree(lenghtOfRegionSide, limitNbOfIntersectingTriangles,
                    points[0].x() + (float) lenghtOfRegionSide, points[0].y() + (float) lenghtOfRegionSide);
            children[2].setSurfaceList(surfaceList);
            children[3] = new QuadTree(lenghtOfRegionSide, limitNbOfIntersectingTriangles, points[0].x(),
                    points[0].y() + (float) lenghtOfRegionSide);
            children[3].setSurfaceList(surfaceList);

            // Sinon mettre la la liste des triangles intersectés dans la region

            // Loop through all child nodes
            for (int filsCourant = 0; filsCourant < children.length; filsCourant++) {

                if (children[filsCourant] != null) {

                    // If the number of intersections is < limite number of authorized
                    // intersections, then continue cutting
                    if (children[filsCourant]
                            .countNbIntersectionsRegionTriangle(surfaceList) > limitNbOfIntersectingTriangles) {
                        children[filsCourant].build();
                    }
                    // Else add the list of intersected triangled to the leaf
                    else {
                        children[filsCourant].intersectedTriangles = listOfIntersectedTriangles(surfaceList);
                    }
                }
            }
        }
    }

    /**
     * Check if current region contains the given point
     * 
     * @param point point to check whether is contained by this quarter or not
     * @return regionContenantPoint if point is contained, else return false
     */
    public QuadTree containsPoint(Point<Float> point) {

        // VARIABLES
        int filsCourant = 0;
        QuadTree regionContainingPoint = null;

        // PROCEDURES

        // Check if the current region contains the point
        if (regionHasPoint(point)) {
            regionContainingPoint = this;

            if (children[0] != null) {

                // Loop through all children
                while (filsCourant < children.length) {

                    // Check if the current child node contains the point
                    if (children[filsCourant].regionHasPoint(point)) {
                        regionContainingPoint = children[filsCourant].containsPoint(point);
                        break;
                    }
                    filsCourant++;
                }
            }
        }
        return regionContainingPoint;
    }

    /**
     * Print the number of child nodes
     */
    public void countMyChildren() {
        int nbChildren = 0;
        if (children[0] != null)
            nbChildren++;
        if (children[1] != null)
            nbChildren++;
        if (children[2] != null)
            nbChildren++;
        if (children[3] != null)
            nbChildren++;

        System.out.println("I have " + nbChildren + " children");
        System.out.println();
    }

    /**
     * Returns the number of child nodes
     * 
     * @return nbFils number of children
     */
    public int nbOfChildren() {
        int nbFils = 0;
        if (children[0] != null)
            nbFils++;
        if (children[1] != null)
            nbFils++;
        if (children[2] != null)
            nbFils++;
        if (children[3] != null)
            nbFils++;

        return nbFils;
    }

    /**
     * Test if this region intersects a triangle
     * 
     * @param triangle to test whether intersects this region or not
     * @return regionIntersectsTriangle boolean true if region intersects the given
     *         triangle, else return false
     */
    public Boolean intersectsTriangle(Triangle triangle) {
        // VARIABLES
        Boolean regionIntersectsTriangle = false;
        int coteRegion = 0;

        // PROCEDURES

        // Si au moins un des points du triangle appartient au rectangle
        if (regionContainsPoint(triangle.points()[0]) || regionContainsPoint(triangle.points()[1])
                || regionContainsPoint(triangle.points()[2])) {
            // System.out.println("Un des points du triangle appartient au triangle");
            regionIntersectsTriangle = true;
        }
        // Sinon vérifier si au moins 1 côté du triangle coupe 2 côtés du carrée en 2
        // points
        else {
            while (coteRegion < 4) {

                // Vérifier pour chaque coté de la region si un des côtés du triangle
                // l'intersecte
                if (triangle.segments()[0].intersectsSegment(segments[coteRegion])
                        || triangle.segments()[1].intersectsSegment(segments[coteRegion])
                        || triangle.segments()[2].intersectsSegment(segments[coteRegion])) {
                    // System.out.println("Un cote du triangle intersecte un cote du de la region");

                    // Tester si l'intersection n'est pas sur la ligne du cote
                    if (!segments[coteRegion].containsPoint(triangle.points()[0])
                            && !segments[coteRegion].containsPoint(triangle.points()[1])
                            && !segments[coteRegion].containsPoint(triangle.points()[2])) {
                        regionIntersectsTriangle = true;
                        break;
                    }
                }
                coteRegion++;
            }
        }

        // RETURN
        return regionIntersectsTriangle;
    }

    public Triangle RecherchePointTriangle(Point<Float> point) {

        // VARIABLES
        int triangleCourant = 0;
        Triangle triangleContenantPoint = null;

        // PROCEDURES

        // Collecte the smallest region where the point belongs to
        QuadTree region = containsPoint(point);
        region.printCoords();

        // Loop through all triangles that this region intersects and find the one that
        // contains the point
        while (triangleCourant < region.intersectedTriangles.size()) {
            if (region.intersectedTriangles.get(triangleCourant).testTriangleContainsPoint(point)) {
                triangleContenantPoint = region.intersectedTriangles.get(triangleCourant);
                break;
            }
            triangleCourant++;
        }
        return triangleContenantPoint;
    }

    /**
     * Count number of intersections between the triangles and the region
     * 
     * @param listeSurfaces list of surface polygons
     * @return nbIntersections number of intersections between the list of
     *         trianglles and the region
     */
    public int countNbIntersectionsRegionTriangle(ArrayList<PolygonSurface> listeSurfaces) {

        // VARIABLES
        int nbIntersections = 0;

        // PROCEDURES

        // Loop through all polygons
        for (int indPoly = 0; indPoly < listeSurfaces.size(); indPoly++) {
            // Loop through all triangles belonging to the current polygon
            for (int indTriangle = 0; indTriangle < listeSurfaces.get(indPoly).listOfTriangles()
                    .size(); indTriangle++) {
                // Test whether the region intersects the current triangle
                if (intersectsTriangle(listeSurfaces.get(indPoly).listOfTriangles().get(indTriangle))) {
                    nbIntersections++;
                }
            }
        }
        return nbIntersections;
    }

    /**
     * Retrieve the list of triangle that intersect the region
     * 
     * @param listeSurfaces list of surface polygons
     * @return listOfTriangles list of triangles that intersect the region
     */
    public ArrayList<Triangle> listOfIntersectedTriangles(ArrayList<PolygonSurface> listeSurfaces) {

        // VARIABLES
        ArrayList<Triangle> listOfTriangles = new ArrayList<Triangle>();

        // PROCEDURES
        for (int indPoly = 0; indPoly < listeSurfaces.size(); indPoly++) {
            for (int indTriangle = 0; indTriangle < listeSurfaces.get(indPoly).listOfTriangles()
                    .size(); indTriangle++) {
                if (intersectsTriangle(listeSurfaces.get(indPoly).listOfTriangles().get(indTriangle))) {
                    listOfTriangles.add(listeSurfaces.get(indPoly).listOfTriangles().get(indTriangle));
                }
            }
        }
        return listOfTriangles;
    }

    /**
     * Generates an array of x coordinates from the 4 vertices that make up the
     * region
     * 
     * @return xCoords array of x coordinates of the 4 vertices that make up the
     *         region
     */
    public int[] xCoords() {

        // TODO : modify the *4 so that the size will be generic to any window size
        int xP1 = Math.round(points[0].x() * 10 * 4);
        int xP2 = Math.round(points[1].x() * 10 * 4);
        int xP3 = Math.round(points[2].x() * 10 * 4);
        int xP4 = Math.round(points[3].x() * 10 * 4);

        int[] xCoords = { xP1, xP2, xP3, xP4 };
        return xCoords;
    }

    /**
     * Generates an array of y coordinates from the 4 vertices that make up the
     * region
     * 
     * @return yCoords array of y coordinates of the 4 vertices that make up the
     *         region
     */
    public int[] yCoords() {
        // TODO : modify the *4 so that the size will be generic to any window size
        int yP1 = Math.round((10 - points[0].y()) * 10 * 4);
        int yP2 = Math.round((10 - points[1].y()) * 10 * 4);
        int yP3 = Math.round((10 - points[2].y()) * 10 * 4);
        int yP4 = Math.round((10 - points[3].y()) * 10 * 4);
        int[] yCoords = { yP1, yP2, yP3, yP4 };
        return yCoords;
    }

    /**
     * Test if a point is inside a region
     * 
     * @param pt to test whether is inside region
     * @return boolean true if point is inside region else otherwise
     */
    public Boolean regionContainsPoint(Point<Float> pt) {
        return (pt.x() > points[0].x() && pt.x() < points[1].x() && pt.y() > points[0].y() && pt.y() < points[2].y());
    }

    /**
     * Test if a point is inside or on the border of a region
     * 
     * @param pt to test whether is inside or on the border of a region
     * @return boolean true if point is inside or on the border of a region else
     *         otherwise
     */
    public Boolean regionHasPoint(Point<Float> pt) {
        return (pt.x() >= points[0].x() && pt.x() <= points[1].x() && pt.y() >= points[0].y()
                && pt.y() <= points[2].y());
    }

    /**
     * Print x and y coordinates of the region
     */
    public void printCoords() {
        System.out.println("Coordonées: (" + points[0].x() + "," + points[0].y() + ")" + " (" + points[1].x() + ","
                + points[1].y() + ")" + " " + "(" + points[2].x() + "," + points[2].y() + ")" + " " + "("
                + points[3].x() + "," + points[3].y() + ")" + " ");
    }

}
