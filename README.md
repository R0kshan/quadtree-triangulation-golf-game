# Golf Game

This golf game project was given at the end of Bachelor of Computer Science  module **Algorithm and Data Structures**. The goal of the project is to implement a golf game, using necessarily the quad tree structure that we define in a way which is best suited to perform as effectively as possible the following operations of the golf game: identify the position (but also environment/surface) where the ball hit by the golfer falls. The construction rules were given to us in the instruction paper **[projet_jeu_de_gold_consigne.pdf](https://github.com/RoxaneKM/GolfGame/blob/master/projet_jeu_de_golf_consigne.pdf)**.

----

### Development time given

From the 24th October 2017 to 8th December 2017

----

### Prerequisites

Minimum Java version required: 1.7

----

### Known issues (PLEASE READ)

* When a game file is loaded, that program freezes if we try to open another game file (to test openning another game file via Files>Open, we have to uncomment lines 183 to 187 in class GameInterface.java).

----

### My futur developments on this project

* Solve all issues stated above
* Write unit tests for classes: Line, PolygonSurface, Triangle, Segment, QuadTree, GolfGame
* Remove all duplicate codes reported in CPD
* Remove all coding rules violation reported in PMD
* Develop a help window
* Implement the game rules (calculate score, and detect if player has won or lost), because at the moment only the technical (graphical interface, loading game file, quad tree etc..) part of the software has been developed.

----

### How to run the program

Clone this repository then once inside, simple run the command (works in both Linux and Windows):  

```
cd GolfGame/GolfGame
java -cp target/GolfGame-1.0-SNAPSHOT.jar rkm.project.golfgame.GolfGame
```

----

## Running all the unit tests

Either open the project in IntelliJ or once inside the folder GolfGame run the command:

```
mvn test
```

----

## Built With

This project was initially developed in Netbeans without any Software Project Management program, but for further development outside of the module, I converted this project into a Maven project.

* [Maven](https://maven.apache.org/)

----

## Authors

* [Røkshan](https://github.com/R0kshan) - *Initial work on classes graphical rendering, quad tree structure and triangulation on the following classes : GolfGame, GameInterface, GolfGround, QuadTree, Triangle, PolygonSurface, GolfBall (graphical part)*
* [InesDt](https://github.com/InesDt)  - *Initial work on mathematical calculations in the following classes : GolfBall (movement calculation), Line, Point, Segment*
