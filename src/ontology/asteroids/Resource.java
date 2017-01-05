package ontology.asteroids;

import sl.shapes.StarPolygon;
import tools.Vector2d;

import java.awt.*;

import static ontology.Constants.*;

/**
 * Created by rdgain on 1/5/2017.
 */
public class Resource extends Weapon{


    public Resource(Vector2d pos){
        super(-1, pos, new Vector2d(0,0));
        this.ttl = RESOURCE_TTL;
    }

    @Override
    public void draw(Graphics2D g) {

        StarPolygon star = new StarPolygon((int)pos.x, (int)pos.y,  STAR_OUTSIDE_RADIUS, STAR_INSIDE_RADIUS,  STAR_EDGES);
        int[] xpoints = star.xpoints;
        int[] ypoints = star.ypoints;

        g.setColor(Color.ORANGE);
        g.fillPolygon(xpoints,ypoints,star.npoints);


    }


}
