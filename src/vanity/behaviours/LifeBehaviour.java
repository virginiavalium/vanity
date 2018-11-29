package vanity.behaviours;

import processing.core.PApplet;
import vanity.core.VanityBehaviour;

public class LifeBehaviour extends VanityBehaviour {

  public float lifetime;
  public float maxLifetime;

  public LifeBehaviour (PApplet context, float maxLifetime) {
    super(context);
    this.maxLifetime = maxLifetime;
  }

  public void start () {
    this.lifetime = 0;
  }

  public void update () {
    lifetime++;

    if (lifetime >= maxLifetime) {
      die();
    }
  }

  public void die () {
    // doSomeFancyStuff();
    destroy();
  }
}
