package vanity.components;

import vanity.core.ProcessingBehaviour;

import processing.core.PVector;
import static processing.core.PApplet.radians;

public class Transform2D extends Transform {
  public float rotation = 0;

  /**
   * Constructors
   */

  public Transform2D (ProcessingBehaviour parent) {
    super(parent);
  }

  public Transform2D (ProcessingBehaviour parent, float rotation) {
    super(parent);
    this.rotation = rotation;
  }

  public Transform2D (ProcessingBehaviour parent, PVector position) {
    super(parent, position);
  }

  public Transform2D (ProcessingBehaviour parent, PVector position, float rotation) {
    super(parent, position);
    this.rotation = rotation;
  }

  public Transform2D (ProcessingBehaviour parent, PVector position, PVector scale) {
    super(parent, position, scale);
  }

  public Transform2D (ProcessingBehaviour parent, PVector position, PVector scale, float rotation) {
    super(parent, position, scale);
    this.rotation = rotation;
  }

  /**
   * Transformation methods
   */

  public void beginMatrix () {
    super.beginMatrix();
    context.rotate(rotation);
  }

  public void rotate (float radians) { this.rotation += radians; }
  public void rotateDegrees (float degrees) { this.rotation += radians(degrees); }


  /**
   * Debug methods
   */

  public String toString () {
    return String.format("position: %s, scale: %s, rotation: %2f", position, scale, rotation);
  }
}
