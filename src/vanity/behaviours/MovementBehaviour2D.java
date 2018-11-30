package vanity.behaviours;

import processing.core.PVector;
import vanity.components.Transform2D;
import vanity.core.VanityApplet;
import vanity.core.VanityBehaviour;

public class MovementBehaviour2D extends VanityBehaviour {
  public Transform2D transform;
  public PVector direction;
  public PVector speed;
  public float angularSpeed;

  public MovementBehaviour2D (VanityApplet context, PVector origin, PVector scale, PVector direction, PVector speed, float angularSpeed) {
    super(context);

    this.transform = new Transform2D(this, origin, scale);
    this.direction = direction.copy();
    this.speed     = speed.copy();
    this.angularSpeed = angularSpeed;
  }

  public void setDirection (PVector direction) {
    this.direction = direction.normalize();
  }

  public void addToDirection (PVector addition) {
    this.direction.add(addition).normalize();
  }

  public void move () {
    transform.translate(direction, speed);

    if (!transform.inBounds(new PVector(0, 0), new PVector(context.width, context.height))) {
      this.direction = direction.mult(-1).normalize();
    }
  }

  public void rotateDirection (float radians) {
    float angle = direction.heading() + radians;
    this.direction = PVector.fromAngle(angle);
  }
}
