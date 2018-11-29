package vanity.core;

import processing.core.PApplet;

public abstract class ProcessingEntity {
  protected PApplet context;

  public ProcessingEntity (PApplet context) {
    this.context = context;
  }

  public PApplet getContext() { return context; }
}
