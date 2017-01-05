package controllers.humanWSAD;

import core.game.StateObservationMulti;
import core.player.AbstractMultiPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jialin Liu on 05/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Agent extends AbstractMultiPlayer implements KeyListener {
  Types.ACTIONS action1, action2;
  static int nControllers = 0;

  /**
   * Public constructor with state observation and time due.
   * @param so state observation of the current game.
   * @param elapsedTimer Timer for the controller creation.
   * @param playerID ID if this agent
   */
  public Agent(StateObservationMulti so, ElapsedCpuTimer elapsedTimer, int playerID)
  {
    setPlayerID(playerID);
    action1 = Types.ACTIONS.ACTION_NIL;
    action2 = Types.ACTIONS.ACTION_NIL;
  }

  @Override
  public Types.ACTIONS act(StateObservationMulti stateObs, ElapsedCpuTimer elapsedTimer) {


    if (action1.equals(action2)) {
      action2 = Types.ACTIONS.ACTION_NIL;
      return action1;
    }
    else if (action1 == Types.ACTIONS.ACTION_NIL) {
      action2 = Types.ACTIONS.ACTION_NIL; return action2;}
    else if (action2 == Types.ACTIONS.ACTION_NIL) return action1;
    else {
      Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

      if(action1==Types.ACTIONS.ACTION_THRUST && action2==Types.ACTIONS.ACTION_LEFT ||
              action2==Types.ACTIONS.ACTION_THRUST && action1==Types.ACTIONS.ACTION_LEFT
              ) {
        action1 = Types.ACTIONS.ACTION_THRUST_LEFT;
        action = Types.ACTIONS.ACTION_THRUST_LEFT;
      }
      else if(action1==Types.ACTIONS.ACTION_THRUST && action2==Types.ACTIONS.ACTION_RIGHT ||
              action2==Types.ACTIONS.ACTION_THRUST && action1==Types.ACTIONS.ACTION_RIGHT
              ) {
        action = action1 = Types.ACTIONS.ACTION_THRUST_RIGHT;
        //action = Types.ACTIONS.ACTION_THRUST_RIGHT;
      }
      else if(action1==Types.ACTIONS.ACTION_THRUST && action2==Types.ACTIONS.ACTION_FIRE ||
              action2==Types.ACTIONS.ACTION_THRUST && action1==Types.ACTIONS.ACTION_FIRE
              ) {
        action = action1 = Types.ACTIONS.ACTION_THRUST_FIRE;
        //  action = Types.ACTIONS.ACTION_THRUST_FIRE;
      }

      else if(action1 == Types.ACTIONS.ACTION_THRUST_LEFT && action2==Types.ACTIONS.ACTION_FIRE
              ||
              action1 == Types.ACTIONS.ACTION_LEFT_FIRE && action2==Types.ACTIONS.ACTION_THRUST
              ||
              action1 == Types.ACTIONS.ACTION_THRUST_FIRE && action2==Types.ACTIONS.ACTION_LEFT
              )
      {
        action = action1 = Types.ACTIONS.ACTION_THRUST_LEFT_FIRE;
        // action = Types.ACTIONS.ACTION_THRUST_LEFT_FIRE;
      }

      else if(action1 == Types.ACTIONS.ACTION_THRUST_RIGHT && action2==Types.ACTIONS.ACTION_FIRE ||
              action1 == Types.ACTIONS.ACTION_RIGHT_FIRE && action2==Types.ACTIONS.ACTION_THRUST
              ||
              action1 == Types.ACTIONS.ACTION_THRUST_FIRE && action2==Types.ACTIONS.ACTION_RIGHT
              )
      {

        action = action1 = Types.ACTIONS.ACTION_THRUST_RIGHT_FIRE;
      }

      else if(action1 == Types.ACTIONS.ACTION_LEFT && action2==Types.ACTIONS.ACTION_FIRE ||
              action2 == Types.ACTIONS.ACTION_LEFT && action1==Types.ACTIONS.ACTION_FIRE)
      {
        action = action1 = Types.ACTIONS.ACTION_LEFT_FIRE;
      }

      else if(action1 == Types.ACTIONS.ACTION_RIGHT && action2==Types.ACTIONS.ACTION_FIRE||
              action2 == Types.ACTIONS.ACTION_RIGHT && action1==Types.ACTIONS.ACTION_FIRE)
      {
        action = action1 = Types.ACTIONS.ACTION_RIGHT_FIRE;
      }

//      if (action1 == Types.ACTIONS.ACTION_THRUST) {
//        if (action2 == Types.ACTIONS.ACTION_LEFT)
//          action = Types.ACTIONS.ACTION_THRUST_LEFT;
//        if (action2 == Types.ACTIONS.ACTION_RIGHT)
//          action = Types.ACTIONS.ACTION_THRUST_RIGHT;
//        if (action2 == Types.ACTIONS.ACTION_FIRE)
//          action = Types.ACTIONS.ACTION_THRUST_FIRE;
//      }
//
//      if (action2 == Types.ACTIONS.ACTION_THRUST) {
//        if (action1 == Types.ACTIONS.ACTION_LEFT)
//          action = Types.ACTIONS.ACTION_THRUST_LEFT;
//        if (action1 == Types.ACTIONS.ACTION_RIGHT)
//          action = Types.ACTIONS.ACTION_THRUST_RIGHT;
//        if (action1 == Types.ACTIONS.ACTION_FIRE)
//          action = Types.ACTIONS.ACTION_THRUST_FIRE;
//      }

      action2 = Types.ACTIONS.ACTION_NIL;
      //    System.out.println(action1+" "+action2);
      return action;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
      case KeyEvent.VK_W :
        if (action1 == Types.ACTIONS.ACTION_NIL)
          action1 = Types.ACTIONS.ACTION_THRUST; else action2 = Types.ACTIONS.ACTION_THRUST;
        break;
      case KeyEvent.VK_A :
        if (action1 == Types.ACTIONS.ACTION_NIL)
          action1 = Types.ACTIONS.ACTION_LEFT; else action2 = Types.ACTIONS.ACTION_LEFT;
        break;
      case KeyEvent.VK_D :
        if (action1 == Types.ACTIONS.ACTION_NIL)
          action1 = Types.ACTIONS.ACTION_RIGHT; else action2 = Types.ACTIONS.ACTION_RIGHT;
        break;
      case KeyEvent.VK_SHIFT :
        if (action1 == Types.ACTIONS.ACTION_NIL)
          action1 = Types.ACTIONS.ACTION_FIRE; else action2 = Types.ACTIONS.ACTION_FIRE;
        break;
      default:
        if (action1 == Types.ACTIONS.ACTION_NIL)
          action1 = Types.ACTIONS.ACTION_NIL; else  action2 = Types.ACTIONS.ACTION_NIL;
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_W) {
      if (action1 == Types.ACTIONS.ACTION_THRUST)
        action1 = Types.ACTIONS.ACTION_NIL;
//      else if (action2 == Types.ACTIONS.ACTION_THRUST)
//        action2 = Types.ACTIONS.ACTION_NIL;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_FIRE)
        action1 = Types.ACTIONS.ACTION_FIRE;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_LEFT)
        action1 = Types.ACTIONS.ACTION_LEFT;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_RIGHT)
        action1 = Types.ACTIONS.ACTION_RIGHT;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_LEFT_FIRE)
        action1 = Types.ACTIONS.ACTION_LEFT_FIRE;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_RIGHT_FIRE)
        action1 = Types.ACTIONS.ACTION_RIGHT_FIRE;
    }
    if (key == KeyEvent.VK_A) {
      if (action1 == Types.ACTIONS.ACTION_LEFT)
        action1 = Types.ACTIONS.ACTION_NIL;

      else if (action1 == Types.ACTIONS.ACTION_LEFT_FIRE)
        action1 = Types.ACTIONS.ACTION_FIRE;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_LEFT)
        action1 = Types.ACTIONS.ACTION_THRUST;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_LEFT_FIRE)
        action1 = Types.ACTIONS.ACTION_THRUST_FIRE;
    }
    if (key == KeyEvent.VK_D) {
      if (action1 == Types.ACTIONS.ACTION_RIGHT)
        action1 = Types.ACTIONS.ACTION_NIL;
      else if (action1 == Types.ACTIONS.ACTION_RIGHT_FIRE)
        action1 = Types.ACTIONS.ACTION_FIRE;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_RIGHT)
        action1 = Types.ACTIONS.ACTION_THRUST;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_RIGHT_FIRE)
        action1 = Types.ACTIONS.ACTION_THRUST_FIRE;
    }
    if (key == KeyEvent.VK_SHIFT) {
      if (action1 == Types.ACTIONS.ACTION_FIRE)
        action1 = Types.ACTIONS.ACTION_NIL;
//      else
//      if (action2 == Types.ACTIONS.ACTION_FIRE)
//        action2 = Types.ACTIONS.ACTION_NIL;

      else if (action1 == Types.ACTIONS.ACTION_THRUST_FIRE)
        action1 = Types.ACTIONS.ACTION_THRUST;
      else if (action1 == Types.ACTIONS.ACTION_LEFT_FIRE)
        action1 = Types.ACTIONS.ACTION_LEFT;
      else if (action1 == Types.ACTIONS.ACTION_RIGHT_FIRE)
        action1 = Types.ACTIONS.ACTION_RIGHT;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_LEFT_FIRE)
        action1 = Types.ACTIONS.ACTION_THRUST_LEFT;
      else if (action1 == Types.ACTIONS.ACTION_THRUST_RIGHT_FIRE)
        action1 = Types.ACTIONS.ACTION_THRUST_RIGHT;
    }
  }

  @Override
  public void draw(Graphics2D g) {}
}
