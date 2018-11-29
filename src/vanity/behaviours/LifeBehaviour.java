package vanity.behaviours;

import processing.core.PApplet;
import processing.core.PVector;
import vanity.components.Transform;
import vanity.core.ProcessingBehaviour;

public class LifeBehaviour extends ProcessingBehaviour {

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
