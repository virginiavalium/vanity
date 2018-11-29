package vanity.components;

import vanity.core.*;

import processing.core.PVector;
import static processing.core.PApplet.*;

public abstract class Transform extends ProcessingComponent {
  public PVector position = new PVector(0, 0, 0);
  public PVector scale = new PVector(1, 1, 1);

  public float x = 0, y = 0;

  /**
   * Constructors
   */
  public Transform (ProcessingBehaviour parent) {
    super(parent);
  }

  public Transform (ProcessingBehaviour parent, PVector position) {
    super(parent);
    this.position = position.copy();
    updatePositionShortcuts();
  }

  public Transform (ProcessingBehaviour parent, PVector position, PVector scale) {
    super(parent);
    this.position = position.copy();
    this.scale = scale.copy();
    updatePositionShortcuts();
  }

  /**
   * Transformation methods
   */

  public void translate (PVector direction) {
    translate(direction, 1);
  }

  public void translate (PVector direction, float speed) {
    position.add(direction.copy().mult(speed));
    updatePositionShortcuts();
  }

  public void beginMatrix () {
    context.pushMatrix();
    context.translate(position.x, position.y, position.z);
  }

  public void endMatrix () {
    context.popMatrix();
  }

  /**
  * Checking stuff
  */

  public boolean inBounds (PVector minBounds, PVector maxBounds) {
    float minX = min(minBounds.x, maxBounds.x), maxX = max(minBounds.x, maxBounds.x);
    float minY = min(minBounds.y, maxBounds.y), maxY = max(minBounds.y, maxBounds.y);
    float minZ = min(minBounds.z, maxBounds.z), maxZ = max(minBounds.z, maxBounds.z);

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
    return String.format("position: %s, scale: %s", position, scale);
  }
}
