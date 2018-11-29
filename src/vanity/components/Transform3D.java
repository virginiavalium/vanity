package vanity.components;

import vanity.core.ProcessingBehaviour;
import processing.core.PVector;

public class Transform3D extends Transform {
  public float z;

  /**
   * Constructors
   */

  public Transform3D (ProcessingBehaviour parent) {
    super(parent);
  }

  public Transform3D (ProcessingBehaviour parent, PVector position) {
    super(parent, position);
  }

  public Transform3D (ProcessingBehaviour parent, PVector position, PVector scale) {
    super(parent, position, scale);
  }


  /**
   * Inner use methods
   */

  protected void updatePositionShortcuts () {
    super.updatePositionShortcuts();
    this.z = position.z;
  }
}
