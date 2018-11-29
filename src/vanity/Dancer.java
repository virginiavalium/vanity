package vanity;

import processing.core.PApplet;
import processing.core.PVector;
import vanity.behaviours.LifeBehaviour;
import vanity.behaviours.MovementBehaviour;
import vanity.behaviours.MovementBehaviour2D;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Dancer {
  public MovementBehaviour movement;
  public LifeBehaviour life;

  public Dancer (PApplet context, PVector origin, PVector direction, PVector scale, PVector speed, float angularSpeed, float lifetime) {
    this.movement = new MovementBehaviour2D(context, origin, scale, direction, speed, angularSpeed);
    this.life     = new LifeBehaviour(context, lifetime);
  }

  public void draw (int mode) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    movement.transform.beginMatrix();

    Method drawMethod = this.getClass().getDeclaredMethod("draw_" + mode, new Class[]{});
    drawMethod.invoke(this);

    movement.transform.endMatrix();
  }
}

