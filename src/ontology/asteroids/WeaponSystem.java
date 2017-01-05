package ontology.asteroids;

import ontology.Constants;
import tools.Utils;
import ontology.Types;

import static ontology.Constants.RESOURCE_PACK;

/**
 * Created by Mike and Raluca on 20/05/2016.
 * CSEE, University of Essex, UK
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class WeaponSystem {
  /**
   * weaponId, destructive power, cost, resource, weapon cooldown time
   */
  private int weaponId;
  private int power;
  private int cost;
  private int resource;
  private int cooldown;

  public WeaponSystem(int weaponId) {
    this.weaponId = weaponId;
    this.cooldown = 0;
    this.resource = Constants.MISSILE_MAX_RESOURCE;
    this.power = Constants.MISSILE_DESTRUCTIVE_POWER;
    this.cost = Constants.MISSILE_COST;

//    this.power = Types.RESOURCE_INFO.get(weaponId)[0];
//    this.cost = Types.RESOURCE_INFO.get(weaponId)[1];
//    this.resource = Types.RESOURCE_INFO.get(weaponId)[2];
//    this.cooldown = 0;
  }

  public void resetCooldown() {
    this.cooldown = Constants.MISSILE_COOLDOWN;
//    this.cooldown = Types.RESOURCE_INFO.get(weaponId)[3];
  }

  public boolean fire() {
    boolean fire = canFire();
    if (fire) {
      this.resource--;
      resetCooldown();
    }
    return fire;
  }

  public boolean canFire() {
    return (this.resource>0 && this.cooldown==0);
  }

  public WeaponSystem copy() {
    WeaponSystem ws = new WeaponSystem(this.getWeaponId());
    ws.resource = this.resource;
    ws.cooldown = this.cooldown;
    return ws;
  }

  public int getWeaponId() {
    return weaponId;
  }

  public int getPower() {
    return power;
  }

  public int getCost() {
    return cost;
  }

  public int getResource() {
    return resource;
  }

  public void addResource(){resource += RESOURCE_PACK;}

  public int getCooldown() {
    return Constants.MISSILE_COOLDOWN;
  }

  public void update() {
    this.cooldown--;
    this.cooldown = Utils.clamp(0, this.cooldown, Constants.MISSILE_COOLDOWN);

//    this.cooldown = Utils.clamp(0, this.cooldown, Types.RESOURCE_INFO.get(weaponId)[3]);
  }
}
