package ontology;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Constants {

    public static final int DEFAULT = 0;
    public static final int TWIN = 1;
    public static double THETA = 40;
    public static final int BOMB = 2;
    public static int BOMB_TTL = 2;

    public static String [] CONTROLLERS = new String[]{"rotateAndShoot", "doNothing", "sampleRandom",
            "sampleOneStepLookAhead", "sampleGA", "sampleOLMCTS"};

    /**
     * Things to evolve
     */
    public static int MISSILE_MAX_SPEED = 5; // to optimise, id=7
    public static int MISSILE_COOLDOWN = 4; // to optimise, id=8
    public static int MISSILE_RADIUS = 4; // to optimise, id=5
    public static int MISSILE_MAX_TTL = 100; // to optimise, id=6

    public static int GRID_SIZE = 2; //max 4

    public static int grid1 = 0;
    public static int grid2 = 0;
    public static int grid3 = 0;
    public static int grid4 = 0;
    public static int grid5 = 0;
    public static int grid6 = 0;
    public static int grid7 = 0;
    public static int grid8 = 0;
    public static int grid9 = 0;
    public static int grid10 = 0;
    public static int grid11 = 0;
    public static int grid12 = 0;
    public static int grid13 = 0;
    public static int grid14 = 0;
    public static int grid15 = 0;
    public static int grid16 = 0;

    public static String HOLE_GRID_MAYBE = (grid1 == 0? "F" : "T") + (grid2 == 0? "F" : "T") + (grid3 == 0? "F" : "T") + (grid4 == 0? "F" : "T")
            + (grid5 == 0? "F" : "T") + (grid6 == 0? "F" : "T") + (grid7 == 0? "F" : "T") + (grid8 == 0? "F" : "T") + (grid9 == 0? "F" : "T") +
            (grid10 == 0? "F" : "T") + (grid11 == 0? "F" : "T") + (grid12 == 0? "F" : "T") + (grid13 == 0? "F" : "T") + (grid14 == 0? "F" : "T") + (grid15 == 0? "F" : "T") + (grid16 == 0? "F" : "T");
    //    public static String HOLE_GRID_MAYBE = "TTTTTTTTTTTTTTTTTTT";
    public static int BLACKHOLE_RADIUS = 100;
    public static int BLACKHOLE_FORCE = 2;
    public static int BLACKHOLE_PENALTY = 1;
    public static int SAFE_ZONE = 10;

    public static int BOMB_RADIUS = 20;
    public static int MISSILE_TYPE = TWIN;

    public static int RESOURCE_TTL = 500;
    public static int RESOURCE_COOLDOWN = 250;

    public static int ENEMY_ID = 0;



    /**
     * Other things that are set
     */
    public static final int MISSILE_MAX_RESOURCE = 100;
    public static int RESOURCE_PACK = 20;

    public static int MAX_GRID_SIZE = 4;

    public static double SHIP_MAX_SPEED = 20; // to optimise, id=2
    public static double THRUST_SPEED = 15; // to optimise, id=3
    public static int MISSILE_COST = 0; // to optimise, id=4
    public static int SHIP_RADIUS = 20; // to optimise, id=1

    public static int KILL_AWARD = 100; // to optimise, id=11
    public static int RESOURCE_BONUS = 10;





    public static int STAR_OUTSIDE_RADIUS = 10;
    public static int STAR_INSIDE_RADIUS = 3;
    public static int STAR_EDGES = 5;


    public static final double SHIP_SCALE = 1;
    public static final double MAX_REPULSE_FORCE = 1.0;

    public static final int WEAPON_ID_MISSILE = 1;
    public static final int MISSILE_DESTRUCTIVE_POWER = 1;

    public static final double GRAVITY = 0;
    public static double FRICTION = 0.99; // to optimise, id=9

    public static double RADIAN_UNIT = 4; // to optimise, id=10

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final double VIEW_SCALE = 1.0;

    public static final int MAX_HEALTH_POINTS = 1000;
    public static final int LIVE_AWARD = 10;

    public static final boolean SHOW_ROLLOUTS = true;
}
