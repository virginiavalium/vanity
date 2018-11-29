
package vanity.core;
import processing.core.PApplet;

public abstract class ProcessingComponent extends ProcessingEntity {
  protected ProcessingBehaviour parent;

  public boolean enabled = true;

  public ProcessingComponent (ProcessingBehaviour parent) {
    super(parent.getContext());
    this.parent = parent;
  }
}
