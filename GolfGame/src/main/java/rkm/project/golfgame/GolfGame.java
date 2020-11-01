/*******************************************************************************
 *
 * Contributors:
 *     Røkshan              -   Initial implementation of classes: GolfGame, GameInterface, GolfGround, QuadTree, Triangle, PolygonSurface, GolfBall (graphical part)
 *                          -   Code refactoring
 *                          -   Comments and variables translation to english
 *                          -   Doxygen documentation
 *     InesDt               -   Initial implementation of classes: GolfBall (movement calculation part), Line, Point, Segment
 *
 ******************************************************************************/
package rkm.project.golfgame;

/**
 * Golf game
 *
 * @author RoxaneKM
 */
public class GolfGame {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // Start game
        new GameInterface();
    }

}
