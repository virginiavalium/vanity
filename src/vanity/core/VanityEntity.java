package vanity.core;

public abstract class VanityEntity {
  protected VanityApplet context;

  public VanityEntity (VanityApplet context) {
    this.context = context;
  }

  public VanityApplet getContext() { return context; }
}
