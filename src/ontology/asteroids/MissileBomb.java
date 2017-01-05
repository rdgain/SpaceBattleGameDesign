package ontology.asteroids;

import ontology.Constants;
import ontology.Types;
import tools.Vector2d;

import java.awt.*;

import static ontology.Constants.BOMB_TTL;

/**
 * Created by Jialin Liu on 04/10/16.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class MissileBomb extends Missile {
  private Color color;

  public MissileBomb(int playerId, Vector2d pos) {
    super(playerId, pos, new Vector2d());
    this.destructivePower = Constants.MISSILE_DESTRUCTIVE_POWER;
    this.ttl = BOMB_TTL;
    setRadius();
    setColor();
  }

  public void setVelocityByDir(Vector2d dir) {
    this.velocity = Vector2d.multiply(dir, Constants.MISSILE_MAX_SPEED);
  }

  public void setColor() {
    this.color = Types.PLAYER_COLOR[this.getPlayerId()];
  }

  @Override
  public void update() {
    if (!isDead()) {
      pos.add(velocity);
      ttl--;
    }
  }

  @Override
  public void setRadius() {
    this.radius = Constants.BOMB_RADIUS;
  }

  @Override
  public void draw(Graphics2D g) {
    g.setColor(color);
    g.fillOval((int) (pos.x-radius), (int) (pos.y-radius), (int) radius * 2, (int) radius * 2);
  }

  @Override
  public GameObject copy() {
    MissileBomb object = new MissileBomb(playerId, pos.copy());
    return object;
  }
}
