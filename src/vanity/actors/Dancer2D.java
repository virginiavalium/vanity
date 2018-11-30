package vanity.actors;

import processing.core.PApplet;
import processing.core.PVector;
import vanity.behaviours.LifeBehaviour;
import vanity.behaviours.MovementBehaviour2D;
import vanity.core.VanityActor;
import vanity.core.VanityApplet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Dancer2D extends VanityActor {
  public MovementBehaviour2D movement;
  public LifeBehaviour life;

  public Dancer2D (VanityApplet context, PVector origin, PVector direction, PVector scale, PVector speed, float angularSpeed, float lifetime) {
    super(context);

    this.movement = new MovementBehaviour2D(context, origin, scale, direction, speed, angularSpeed);
    this.life     = new LifeBehaviour(context, lifetime);
  }
}

