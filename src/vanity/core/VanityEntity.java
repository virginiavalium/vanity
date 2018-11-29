package vanity.core;

import processing.core.PApplet;

public abstract class VanityEntity {
  protected PApplet context;

  public VanityEntity (PApplet context) {
    this.context = context;
  }

  public PApplet getContext() { return context; }
}
