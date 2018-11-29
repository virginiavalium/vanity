package vanity.behaviours;

import vanity.components.Transform2D;

import processing.core.PApplet;
import processing.core.PVector;

public class MovementBehaviour2D extends MovementBehaviour {

  public Transform2D transform;
  public float angularSpeed;

  public MovementBehaviour2D (PApplet context, PVector origin, PVector scale, PVector direction, PVector speed, float angularSpeed) {
    super(context, origin, scale, direction, speed);
    this.angularSpeed = angularSpeed;
  }
}
