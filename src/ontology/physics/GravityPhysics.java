package ontology.physics;

import ontology.Constants;
import ontology.asteroids.BlackHole;
import tools.Vector2d;

import static ontology.Constants.BLACKHOLE_FORCE;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class GravityPhysics {

  public static void gravity(Vector2d pos, Vector2d velocity) {
    pos.subtract(0, Constants.GRAVITY);
    velocity.mul(Constants.FRICTION);
  }

  public static void blackHoleForce(Vector2d pos, Vector2d velocity, BlackHole bh) {

    Vector2d direction = bh.getPosition().getNormalDirectionBetween(pos);
    direction.mul(-BLACKHOLE_FORCE);

    pos.add(direction);

    //velocity.subtract(bh.getPosition().x*bh.force, bh.getPosition().y*bh.force);
    //velocity.mul(Constants.FRICTION);
  }
}
