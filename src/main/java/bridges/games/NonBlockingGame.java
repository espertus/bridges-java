package bridges.games;

import bridges.connect.SocketConnection;

/**
 * @brief This class provides the features necessary to implement
 * simple non blocking games.
 *
 * The games that can be created out of NonBlockingGame are based on a
 * simple board grid of at most 1024 cells (e.g., 32x32, or any
 * combinations less than 1024 cells). Each cell has a background
 * color, and a colored symbol.
 *
 * This class is used by having another class derive from it and
 * implement the two functions: initialize() and
 * gameLoop(). initialize() is called exactly once, on the first frame
 * of the game. It is used to make first time initializations of the
 * game state (such as setting the board in its initial position, for
 * instance in chess). However, gameLoop() is called at every frame of
 * the game. The game starts when the function start() is called on
 * the object you created.
 *
 * For this reason the simplest game that can run is created by:
 *
 * \code{.java}
 * import bridges.game.*;
 * import bridges.base.*;
 * class MyGame extends NonBlockingGame {
 *   public MyGame() { super (1, "myuserid",  "myapikey", 10, 10); }
 *   public void initialize()  { }
 *   public void gameLoop()  { }
 *   public static void  main (String args[]) {
 *     MyGame g = new MyGame();
 *     g.start();
 *   }
 * }
 * \endcode
 *
 * This game does not do anything, but it is the minimal code that
 * will run a game. Note that the constructor of my_game passes 5
 * parameters to the constructor of NonBlockingGame(). The first three
 * parameters are the classic parameters that the constructor of
 * bridges.connect.Bridges takes (e.g., assignmentID, username, apikey), the
 * last two are the size of the game board.
 *
 * To change the board, two functions are used. setBGColor() change
 * the background color of a particular cell. It takes three
 * parameters, the first two identify the cell of the board to change,
 * and the last one is a color from a color palette provided by
 * NamedColor. drawSymbol() takes four parameters, the first two
 * identify the cell of the board to change, the third is a symbol
 * from a symbol palette provided by NamedSymbol, the fourth is the
 * color that symbol shold be drawn in and provided by NamedColor.
 *
 * For instance, a very simple initialize() could look like:
 * \code{.java}
 * public void initialize() {
 *   setBGColor(0, 0, NamedColor.lightsalmon);
 *   drawSymbol(0, 0, NamedSymbol.sword, NamedColor.blue);
 * }
 * \endcode
 *
 * Note that the size of the board was set at 10x10 and that drawing
 * on a cell that does not exist will lead to an error. One can
 * specify a gameboard of a different size by changing the parameters
 * to the NonBlockingGame constructor. However, the game board can not
 * be more than 1024 cells in total, so a 15x15 board is possible, a
 * 32x32 board is the largest square board possible, and a rectangular
 * 64x16 rectangular board is also possible. But a 100x20 board would
 * be 2000 cells and is not possible. For instance a board of 16 rows
 * and 64 columns can be created defining the my_game constructor as:
 *
 * \code{.java}
 *   public MyGame() { super(1, "myuserid",  "myapikey", 16, 64); }
 * \endcode
 *
 * The bridges game engine will call the gameLoop() function at each
 * frame of the game. You can write this function to modify the state
 * of the game board using setBGColor() and drawSymbol(). For
 * instance, the following gameLoop() will color the board randomly
 * one cell at a time.
 *
 * \code{.java}
 * public void gameLoop() {
 *   setBGColor(Math.random()*10, Math.random()*10, NamedColor.lightsalmon);
 * }
 * \endcode
 *
 * The gameLoop can also probe the state of some keys of the keyboard
 * using functions keyUp(), keyLeft(), keyDown(), keyRight(), keyW(),
 * keyA(), keyS(), keyD(), keySpace(), and keyQ(). These functions
 * return a boolean that indicate whether the key is pressed at that
 * frame or not. For instance, the following code will only color the
 * board randomly when the up arrow is pressed.
 *
 * \code{.java}
 * public void gameLoop() {
 *   if (keyUp())
 *     setBGColor(Math.random()*10, Math.rand()*10, NamedColor.lightsalmon);
 * }
 * \endcode
 *
 * @sa See tutorial at  https://bridgesuncc.github.io/tutorials/NonBlockingGame.html
 *
 * @author Erik Saule
 * @date 7/21/19, 12/02/22
 *
 **/

public abstract class NonBlockingGame extends GameBase {
    private double fps = 30.;
    
    ///helper class to make Input Management a bit easier.
    private InputHelper ih;

    private InputStateMachine upSM;
    private InputStateMachine downSM;
    private InputStateMachine leftSM;
    private InputStateMachine rightSM;

    private InputStateMachine qSM;
    private InputStateMachine spaceSM;
    
    private InputStateMachine wSM;
    private InputStateMachine aSM;
    private InputStateMachine sSM;
    private InputStateMachine dSM;

    
    ///used for fps control
    private long timeoflastframe;

    /// @brief Is the LeftArrow key currently pressed?
    ///
    /// @return true if "left" is pressed
    protected boolean keyLeft() {
        return ih.left();
    }

    protected boolean keyLeftJustPressed() {
        return leftSM.justPressed();
    }
    protected boolean keyLeftStillPressed() {
        return leftSM.stillPressed();
    }
    protected boolean keyLeftJustNotPressed() {
        return leftSM.justNotPressed();
    }
    protected boolean keyLeftStillNotPressed() {
        return leftSM.stillNotPressed();
    }
    protected boolean keyLeftFire() {
        return leftSM.fire();
    }
    protected void keyLeftSetupFire(int f) {
        leftSM.setFireCooldown(f);
    }


    /// @brief Is the RightArrow key currently pressed?
    ///
    /// @return true if "right" is pressed
    protected boolean keyRight() {
        return ih.right();
    }

    protected boolean keyRightJustPressed() {
        return rightSM.justPressed();
    }
    protected boolean keyRightStillPressed() {
        return rightSM.stillPressed();
    }
    protected boolean keyRightJustNotPressed() {
        return rightSM.justNotPressed();
    }
    protected boolean keyRightStillNotPressed() {
        return rightSM.stillNotPressed();
    }
    protected boolean keyRightFire() {
        return rightSM.fire();
    }
    protected void keyRightSetupFire(int f) {
        rightSM.setFireCooldown(f);
    }

    
    /// @brief Is the UpArrow key currently pressed?
    ///
    /// @return true if "up" is pressed
    protected boolean keyUp() {
        return ih.up();
    }

    protected boolean keyUpJustPressed() {
        return upSM.justPressed();
    }
    protected boolean keyUpStillPressed() {
        return upSM.stillPressed();
    }
    protected boolean keyUpJustNotPressed() {
        return upSM.justNotPressed();
    }
    protected boolean keyUpStillNotPressed() {
        return upSM.stillNotPressed();
    }
    protected boolean keyUpFire() {
        return upSM.fire();
    }
    protected void keyUpSetupFire(int f) {
        upSM.setFireCooldown(f);
    }


    
    /// @brief Is the DownArrow key currently pressed?
    ///
    /// @return true if "down" is pressed
    protected boolean keyDown() {
        return ih.down();
    }

    protected boolean keyDownJustPressed() {
        return downSM.justPressed();
    }
    protected boolean keyDownStillPressed() {
        return downSM.stillPressed();
    }
    protected boolean keyDownJustNotPressed() {
        return downSM.justNotPressed();
    }
    protected boolean keyDownStillNotPressed() {
        return downSM.stillNotPressed();
    }
    protected boolean keyDownFire() {
        return downSM.fire();
    }
    protected void keyDownSetupFire(int f) {
        downSM.setFireCooldown(f);
    }


    /// @brief Is the Q key currently pressed?
    ///
    /// @return true if "q" is pressed
    protected boolean keyQ() {
        return ih.q();
    }

    protected boolean keyQJustPressed() {
        return qSM.justPressed();
    }
    protected boolean keyQStillPressed() {
        return qSM.stillPressed();
    }
    protected boolean keyQJustNotPressed() {
        return qSM.justNotPressed();
    }
    protected boolean keyQStillNotPressed() {
        return qSM.stillNotPressed();
    }
    protected boolean keyQFire() {
        return qSM.fire();
    }
    protected void keyQSetupFire(int f) {
        qSM.setFireCooldown(f);
    }


    /// @brief Is the SpaceBar key currently pressed?
    ///
    /// @return true if SpaceBar is pressed
    protected boolean keySpace() {
        return ih.space();
    }

    protected boolean keySpaceJustPressed() {
        return spaceSM.justPressed();
    }
    protected boolean keySpaceStillPressed() {
        return spaceSM.stillPressed();
    }
    protected boolean keySpaceJustNotPressed() {
        return spaceSM.justNotPressed();
    }
    protected boolean keySpaceStillNotPressed() {
        return spaceSM.stillNotPressed();
    }
    protected boolean keySpaceFire() {
        return spaceSM.fire();
    }
    protected void keySpaceSetupFire(int f) {
        spaceSM.setFireCooldown(f);
    }


    /// @brief Is the W key currently pressed?
    ///
    /// @return true if "w" is pressed
    protected boolean keyW() {
        return ih.w();
    }

    protected boolean keyWJustPressed() {
        return wSM.justPressed();
    }
    protected boolean keyWStillPressed() {
        return wSM.stillPressed();
    }
    protected boolean keyWJustNotPressed() {
        return wSM.justNotPressed();
    }
    protected boolean keyWStillNotPressed() {
        return wSM.stillNotPressed();
    }
    protected boolean keyWFire() {
        return wSM.fire();
    }
    protected void keyWSetupFire(int f) {
        wSM.setFireCooldown(f);
    }

    
    /// @brief Is the A key currently pressed?
    ///
    /// @return true if "a" is pressed?
    protected boolean keyA() {
        return ih.a();
    }

    protected boolean keyAJustPressed() {
        return aSM.justPressed();
    }
    protected boolean keyAStillPressed() {
        return aSM.stillPressed();
    }
    protected boolean keyAJustNotPressed() {
        return aSM.justNotPressed();
    }
    protected boolean keyAStillNotPressed() {
        return aSM.stillNotPressed();
    }
    protected boolean keyAFire() {
        return aSM.fire();
    }
    protected void keyASetupFire(int f) {
        aSM.setFireCooldown(f);
    }


    /// @brief Is the S key currently pressed?
    ///
    /// @return true if "s" is pressed
    protected boolean keyS() {
        return ih.s();
    }

    protected boolean keySJustPressed() {
        return sSM.justPressed();
    }
    protected boolean keySStillPressed() {
        return sSM.stillPressed();
    }
    protected boolean keySJustNotPressed() {
        return sSM.justNotPressed();
    }
    protected boolean keySStillNotPressed() {
        return sSM.stillNotPressed();
    }
    protected boolean keySFire() {
        return sSM.fire();
    }
    protected void keySSetupFire(int f) {
        sSM.setFireCooldown(f);
    }

    
    /// @brief Is the D key currently pressed?
    ///
    /// @return true if "d" is pressed
    protected boolean keyD() {
        return ih.d();
    }

    protected boolean keyDJustPressed() {
        return dSM.justPressed();
    }
    protected boolean keyDStillPressed() {
        return dSM.stillPressed();
    }
    protected boolean keyDJustNotPressed() {
        return dSM.justNotPressed();
    }
    protected boolean keyDStillNotPressed() {
        return dSM.stillNotPressed();
    }
    protected boolean keyDFire() {
        return dSM.fire();
    }
    protected void keyDSetupFire(int f) {
        dSM.setFireCooldown(f);
    }


    /// @brief Construct a NonBlockingGame object. It is expected
    /// students will never directly construct a NonBlockingGame
    /// object but rather derive from it.
    ///
    ///
    /// The created grid can not be larger than 1024 cells in total
    /// (e.g., 32x32, or 2x512 are ok).
    ///
    /// @param assignmentID bridges assignment ID
    /// @param login login on the bridges server
    /// @param apikey apikey of the account specified in login
    /// @param rows number of rows in the game
    /// @param cols number of columns in the game
    public NonBlockingGame(int assignmentID, String login, String apikey, int rows, int cols) {
        super(assignmentID, login, apikey, rows, cols);

	if ((cols * rows) > 1024) { // Allows students to create smaller grids if they prefer.
            System.out.println("ERROR: Number of cells in a non-blocking game grid cannot exceed 32x32 or 1024.");
            System.exit(1);
        }
        
        nonBlockInit();
    }

    public NonBlockingGame(int assignmentID, String login, String apikey, int rows, int cols, boolean deb) {
        super(assignmentID, login, apikey, rows, cols, deb);

	if ((cols * rows) > 1024) { // Allows students to create smaller grids if they prefer.
            System.out.println("ERROR: Number of cells in a non-blocking game grid cannot exceed 32x32 or 1024.");
            System.exit(1);
        }
        
        nonBlockInit();
    }

    
    ///Initializes specific non-blocking game variables
    private void nonBlockInit() {
        
        timeoflastframe = System.currentTimeMillis();

        // /Create input helpter for non-blocking game.
        ih = new InputHelper();
        registerKeypress(ih);

	upSM = new InputStateMachine(()->ih.up());
	downSM = new InputStateMachine(()->ih.down());
	leftSM = new InputStateMachine(()->ih.left());
	rightSM = new InputStateMachine(()->ih.right());


	aSM = new InputStateMachine(()->ih.a());
	sSM = new InputStateMachine(()->ih.s());
	dSM = new InputStateMachine(()->ih.d());
	wSM = new InputStateMachine(()->ih.w());

	qSM = new InputStateMachine(()->ih.q());
	spaceSM = new InputStateMachine(()->ih.space());
    }

    private void updateInputState() {
	upSM.update();
	downSM.update();
	leftSM.update();
	rightSM.update();
	
	aSM.update();
	sSM.update();
	dSM.update();
	wSM.update();

    	qSM.update();
	spaceSM.update();
    }

    
    /// sleeps so there is time between frames
    private void sleepTimer() {
	sleepTimer(1000);
    }

    /// sleeps so there is time between frames
    private void sleepTimer(long timems) {
        try {
            Thread.sleep(timems); // wait for browser to connect
        } catch (InterruptedException ie) {
            quit();
        }
    }

    /// should be called right before render() Aims at having a fixed
    /// fps of 30 frames per second. This work by waiting until
    /// 1/30th of a second after the last call to this function.
    private void controlFrameRate() {
        double hz = 1. / fps;

        long currenttime = System.currentTimeMillis();
        long theoreticalnextframe = timeoflastframe + (int) (hz * 1000);
        long waittime = theoreticalnextframe - currenttime;

        //System.out.println(waittime);
        if (waittime > 0) {
            sleepTimer(waittime);
        }
        timeoflastframe = System.currentTimeMillis();
    }

    /// @brief What frame rate is the game running at?
    ///
    /// @return the target framerate. The game could be somewhat
    /// slower depending on how computationally expensive the
    /// gameloop is and on the speed of the network.
    protected double getFrameRate() {
	return fps;
    }
    
    /// Call this function to start the game engine.
    ///
    public void start() {
        sleepTimer();
        // visualize the grid
        initialize();
        render();

        long framelimit = -1;

        {
            String str_limit = System.getenv("FORCE_BRIDGES_FRAMELIMIT");
            if (str_limit != null) {
                try {
                    framelimit = Long.parseLong(str_limit);
                    System.err.println("Setting framelimit to " + framelimit+ "!");
                }
                catch (java.lang.NumberFormatException e) {
                    System.err.println("FORCE_BRIDGES_FRAMELIMIT environment variable is not an integer. Ignoring it...");
                }
            }
        }

        long frame = 0;
        while (gameStarted) {
	    updateInputState();
            gameLoop();
            render();
            controlFrameRate();
            // System.out.println("rendered");
            frame ++;

            if (framelimit > 0 && frame > framelimit) {
                quit();
            }
        }

        terminateNetwork();

    }
}
