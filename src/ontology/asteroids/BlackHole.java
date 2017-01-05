package ontology.asteroids;

import ontology.Constants;
import ontology.Types;
import tools.Vector2d;

import java.awt.*;

/**
 * Created by rdgain on 1/5/2017.
 */
public class BlackHole extends GameObject{

    public double force;

    public BlackHole(Vector2d pos, double radius, double force) {
        super(pos);
        this.wrappable = false;
        this.color = Types.GRAY;
        this.radius = radius;
        this.force = force;


    }

    @Override
    public GameObject copy() {
        BlackHole bh = new BlackHole(this.pos, this.radius, this.force);
        return bh;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(color);
        g.fillOval((int)(pos.x - radius),(int)(pos.y - radius),(int)radius*2,(int)radius*2);

    }

    @Override
    public void injured(int harm) {

    }
}
