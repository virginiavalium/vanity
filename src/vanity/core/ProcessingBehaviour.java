package vanity.core;

import processing.core.PApplet;

public abstract class ProcessingBehaviour extends ProcessingEntity {

  public ProcessingBehaviour (PApplet context) {
    super(context);
  }

  protected void start () {}

  private void innerStart () {
    start();
  }

  protected void update () {}

  private void innerUpdate () {
    update();
  }

  protected void destroy () {}

  private void innerDestroy () {
    destroy();
  }
}
