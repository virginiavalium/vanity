package vanity.behaviours;

import processing.core.PApplet;
import processing.core.PVector;
import vanity.core.VanityBehaviour;
import vanity.components.Transform;

public abstract class MovementBehaviour extends VanityBehaviour {

  public Transform transform;
  public PVector direction;
  public PVector speed;

  public MovementBehaviour (PApplet context, PVector origin, PVector scale, PVector direction, PVector speed) {
    super(context);
    this.direction = direction.copy();
    this.speed = speed.copy();
  }
}
