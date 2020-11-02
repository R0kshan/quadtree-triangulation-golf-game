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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//import javafx.scene.layout.Border;
//import javafx.stage.FileChooser;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GameInterface class
 * 
 * @author RoxaneKM
 */
public class GameInterface extends JFrame {

    // MEMBERS --------------------------------------------------------------

    // Game elements

    /**
     * List of surface polygon
     */
    ArrayList<PolygonSurface> listOfSurfacePolygons = new ArrayList<PolygonSurface>();

    /**
     * List of surface lines
     */
    ArrayList<Integer> listOfSurfaceLines = new ArrayList<Integer>();

    /**
     * List of parameters (contains the number of "tracés" and the "par")
     */
    ArrayList<Integer> listOfParameters = new ArrayList<Integer>();

    /**
     * Quad tree for cutting the ground into regions
     */
    QuadTree quadTree = new QuadTree();

    /**
     * Starting point of the golf ball
     */
    Point<Float> startingPoint = new Point<Float>();

    /**
     * Ending point of the golf ball
     */
    Point<Float> endingPoint = new Point<Float>();

    /**
     * Golf ball
     */
    GolfBall golfBall;

    // Configuration members

    /**
     * Path from user directory to program
     */
    String systemPath = System.getProperty("user.dir");

    /**
     * Path to default golf ground
     */
    String path = systemPath + "/src/main/resources/ground1.txt";

    // Swing objects

    /**
     * Golf ground pannel
     */
    GolfGround golfGroudPannel;

    /**
     * Limit of number of triangles intersections per region
     */
    JTextField limitTriangleIntersections;

    /**
     * Input fields for point's x coordinate
     */
    JTextField inputXCoord;

    /**
     * Input fields for point's y coordinate
     */
    JTextField inputYCoord;

    /**
     * Input fields for line1 starting x point coordinate
     */
    JTextField inputLine1X1;

    /**
     * Input fields for line1 starting y point coordinate
     */
    JTextField inputLine1Y1;

    /**
     * Input fields for line1 ending x point coordinate
     */
    JTextField inputLine1X2;

    /**
     * Input fields for line1 ending y point coordinate
     */
    JTextField inputLine1Y2;

    /**
     * Input fields for line2 starting x point coordinate
     */
    JTextField inputLine2X1;

    /**
     * Input fields for line2 starting y point coordinate
     */
    JTextField inputLine2Y1;

    /**
     * Input fields for line2 ending x point coordinate
     */
    JTextField inputLine2X2;

    /**
     * Input fields for line2 ending y point coordinate
     */
    JTextField inputLine2Y2;

    /**
     * x coordinate of golf ball target
     */
    JTextField inputXCoordTargetGolfBall;

    /**
     * y coordinate of golf ball target
     */
    JTextField inputYCoordTargetGolfBall;

    // CONSTRUCTORS --------------------------------------------------------

    /**
     * GameInterface constructor
     */
    public GameInterface() {
        System.out.println("System path: " + systemPath);
        setTitle("Golf game");
        initInterface();
        setLocationRelativeTo(null); // center the window to the middle of the screen
        setVisible(true);

        // Initialise the default file
        loadGameFile(path, listOfSurfacePolygons, listOfSurfaceLines, startingPoint, endingPoint, listOfParameters);

        // Build the quad tree from the golf ground loaded from the game file
        quadTree.build();
        System.out.println("End of quad tree building");
    }

    // INTERFACE INITIALISATION METHODS
    // -----------------------------------------------------

    /**
     * Reset the game by empty all game objects and resetting the golf ground pannel
     */
    public void resetGame() {
        listOfSurfacePolygons = new ArrayList<PolygonSurface>();
        listOfSurfaceLines = new ArrayList<Integer>();
        listOfParameters = new ArrayList<Integer>();
        quadTree.reset();
        golfGroudPannel.reset();
        golfGroudPannel.repaint();
    }

    /**
     * Initialise the interface
     */
    public void initInterface() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initBarMenu();
        placementContenu();
        pack();
    }

    /**
     * Content placement (JPanels, JButtons etc...)
     */
    private void placementContenu() {

        // General placement
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new FlowLayout());

        // Create a tab pannel
        JTabbedPane tabbedPane = new JTabbedPane();

        // Game commands pannel
        JPanel panelCommands = new JPanel();
        panelCommands.setLayout(new BoxLayout(panelCommands, BoxLayout.PAGE_AXIS));

        // Buttons for function testing
        JButton btnTriangulateGround = new JButton("GolfGroundTriangulation()");
        btnTriangulateGround.addActionListener(new TriangulationActionListener());

        JButton btnBuildQT = new JButton("Build QuadTree()");
        btnBuildQT.addActionListener(new BuildQTActionListener());

        JButton btnFindPointRegion = new JButton("RechercherPointRegion()");
        btnFindPointRegion.addActionListener(new FindPointRegionActionListener());

        JButton btnCalcLineCoeffs = new JButton("CalcLineCoefficients()");
        btnCalcLineCoeffs.addActionListener(new CalcLineCoeffsActionListener());

        JButton btnTest2SegmentsIntersection = new JButton("TestIntersection2Segments()");
        btnTest2SegmentsIntersection.addActionListener(new Test2SegmentsIntersectionActionListener());

        JButton btnTestRegionIntersectsTriangle = new JButton("IntersectsTriangle()");
        btnTestRegionIntersectsTriangle.addActionListener(new TestRegionIntersectsTriangleActionListener());

        JButton btnTestTriangleContainsPoint = new JButton("TestTriangleContainsPoint()");
        btnTestTriangleContainsPoint.addActionListener(new TestTriangleContientPointActionListener());

        JButton btnFindPointTriangle = new JButton("FindPointTriangle()");
        btnFindPointTriangle.addActionListener(new FindPointTriangleActionListener());

        JButton btnCalcGolfBallLandingPos = new JButton("CalcGolfBallLandingPos()");
        btnCalcGolfBallLandingPos.addActionListener(new CalcGolfBallLandingPosActionListener());

        JButton btnCalcGolfBallStartingSpot = new JButton("CalcGolfBallStartingSpot()");
        btnCalcGolfBallStartingSpot.addActionListener(new CalcGolfBallStartingSpotActionListener());
        btnCalcGolfBallStartingSpot.setEnabled(false);

        JButton btnCalcScore = new JButton("CalculateScore()");
        btnCalcScore.addActionListener(new CalcScoreActionListener());
        btnCalcScore.setEnabled(false);

        JLabel labelInputXCoord = new JLabel("x: ");
        inputXCoord = new JTextField("3.5");
        JLabel labelInputPointY = new JLabel("y: ");
        inputYCoord = new JTextField("1.5");
        JPanel panelFindPointRegion = new JPanel();
        panelFindPointRegion.setLayout(new FlowLayout());

        panelFindPointRegion.add(labelInputXCoord);
        panelFindPointRegion.add(inputXCoord);
        panelFindPointRegion.add(labelInputPointY);
        panelFindPointRegion.add(inputYCoord);

        JLabel labelLimitIntersections = new JLabel("Limit intersections t: ");
        JPanel panelLimitIntersections = new JPanel();
        panelLimitIntersections.setLayout(new FlowLayout());

        limitTriangleIntersections = new JTextField(2);
        JButton btnLimitTriangleIntersections = new JButton("Modify");
        btnLimitTriangleIntersections.addActionListener(new ModifyLimitTriangleIntersectionsActionListener());
        panelLimitIntersections.add(limitTriangleIntersections);
        panelLimitIntersections.add(btnLimitTriangleIntersections);

        // Line 1 coordinates
        JPanel panelCoordL1_P1 = new JPanel(new FlowLayout());
        JPanel panelCoordL1_P2 = new JPanel(new FlowLayout());

        JLabel line1 = new JLabel("Line 1 coordinates");

        JLabel l1X1 = new JLabel("X1: ");
        inputLine1X1 = new JTextField(2);
        JLabel l1Y1 = new JLabel("Y1: ");
        inputLine1Y1 = new JTextField(2);

        JLabel l1X2 = new JLabel("X2: ");
        inputLine1X2 = new JTextField(2);
        JLabel l1Y2 = new JLabel("Y2: ");
        inputLine1Y2 = new JTextField(2);

        panelCoordL1_P1.add(l1X1);
        panelCoordL1_P1.add(inputLine1X1);
        panelCoordL1_P1.add(l1Y1);
        panelCoordL1_P1.add(inputLine1Y1);

        panelCoordL1_P2.add(l1X2);
        panelCoordL1_P2.add(inputLine1X2);
        panelCoordL1_P2.add(l1Y2);
        panelCoordL1_P2.add(inputLine1Y2);

        // Line 2 coordinates
        JPanel panelCoordL2_P1 = new JPanel(new FlowLayout());
        JPanel panelCoordL2_P2 = new JPanel(new FlowLayout());

        JLabel line2 = new JLabel("Line 2 coordinates");
        JLabel l2X1 = new JLabel("X1: ");
        inputLine2X1 = new JTextField(2);
        JLabel l2Y1 = new JLabel("Y1: ");
        inputLine2Y1 = new JTextField(2);

        JLabel l2X2 = new JLabel("X2: ");
        inputLine2X2 = new JTextField(2);
        JLabel l2Y2 = new JLabel("Y2: ");
        inputLine2Y2 = new JTextField(2);

        panelCoordL2_P1.add(l2X1);
        panelCoordL2_P1.add(inputLine2X1);
        panelCoordL2_P1.add(l2Y1);
        panelCoordL2_P1.add(inputLine2Y1);

        panelCoordL2_P2.add(l2X2);
        panelCoordL2_P2.add(inputLine2X2);
        panelCoordL2_P2.add(l2Y2);
        panelCoordL2_P2.add(inputLine2Y2);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ClearActionListener());

        // Add the GUI elements to the commands pannel
        panelCommands.add(btnTriangulateGround);
        panelCommands.add(btnBuildQT);
        panelCommands.add(btnFindPointRegion);
        panelCommands.add(btnCalcLineCoeffs);
        panelCommands.add(btnTest2SegmentsIntersection);
        panelCommands.add(btnTestRegionIntersectsTriangle);
        panelCommands.add(btnTestTriangleContainsPoint);
        panelCommands.add(btnFindPointTriangle);
        panelCommands.add(btnCalcGolfBallLandingPos);
        panelCommands.add(btnCalcGolfBallStartingSpot);
        panelCommands.add(btnCalcScore);
        panelCommands.add(panelFindPointRegion);
        panelCommands.add(btnClear);
        panelCommands.add(labelLimitIntersections);
        panelCommands.add(panelLimitIntersections);

        panelCommands.add(line1);
        panelCommands.add(panelCoordL1_P1);
        panelCommands.add(panelCoordL1_P2);
        panelCommands.add(line2);
        panelCommands.add(panelCoordL2_P1);
        panelCommands.add(panelCoordL2_P2);

        // Game commands pannel
        JPanel panelGameCommands = new JPanel();
        panelGameCommands.setLayout(new BoxLayout(panelGameCommands, BoxLayout.PAGE_AXIS));

        JLabel labelGolfBallTargetPos = new JLabel("Cible golfBall: ");
        JLabel labelGolfBallTargetXCoord = new JLabel("x: ");
        inputXCoordTargetGolfBall = new JTextField(2);
        JLabel labelGolfBallTargetYCoord = new JLabel("y: ");
        inputYCoordTargetGolfBall = new JTextField(2);

        JButton btnHitGolfBall = new JButton("Taper golfBall!");
        btnHitGolfBall.addActionListener(new HitGolfBallActionListener());

        JPanel panelGolfBallCoords = new JPanel(new FlowLayout());
        panelGolfBallCoords.add(labelGolfBallTargetPos);
        panelGolfBallCoords.add(labelGolfBallTargetXCoord);
        panelGolfBallCoords.add(inputXCoordTargetGolfBall);
        panelGolfBallCoords.add(labelGolfBallTargetYCoord);
        panelGolfBallCoords.add(inputYCoordTargetGolfBall);
        panelGolfBallCoords.add(btnHitGolfBall);

        panelGameCommands.add(panelGolfBallCoords);

        // Create JPanel representing the golf ground
        golfGroudPannel = new GolfGround();

        // Set list of polygons read from file to the golf ground to be drawn
        golfGroudPannel.setListOfPolygons(listOfSurfacePolygons);

        // Create a new QuadTree
        quadTree = new QuadTree(10f, 4, 0.0f, 0.0f);

        // We set the surface polygons list to the quad tree but we don't call the
        // build() function yet because
        // we haven't yet loaded the game file
        quadTree.setSurfaceList(listOfSurfacePolygons);

        // Set the quad tree and golf ball to the ground pannel to be drawn graphically
        golfGroudPannel.setQuadTree(quadTree);
        golfBall = new GolfBall(8f, 7f, 10f, Color.YELLOW);
        golfGroudPannel.setGolfBall(golfBall);

        // Define the size of the terrain
        golfGroudPannel.setPreferredSize(new Dimension(401, 401));
        golfGroudPannel.setBackground(Color.WHITE);

        // Add the pannel commands and the ground pannel to the main pannel
        tabbedPane.add("Test des fonctions", panelCommands);
        tabbedPane.add("Commandes du jeu", panelGameCommands);

        panelGeneral.add(tabbedPane);
        panelGeneral.add(golfGroudPannel);

        getContentPane().add(panelGeneral);
    }

    /**
     * Initialise bar menu interface
     */
    private void initBarMenu() {

        // Creation des elements de la bar de menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFiles = new JMenu("Files");
        menuFiles.setMnemonic(KeyEvent.VK_F);

        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuItemNewGame = new JMenuItem("New game");
        menuItemNewGame.addActionListener(new NouveauActionListener());
        JMenuItem menuItemOpen = new JMenuItem("Open");
        menuItemOpen.addActionListener(new OpenActionListener());

        JMenuItem menuItemQuit = new JMenuItem("Quit");
        menuItemQuit.setMnemonic(KeyEvent.VK_E);
        menuItemQuit.setToolTipText("Quit application");
        menuItemQuit.addActionListener(new CloseActionListener());

        // 'Files' menu
        menuFiles.add(menuItemNewGame);
        menuFiles.add(menuItemOpen);
        menuFiles.add(menuItemQuit);

        // Menu bar
        menuBar.add(menuFiles);
        menuBar.add(menuHelp);

        // Show bar menu
        setJMenuBar(menuBar);
    }

    // ACTION LISTENERS
    // ---------------------------------------------------------------

    /**
     * Action listener to close applications
     */
    class CloseActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Action listener to reset the game
     */
    class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            golfGroudPannel.setStateDrawQuarterCuts(false);
            golfGroudPannel.setStateDrawFirstTriangle(false);
            golfGroudPannel.setStateDrawTriangles(false);
            golfGroudPannel.setStateDrawRegionContainingPoint(false);
            golfGroudPannel.setStateDrawTriangleContainingPoint(false);
            golfGroudPannel.setStateDrawPoint(false);

            golfGroudPannel.repaint();
        }
    }

    /**
     * Action listener to open game file
     */
    class OpenActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // VARIABLES
            JFileChooser fileChooser = new JFileChooser();
            File pathToLastFile;

            // PROCEDURES

            // Add the filter to find only files with the extension .txt
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            int returnVal = fileChooser.showOpenDialog(GameInterface.this);

            // If the file has been selected than try to read it
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                pathToLastFile = fileChooser.getSelectedFile();

                loadGameFile(pathToLastFile.toPath().toString(), listOfSurfacePolygons, listOfSurfaceLines,
                        startingPoint, endingPoint, listOfParameters);
                System.out.println("List of polygon's size: " + listOfSurfacePolygons.size());
                quadTree.build();
                System.out.println("End of QT construction");

                System.out.println("Game file read");
                golfGroudPannel.repaint();

            }
            // Else if no file has been chosen then do nothing
            else {
                // JOptionPane.showMessageDialog(null,"No file has been selected.");
            }
        }
    }

    /**
     * Action listener to start a new game
     */
    class NouveauActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
            System.out.println("Game has been reset.");
        }
    }

    /**
     * Action listener to draw the triangulation on the golf ground
     */
    class TriangulationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (golfGroudPannel.stateDrawTriangles() == true) {
                golfGroudPannel.setStateDrawTriangles(false);
            } else {
                golfGroudPannel.setStateDrawTriangles(true);
            }
            golfGroudPannel.repaint();
        }
    }

    /**
     * Action listener to draw the QuadTree that has been built
     */
    class BuildQTActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (golfGroudPannel.stateDrawQuarterCuts() == true) {
                golfGroudPannel.setStateDrawQuarterCuts(false);
            } else {
                golfGroudPannel.setStateDrawQuarterCuts(true);
            }
            golfGroudPannel.repaint();
        }
    }

    /**
     * Action listener to find whether the point whose coordinates has been
     * specified in the input fields is in one of the quad tree's region
     */
    class FindPointRegionActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!inputXCoord.getText().isEmpty() && !inputYCoord.getText().isEmpty()) {

                // Get the point's coordinates from input fields
                float x = Float.parseFloat(inputXCoord.getText());
                float y = 10 - Float.parseFloat(inputYCoord.getText());

                Point<Float> p = new Point(x, y);
                golfGroudPannel.setStateDrawPoint(true);
                golfGroudPannel.setPoint(p);

                // Call the function to search for the region containing the given point
                golfGroudPannel.setRegionContainingPoint(quadTree.containsPoint(new Point(x, y)));
                golfGroudPannel.setStateDrawRegionContainingPoint(true);
                golfGroudPannel.repaint();

                System.out.println("I have " + golfGroudPannel.regionContainingPoint().intersectedTriangles().size()
                        + " intersecting triangles");

                if (golfGroudPannel.regionContainingPoint() == null) {
                    JOptionPane.showMessageDialog(null,
                            "The point's coordinates are out of the golf ground's dimensions.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The x and y coordinates are empty. No point to search for.");
            }

        }
    }

    /**
     * Action listener to find whether a triangle contains the given point
     */
    class FindPointTriangleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!inputXCoord.getText().isEmpty() && !inputYCoord.getText().isEmpty()) {
                // Get the point's coordinates from input fields
                float x = Float.parseFloat(inputXCoord.getText());
                float y = 10 - Float.parseFloat(inputYCoord.getText());

                Point<Float> p = new Point(x, y);
                golfGroudPannel.setStateDrawPoint(true);
                golfGroudPannel.setPoint(p);

                // Call the function to search for the triangle containing the given point
                golfGroudPannel.setTriangleContainingPoint(quadTree.RecherchePointTriangle(new Point<Float>(x, y)));
                if (golfGroudPannel.triangleContainingPoint() != null) {
                    golfGroudPannel.triangleContainingPoint().printCoords();
                    golfGroudPannel.setStateDrawTriangleContainingPoint(true);
                } else {
                    System.out.println(
                            "No triangle has been found (there might have been a problem during triangulation.");
                }

                golfGroudPannel.repaint();

                if (golfGroudPannel.triangleContainingPoint() == null) {
                    JOptionPane.showMessageDialog(null, "No triangle contains this point.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The x and y coordinates are empty. No point to search for.");
            }
        }
    }

    /**
     * Action listener to modify the limit number of triangles intersecting a region
     */
    class ModifyLimitTriangleIntersectionsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!limitTriangleIntersections.getText().isEmpty()) {

                // VARIABLES
                int limit = Integer.parseInt(limitTriangleIntersections.getText());

                // PROCEDURES
                if (limit > 2) {
                    quadTree.setLimiteIntersectionsTriangles(Integer.parseInt(limitTriangleIntersections.getText()));
                    if (listOfSurfacePolygons.size() != 0) {
                        quadTree.build();
                        golfGroudPannel.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The limit can't be <= 2");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The field is empty.");
            }
        }
    }

    /**
     * Action listener to calculate the coefficients of a given line
     */
    class CalcLineCoeffsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!inputLine1X1.getText().isEmpty() && !inputLine1Y1.getText().isEmpty()
                    && !inputLine1X2.getText().isEmpty() && !inputLine1Y2.getText().isEmpty()) {

                // Get the line's starting and ending point coordinates from the input field
                float l1X1 = Float.parseFloat(inputLine1X1.getText());
                float l1Y1 = 10 - Float.parseFloat(inputLine1Y1.getText());
                float l1X2 = Float.parseFloat(inputLine1X2.getText());
                float l1Y2 = 10 - Float.parseFloat(inputLine1Y2.getText());

                // Print the coefficients of the line
                Line d1 = new Line(new Point<Float>(l1X1, l1Y1), new Point<Float>(l1X2, l1Y2));
                JOptionPane.showMessageDialog(null, "Line's coefficients : " + d1.a() + " " + d1.b() + " " + d1.c());
            } else {
                JOptionPane.showMessageDialog(null, "The input fields are empty.");
            }

        }
    }

    /**
     * Action listener to test whether 2 segments intersect
     */
    class Test2SegmentsIntersectionActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!inputLine1X1.getText().isEmpty() && !inputLine1Y1.getText().isEmpty()
                    && !inputLine1X2.getText().isEmpty() && !inputLine1Y2.getText().isEmpty()) {

                // Get the segments's starting and ending point coordinates from the input field
                float l1X1 = Float.parseFloat(inputLine1X1.getText());
                float l1Y1 = 10 - Float.parseFloat(inputLine1Y1.getText());
                float l1X2 = Float.parseFloat(inputLine1X2.getText());
                float l1Y2 = 10 - Float.parseFloat(inputLine1Y2.getText());

                float l2X1 = Float.parseFloat(inputLine2X1.getText());
                float l2Y1 = 10 - Float.parseFloat(inputLine2Y1.getText());
                float l2X2 = Float.parseFloat(inputLine2X2.getText());
                float l2Y2 = 10 - Float.parseFloat(inputLine2Y2.getText());

                // Create 2 segments from the points specified in input field
                Segment d1 = new Segment(new Point<Float>(l1X1, l1Y1), new Point<Float>(l1X2, l1Y2));
                Segment d2 = new Segment(new Point<Float>(l2X1, l2Y1), new Point<Float>(l2X2, l2Y2));

                // Get intersection point between the 2 segments
                Point<Float> intersectionPoint = d1.intersectionPoint(d2);

                // If the intersection point is null then it means that there's no intersection
                // between the 2 segments
                if (intersectionPoint != null) {
                    JOptionPane.showMessageDialog(null, "There's no intersection point between the 2 segments.");
                } else {
                    JOptionPane.showMessageDialog(null, "There's an intersection point at : " + intersectionPoint.x()
                            + "," + intersectionPoint.y());
                }
            } else {
                JOptionPane.showMessageDialog(null, "The field coordinates are empty");
            }
        }
    }

    /**
     * Action listener to whether the number of triangles intersecting each region
     * is correct. (TODO)
     */
    class TestRegionIntersectsTriangleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,
                    "To test, click on GolfGroundTriangulation() than on BuildQuadTree() to see if the number of triangles intersecting each region is correct.");
        }
    }

    /**
     * Action listener to check whether the first triangle create from the golf
     * ground contains the point whose coordinates are specified in the x and y
     * input fieds
     */
    class TestTriangleContientPointActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!inputXCoord.getText().isEmpty() && !inputYCoord.getText().isEmpty()) {

                // Get the point's coordinates from the input field
                float x = Float.parseFloat(inputXCoord.getText());
                float y = 10 - Float.parseFloat(inputYCoord.getText());

                Point<Float> pt = new Point<Float>(x, y);
                golfGroudPannel.setPoint(pt);
                golfGroudPannel.setStateDrawPoint(true);

                golfGroudPannel.setStateDrawFirstTriangle(true);
                golfGroudPannel.repaint();

                if (listOfSurfacePolygons.get(0).listOfTriangles().get(0).testTriangleContainsPoint(new Point(x, y))) {
                    JOptionPane.showMessageDialog(null, "The first triangle of the golf ground contains the point.");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "The point does not belong to the first triangle of the golf ground.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The x et y coordinates are empty. No point to look for.");
            }

        }
    }

    /**
     * Action listener to calculate the golf ball's next starting position (TODO)
     */
    class CalcGolfBallStartingSpotActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!inputXCoord.getText().isEmpty() && !inputYCoord.getText().isEmpty()) {

                // Get the point's coordinates from the input field
                float x = Float.parseFloat(inputXCoord.getText());
                float y = 10 - Float.parseFloat(inputYCoord.getText());

                Point<Float> p = new Point<Float>(x, y);
                golfGroudPannel.setStateDrawPoint(true);
                golfGroudPannel.setPoint(p);
            } else {
                JOptionPane.showMessageDialog(null, "The x et y coordinates are empty. No point to look for.");
            }
        }
    }

    /**
     * Action listener to calculate the golf ball's landing position
     */
    class CalcGolfBallLandingPosActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!inputXCoord.getText().isEmpty() && !inputYCoord.getText().isEmpty()) {
                // Get the point's coordinates from the input field
                float x = Float.parseFloat(inputXCoord.getText());
                float y = 10 - Float.parseFloat(inputYCoord.getText());

                Point<Float> pt = new Point<Float>(x, y);
                golfGroudPannel.setStateDrawPoint(true);
                golfGroudPannel.setPoint(pt);

                golfBall.calculateNextLandingPoint(new Point<Float>(golfBall.xCoord(), golfBall.yCoord()),
                        new Point<Float>(x, y));
                golfGroudPannel.setGolfBall(golfBall);
                golfGroudPannel.repaint();

                JOptionPane.showMessageDialog(null, "Golf ball's landing point : (" + golfBall.landingPoint().x()
                        + " , " + golfBall.landingPoint().y() + ")");
            } else {
                JOptionPane.showMessageDialog(null, "The x et y coordinates are empty. No point to look for.");
            }
        }
    }

    /**
     * Action listener to calculate to player's score
     */
    class CalcScoreActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    /**
     * Action listener to hit the golf ball
     */
    class HitGolfBallActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get the point's coordinates from input fields
            if (!inputXCoordTargetGolfBall.getText().isEmpty() && !inputYCoordTargetGolfBall.getText().isEmpty()) {

                System.out.println("Golf ball's starting point : " + golfBall.xCoord() + " " + golfBall.yCoord());
                float x = Float.parseFloat(inputXCoordTargetGolfBall.getText());
                float y = 10 - Float.parseFloat(inputYCoordTargetGolfBall.getText());
                System.out.println("Golf ball's targer point : " + x + " " + y);

                golfBall.calculateNextLandingPoint(new Point(golfBall.xCoord(), golfBall.yCoord()), new Point(x, y));
                System.out.println("Golf ball's landing point: " + golfBall.landingPoint().x() + " "
                        + golfBall.landingPoint().y());
                golfGroudPannel.setGolfBall(golfBall);
                golfGroudPannel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "The x et y coordinates are empty. No point to look for.");
            }
        }
    }

    // GAME METHODS
    // ----------------------------------------------------------------------------

    /**
     * Read the game file and get the data to generate the golf ground
     * 
     * @param path             path of the game file
     * @param listOfPolygons   (initially empty) list of polygons
     * @param surfaceLines     (initially empty) list of surfaces lines (not to be
     *                         mistake for the lines that make up the polygons)
     * @param startingPoint    starting point for the golf ball
     * @param endingPoint      ending point of the golf ball (when it arrives at
     *                         this point it means the game is over)
     * @param listOfParameters list containing the game parameters
     */
    public static void loadGameFile(String path, ArrayList<PolygonSurface> listOfPolygons,
            ArrayList<Integer> surfaceLines, Point<Float> startingPoint, Point<Float> endingPoint,
            ArrayList<Integer> listOfParameters) {

        // VARIABLES
        int nbSurface = 0; // Number of polygon surfaces
        String strCurrentLine; // String containing the current line in game text file
        int nCurrentLine = 0; // Index of the current line in game text file
        String strLastLine = ""; // String containing the last line in the text file

        // PROCEDURES

        // Open and read the file's content
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            // Get the number of polygon surfaces from the first line
            nbSurface = Integer.parseInt(in.readLine());

            // Read the lines according to the number of surfaces indicate in the first line
            // -> nbSurface
            while (nCurrentLine < nbSurface) {

                ArrayList<Point<Float>> listOfPolygonVertices = new ArrayList<Point<Float>>();

                // Put the current line in a string
                strCurrentLine = in.readLine();

                // Split the string using comma's as seperator
                for (String coordonnees : strCurrentLine.split("\\),\\(")) {

                    // Remove all the openning and closing parenthesis of each split string
                    coordonnees = coordonnees.replaceAll("\\(", "");
                    coordonnees = coordonnees.replaceAll("\\)", "");

                    // Seperate into 2 each coordinate "line"
                    ArrayList<String> coordsPoint = new ArrayList<String>(Arrays.asList(coordonnees.split(",")));

                    // Store the coordinates in a Point
                    if (coordsPoint.size() >= 2) {
                        Point<Float> pt = new Point<Float>(Float.parseFloat(coordsPoint.get(0)),
                                Float.parseFloat(coordsPoint.get(1)));

                        // Store the point in a list of vertices (composing the polygon)
                        listOfPolygonVertices.add(pt);
                    }

                    // If the line contains a letter (represents color of the polygon) it means we
                    // haven a polygon
                    if (coordsPoint.size() == 3) {
                        // Create the polygon
                        PolygonSurface polygon = new PolygonSurface(listOfPolygonVertices, coordsPoint.get(2));

                        // Store the newly created polygon into a list of polygons
                        listOfPolygons.add(polygon);
                    }
                }

                // Increment the current line's index for the next iteration
                nCurrentLine++;
            }

            // Get and store the number of "tracés"
            listOfParameters.add(Integer.parseInt(in.readLine()));

            // Get the last line of the file containing the index of surfaces lines, the
            // starting and ending point as well as the "par"
            strLastLine = in.readLine();

            // Loop through last line
            ArrayList<String> sectionsLastLine = new ArrayList<String>(Arrays.asList(strLastLine.split("\\(")));

            // Get the index of the surface line
            for (String ligne : sectionsLastLine.get(0).split(",")) {
                surfaceLines.add(Integer.parseInt(ligne));
            }

            // Get the coordinates of the starting point position
            ArrayList<String> coordStartingPoint = new ArrayList<String>(
                    Arrays.asList(sectionsLastLine.get(1).split(",")));

            // Remove the closing parenthesis at the end of the Y coordinate
            String coordY = coordStartingPoint.get(1);
            coordY = coordY.replaceAll("\\)", "");

            // Add the coordinates to the starting point
            startingPoint.setX(Float.parseFloat(coordStartingPoint.get(0)));
            startingPoint.setY(Float.parseFloat(coordY));

            // Get the coordinates of the ending point position
            ArrayList<String> coordPointArr = new ArrayList<String>(Arrays.asList(sectionsLastLine.get(2).split(",")));

            // Remove the closing parenthesis at the end of the Y coordinate
            String coordYArr = coordPointArr.get(1);
            coordYArr = coordYArr.replaceAll("\\)", "");

            // Add the coordinates to the ending point
            endingPoint.setX(Float.parseFloat(coordPointArr.get(0)));
            endingPoint.setY(Float.parseFloat(coordYArr));

            // Get the "par" value
            listOfParameters.add(Integer.parseInt(coordPointArr.get(2)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
