package vanity.core;

public abstract class VanityBehaviour extends VanityEntity {

  public VanityBehaviour (VanityApplet context) {
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
