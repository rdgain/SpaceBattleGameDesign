package ontology.asteroids;

import core.game.StateObservationMulti;
import core.player.AbstractMultiPlayer;
import ontology.Constants;
import ontology.Types;
import ontology.physics.ForcePhysics;
import ontology.physics.GravityPhysics;
import ontology.physics.RotationPhysics;
import tools.Utils;
import tools.Vector2d;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Ship extends GameObject {
  public Vector2d dir;
  public Vector2d velocity;
  public TreeMap<Integer, Integer> resources;
  public ArrayList<WeaponSystem> weaponSystems;
  public AbstractMultiPlayer player;

  private boolean thrusting;
  private int healthPoints;
  private int nbKills;
  public int collected;
  private int inBlackHoles;
  private double cost;
  private Types.WINNER winState;

  /** define the shape of the ship */
  static int[] xp = {-Constants.SHIP_RADIUS, 0, Constants.SHIP_RADIUS, 0};
  static int[] yp = {Constants.SHIP_RADIUS, -Constants.SHIP_RADIUS, Constants.SHIP_RADIUS, 0};
  /** the thrust poly that will be drawn when the ship is thrusting */
  static int[] xpThrust =  {-Constants.SHIP_RADIUS, 0, Constants.SHIP_RADIUS, 0};
  static int[] ypThrust = {Constants.SHIP_RADIUS, Constants.SHIP_RADIUS+1, Constants.SHIP_RADIUS, 0};
  /**
   * Constructor
   */
  public Ship(Vector2d pos, Vector2d dir, int playerId) {
    super(pos);
    this.dir = dir;
    this.velocity = new Vector2d();
    this.playerId = playerId;
    setParam();
  }

  public Ship(Vector2d pos, Vector2d dir, Vector2d velocity, int playerId) {
    this(pos, dir, playerId);
    this.velocity = velocity;
  }


  public void reset() {
    this.pos.set(Constants.WIDTH / 2, Constants.WIDTH / 2);
    this.dir.set(0, -1);
    this.velocity.zero();
    setParam();
  }

  public void setParam() {
    this.radius = Constants.SHIP_RADIUS;
    this.thrusting = false;
    this.healthPoints = Constants.MAX_HEALTH_POINTS;
    this.winState = Types.WINNER.NO_WINNER;
    this.color = Types.PLAYER_COLOR[playerId];
    this.cost = 0.0;
    this.nbKills = 0;
    this.inBlackHoles = 0;
    this.collected = 0;
    this.destructivePower = Constants.MISSILE_DESTRUCTIVE_POWER;
    this.resources = new TreeMap<>();
//    this.resources.put(Constants.WEAPON_ID_MISSILE,Constants.MISSILE_MAX_RESOURCE);
    this.weaponSystems = new ArrayList<>();
  }

  public void update(Types.ACTIONS action) {
    this.thrusting = false;

    if(action.toString().contains("THRUST"))
    {
      this.thrusting = true;
      ForcePhysics.thrust(velocity, dir);
    }

    if(action.toString().contains("LEFT"))
    {
      RotationPhysics.steer(dir, -1.0);
    }

    if(action.toString().contains("RIGHT"))
    {
      RotationPhysics.steer(dir, 1.0);
    }

    GravityPhysics.gravity(pos, velocity);

    for (BlackHole[] bha : StateObservationMulti.blackHoles) {
      for (BlackHole bh : bha) {
        if (bh != null) {
        	
        	double distance = this.pos.dist(bh.pos);
        	
          if (distance < bh.radius) {
            GravityPhysics.blackHoleForce(pos, velocity, bh);
            
            if(distance < bh.radius-Constants.SAFE_ZONE)
            {
            	inBlackHoles++;
            }
            
          }
        }
      }
    }

    velocity.x = Utils.clamp(-Constants.SHIP_MAX_SPEED, velocity.x,
        Constants.SHIP_MAX_SPEED);
    velocity.y = Utils.clamp(-Constants.SHIP_MAX_SPEED, velocity.y,
        Constants.SHIP_MAX_SPEED);

    pos.add(velocity);

    for (WeaponSystem ws : weaponSystems) {
      ws.update();
    }
  }

  public double dotTo(Ship other)
  {
    Vector2d diff = Vector2d.subtract(other.pos,this.pos);
    Vector2d front = new Vector2d(this.dir);
    front.normalise();
    diff.normalise();
    return diff.dot(front);
  }

  public double dotDirections(Ship other)
  {
    Vector2d thisFront = new Vector2d(this.dir);
    Vector2d otherFront = new Vector2d(other.dir);
    thisFront.normalise();
    otherFront.normalise();
    return thisFront.dot(otherFront);
  }

  public double distTo(Ship other)
  {
    Vector2d diff = Vector2d.subtract(other.pos, this.pos);
    return diff.mag();
  }

  public Rectangle2D getBound() {
    return new Rectangle2D.Double(pos.x, pos.y,
        Double.valueOf(xp[2]-xp[0]), Double.valueOf(yp[0]-yp[1]));
  }

  public Vector2d getDirection() {
    return dir;
  }

  public Types.WINNER getWinState() {
    return winState;
  }

  public void setWinState(Types.WINNER winner) {
    this.winState = winner;
  }

  public double getScore() {
    double score = getPoints();
//        + this.healthPoints * Constants.LIVE_AWARD;
    return score;
  }

  public void setPlayer(AbstractMultiPlayer _AbstractMulti_player) {
    this.player = _AbstractMulti_player;
  }

  public WeaponSystem getWeapon(int weaponId) {
    for(WeaponSystem ws : weaponSystems) {
      if (ws.getWeaponId() == weaponId) {
        return ws;
      }
    }
    return null;
  }

  public boolean fireWeapon(int weaponId) {
    WeaponSystem ws = getWeapon(weaponId);
    if (StateObservationMulti.cheating == playerId) {
      this.cost -= ws.getCost();
      return true;
    }
    if (ws != null) {
      boolean canFire = ws.fire();
      if (canFire) {
        this.cost -= ws.getCost();
      }
      return canFire;
    }
    return false;
  }

  public boolean canFireWeapon(int weaponId) {
    if (StateObservationMulti.cheating == playerId) {
      return true;
    }
    WeaponSystem ws = getWeapon(weaponId);
    if (ws != null) {
      return ws.canFire();
    }
    return false;
  }

  public void kill() {
    this.nbKills++;
  }

  @Override
  public void injured(int harm) {
    this.healthPoints = this.healthPoints - harm;
    this.healthPoints = tools.Utils.clamp(0, this.healthPoints, Constants.MAX_HEALTH_POINTS);
  }

  @Override
  public Ship copy() {
    Ship cloneShip = new Ship(pos.copy(), dir.copy(), velocity.copy(), playerId);
    cloneShip.healthPoints = this.healthPoints;
    cloneShip.nbKills = this.nbKills;
    cloneShip.cost = this.cost;
    cloneShip.winState = this.winState;
    cloneShip.resources.clear();
//    for(Map.Entry<Integer,Integer> entry : this.resources.entrySet()) {
//      int key = entry.getKey();
//      int value = entry.getValue();
//      cloneShip.resources.put(key, value);
//    }
    cloneShip.weaponSystems.clear();
    for(WeaponSystem ws : weaponSystems) {
      cloneShip.weaponSystems.add(ws.copy());
    }
    return cloneShip;
  }

  @Override
  public void update() {
    throw new IllegalArgumentException("You shouldn't be calling this...");
  }

  @Override
  public void draw(Graphics2D g) {
    color = Types.PLAYER_COLOR[playerId];
    AffineTransform at = g.getTransform();
    g.translate(pos.x, pos.y);
    double rot = RotationPhysics.rotate(dir);
    g.rotate(rot);
    g.scale(Constants.SHIP_SCALE, Constants.SHIP_SCALE);
    g.setColor(color);
    g.fillPolygon(xp, yp, xp.length);
    if (this.thrusting) {
      g.setColor(Color.red);
      g.fillPolygon(xpThrust, ypThrust, xpThrust.length);
    }
    g.setTransform(at);
  }

  public void updatePoints() {
    getPoints();
  }

  public double getPoints() {
    return this.nbKills * Constants.KILL_AWARD + this.cost + collected*Constants.RESOURCE_BONUS
            - this.inBlackHoles * (Constants.BLACKHOLE_PENALTY / 10.0);
  }

  public double getCost() {
    return this.cost;
  }

  public double getCostUnit() {
    WeaponSystem ws = getWeapon(Constants.WEAPON_ID_MISSILE);
    return ws.getCost();
  }

  public int getCooldown() {
    WeaponSystem ws = getWeapon(Constants.WEAPON_ID_MISSILE);
    return ws.getCooldown();
  }

  public int getHealthPoints() {
    return this.healthPoints;
  }

  @Override
  public boolean isDead() {
    return (this.healthPoints<=0);
  }
}
