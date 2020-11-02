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
import java.util.ArrayList;

/**
 * Polygon interface class (represents all the polygons that make up the golf
 * ground)
 * 
 * @author RoxaneKM
 */

public class PolygonSurface {

    // MEMBERS -------------------------------------------------------------

    /**
     * List of float points
     */
    private ArrayList<Point<Float>> listOfVertices = new ArrayList<Point<Float>>();

    /**
     * List of triangles (generated from triangulation function)
     */
    private ArrayList<Triangle> listOfTriangles = new ArrayList<Triangle>();

    /**
     * Colour of the polygon's surface
     */
    private Color color;

    /**
     * Colour name
     */
    private String colorName;

    /**
     * Number of points composing polygon
     */
    private int nbPoints;

    // CONSTRUCTORS -------------------------------------------------------------

    /**
     * PolygonSurface constructor
     * 
     * @param listOfVertices list of vertices forming the polygon
     * @param color          colour of the surface polygon
     */
    public PolygonSurface(ArrayList<Point<Float>> listOfVertices, String color) {

        this.listOfVertices = listOfVertices;
        nbPoints = listOfVertices.size();

        // Determine the color of the surface (color codes V, C, B, S and J are
        // determined from game files)
        if (color.equals("V")) {
            this.color = new Color(0, 153, 76);
            colorName = color;
            // System.out.println("Vert");
        } else if (color.equals("C")) {
            this.color = new Color(128, 255, 0);
            colorName = color;
            // System.out.println("Vert clair");
        } else if (color.equals("B")) {
            this.color = new Color(81, 153, 255);
            colorName = color;
            // System.out.println("Bleu");
        } else if (color.equals("S")) {
            this.color = new Color(0, 102, 0);
            colorName = color;
            // System.out.println("Vert sapin");
        } else if (color.equals("J")) {
            this.color = new Color(255, 255, 153);
            colorName = color;
            // System.out.println("Beige");
        }
        golfGroundTriangulation();
    }

    // GETTERS

    /**
     * Retrieves an array list of triangles (generated from triangulation of the
     * polygon)
     * 
     * @return array list of triangles
     */
    public ArrayList<Triangle> listOfTriangles() {
        return listOfTriangles;
    }

    /**
     * Retrieves the number of vertices (points) composing the polygon
     * 
     * @return integer value of the number of points
     */
    public int nbPoints() {
        return nbPoints;
    }

    /**
     * Retrieves the color of the polygon surface
     * 
     * @return color of the polygon
     */
    public Color color() {
        return color;
    }

    // METHODS

    /**
     * Triangulate this surface polygon and generate the list of triangles composing
     * it
     */
    public void golfGroundTriangulation() {
        // VARIABLES
        ArrayList<Point<Float>> listOfVerticesCopy = new ArrayList<Point<Float>>(listOfVertices);
        Point<Float> p0, p1, p2;

        int indP0 = 0;
        int indP1 = indP0 + 1;
        int indP2 = indP1 + 1;

        // Check of the polygon contains more then 3 verices (if so then triangulate it)
        while (listOfVerticesCopy.size() > 3) {

            // Initialise the indexes of the 3 points of the triangle :

            // If the following index after P0 is > the size of the liste of vertices then
            // set P1 to 0
            if (indP0 + 1 > listOfVerticesCopy.size() - 1) {
                indP1 = 0;
            }
            // Else increment the index of P1 (by 1 according to P0)
            else
                indP1 = indP0 + 1;

            // If the index of P1 > the size of the list of vertices then set P2 to 0
            if (indP1 + 1 > listOfVerticesCopy.size() - 1) {
                indP2 = 0;
            }
            // Else increment the index of P2 (by 1 according to P1)
            else
                indP2 = indP1 + 1;

            // Get the vertices depending on their indexes
            p0 = listOfVerticesCopy.get(indP0);
            p1 = listOfVerticesCopy.get(indP1);
            p2 = listOfVerticesCopy.get(indP2);

            // Create a segment that links P0 to P2
            Segment seg = new Segment(p0, p2);

            // Create a boolean indicating whether the vertex is alone or not in its half
            // space
            Boolean p1Seul = false;

            // Collect the int value that indicated in which half space the point is
            int demiPlan = p1.halfSpace(seg);

            // Check if the point is in the upper half space
            if (demiPlan == 1) {
                // Check if the point is alone in it's half space
                if (pointSeulDansDemiPlan(seg, listOfVerticesCopy, demiPlan) == true) {
                    // The point is alone in the lower half space
                    p1Seul = true;
                }
            }

            // Check if the point is in the upper half space
            else if (demiPlan == 2) {
                // Check if the point is alone in it's half space
                if (pointSeulDansDemiPlan(seg, listOfVerticesCopy, demiPlan) == true) {
                    // The point is alone in the upper half space
                    p1Seul = true;
                }
            }
            // If the point is alone, add the triangle formed by P0, P1 and P2 to the list
            // of triangles
            if (p1Seul) {
                // Add the triangle formed by these 3 vertices to the list of triangles
                listOfTriangles.add(new Triangle(p0, p1, p2));

                // Remove the vertex from the liste of vertices
                listOfVerticesCopy.remove(p1);

                // After removing the vertex check that the current index isn't overflowing from
                // the list of vertices
                if (indP0 > listOfVerticesCopy.size() - 1) {
                    indP0 = 0;
                }
            }

            // Else if the vertex is not alone in the half space, go to next index
            else {
                // If the next index overflows from the size of the list then set P0 to 0
                if (indP0 + 1 > listOfVerticesCopy.size() - 1) {
                    indP0 = 0;
                }
                // Else increment current index P0 by 1
                else
                    indP0++;
            }
        }
        // Récupérer les points en fonction de leurs indices
        // Collect the vertices from their indexes
        p0 = listOfVerticesCopy.get(0);
        p1 = listOfVerticesCopy.get(1);
        p2 = listOfVerticesCopy.get(2);

        // Add the 3 remaining vertices to the list of triangles
        listOfTriangles.add(new Triangle(p0, p1, p2, color));
    }

    /**
     * Creates and returns an array of all the x coordinates from the list of
     * vertices
     * 
     * @return xCoords an array of all the x coordinates from the list of vertices
     */
    public int[] tabCoordsX() {

        // VARIABLES
        int[] xCoords = new int[listOfVertices.size()];

        // PROCEDURES
        for (int i = 0; i < listOfVertices.size(); i++) {
            xCoords[i] = Math.round(listOfVertices.get(i).x() * 10 * 4);
        }
        // RETURN
        return xCoords;
    }

    public int[] tabCoordsY() {

        // VARIABLES
        int[] yCoords = new int[listOfVertices.size()];

        // PROCEDURES
        for (int i = 0; i < listOfVertices.size(); i++) {

            float tmp = 10 - listOfVertices.get(i).y();
            int y = Math.round(tmp * 10 * 4);
            yCoords[i] = y;
        }
        return yCoords;
    }

    /**
     * Prints out all the coordinates of vertices in the list of vertices
     */
    public void afficherCoordsPoint() {
        for (int i = 0; i < listOfVertices.size(); i++) {
            System.out.println("(" + listOfVertices.get(i).x() + "," + listOfVertices.get(i).y() + ")");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Checks if there's more than 1 point in given half space
     * 
     * @param seg          segment
     * @param listOfPoints liste of points to check
     * @param halfSpace    integer representing the half space (1 for lower and 2
     *                     for upper)
     * @return true if there's no more than 1 point in the given half space, else
     *         return false
     */
    public Boolean pointSeulDansDemiPlan(Segment seg, ArrayList<Point<Float>> listOfPoints, int halfSpace) {
        int i = 0;
        boolean bool = true;
        int nbPoints = 0; // S'il y a plus de 1 points dans un demi renvoyer faux
        while (i < listOfPoints.size()) {
            // If we have found a point in a half space, increment the number of points in
            // the same half space
            if (listOfPoints.get(i).halfSpaceSeg(seg) == halfSpace) {
                nbPoints++;
            }
            // If there are more than 2 points in half space then return false
            if (nbPoints >= 2) {
                bool = false;
                break;
            }
            i++;
        }
        return bool;
    }

    /**
     * Check if there's at least one point in the given half space
     * 
     * @param seg          segment
     * @param listOfPoints list of points
     * @param halfSpace    integer representing the half space (1 for lower and 2
     *                     for upper)
     * @return true if there's at least 1 point in the given half space, else
     *         returns false
     */
    public Boolean auMoins1PointDemiPlan(Segment seg, ArrayList<Point<Float>> listOfPoints, int halfSpace) {
        int i = 0;
        boolean bool = false;

        while (i < listOfPoints.size()) {
            // Si trouvé un point dans le demi plan, incrémenter le compteur de points dans
            // le demi plan
            if (listOfPoints.get(i).halfSpace(seg) == halfSpace) {
                bool = true;
                break;
            }
            i++;
        }
        return bool;
    }

}
