
package vanity.core;

public abstract class VanityComponent extends VanityEntity {
  protected VanityBehaviour parent;

  public boolean enabled = true;

  public VanityComponent (VanityBehaviour parent) {
    super(parent.getContext());
    this.parent = parent;
  }
}
