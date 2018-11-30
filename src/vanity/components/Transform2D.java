package vanity.components;

import vanity.core.VanityBehaviour;

import processing.core.PVector;
import vanity.core.VanityComponent;

import static processing.core.PApplet.radians;

public class Transform2D extends VanityComponent {
  public PVector position = new PVector(0, 0);
  public PVector scale    = new PVector(1, 1);
  public float rotation   = 0;

  public float x = 0, y = 0;

  /**
   * Constructors
   */

  public Transform2D (VanityBehaviour parent) {
    super(parent);
  }

  public Transform2D (VanityBehaviour parent, float rotation) {
    super(parent);
    this.rotation = rotation;
  }

  public Transform2D (VanityBehaviour parent, PVector position) {
    super(parent);
    this.position = position.copy();
    updatePositionShortcuts();
  }

  public Transform2D (VanityBehaviour parent, PVector position, float rotation) {
    super(parent);
    this.position = position.copy();
    this.rotation = rotation;
  }

  public Transform2D (VanityBehaviour parent, PVector position, PVector scale) {
    super(parent);
    this.position = position.copy();
    this.scale = scale.copy();
  }

  public Transform2D (VanityBehaviour parent, PVector position, PVector scale, float rotation) {
    super(parent);
    this.position = position.copy();
    this.scale = scale.copy();
    this.rotation = rotation;
  }


  /**
   * Transformation methods
   */

  public void translate (PVector direction) {
    translate(direction, new PVector(1, 1));
  }

  public void translate (PVector direction, PVector speed) {
    position.add(direction.copy().cross(speed));
    updatePositionShortcuts();
  }

  public void rotate (float radians) { this.rotation += radians; }
  public void rotateDegrees (float degrees) { this.rotation += radians(degrees); }

  public void beginMatrix () {
    context.pushMatrix();
    context.rotate(rotation);
    context.translate(position.x, position.y);
  }

  public void endMatrix () {
    context.popMatrix();
  }


  /**
   * Checking stuff
   */

  public boolean inBounds (PVector minBounds, PVector maxBounds) {
    float minX = context.min(minBounds.x, maxBounds.x), maxX = context.max(minBounds.x, maxBounds.x);
    float minY = context.min(minBounds.y, maxBounds.y), maxY = context.max(minBounds.y, maxBounds.y);
    float minZ = context.min(minBounds.z, maxBounds.z), maxZ = context.max(minBounds.z, maxBounds.z);

    return position.x >= minX && position.x <= maxX
            && position.y >= minY && position.y <= maxY
            && position.z >= minZ && position.z <= maxZ;
  }


  /**
   * Inner use methods
   */

  protected void updatePositionShortcuts () {
    x = position.x;
    y = position.y;
  }


  /**
   * Debug methods
   */

  public String toString () {
    return String.format("position: %s, scale: %s, rotation: %2f", position, scale, rotation);
  }
}
