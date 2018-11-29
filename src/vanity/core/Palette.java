package vanity.core;

import processing.core.PApplet;

import java.util.Arrays;

public class Palette extends ProcessingEntity {
  public enum Harmony { Monochromatic, Analogous, Complementary, Triads };

  public enum LerpMode { Linear, Log, Exp };

  private Harmony harmony = Harmony.Monochromatic;
  private LerpMode lerpMode = LerpMode.Linear;

  private int[] colors;

  private int baseColor;
  private int foreground;
  private int background;


  // Values can be overriden in the second constructor
  private int minSaturation = 10;
  private int maxSaturation = 60;
  private int minLuminosity = 20;
  private int maxLuminosity = 60;

  /**
   * Constructors
   */

  public Palette (PApplet context, int[] colors) {
    super(context);

    this.colors = colors;
  }

  public Palette (PApplet context, Color[] colors) {
    super(context);

    this.colors = Color.toIntArray(colors);
  }

  public Palette (PApplet context, String colors) {
    super(context);

    String[] hexColors = colors.split("-");
    this.colors = new int[hexColors.length];

    for (int i = 0; i < hexColors.length; i++) { this.colors[i] = context.unhex("FF" + hexColors[i]); }
  }

  public Palette (PApplet context, String colors, LerpMode mode) {
    super(context);

    String[] hexColors = colors.split("-");
    this.colors = new int[hexColors.length];

    for (int i = 0; i < hexColors.length; i++) { this.colors[i] = context.unhex("FF" + hexColors[i]); }

    this.lerpMode = mode;
  }

  public Palette (PApplet context, int[] colors, LerpMode mode) {
    super(context);

    this.colors = colors;
    this.lerpMode = mode;
  }

  public Palette (PApplet context, Color[] colors, LerpMode mode) {
    super(context);

    this.colors = Color.toIntArray(colors);
    this.lerpMode = mode;
  }


  /* Create palette from base color */

  public Palette (PApplet context, int baseColor, int size, Harmony harmony) {
    super(context);
    this.baseColor = baseColor;
    this.colors = new int[size];
    switch (harmony) {
      // TODO
    }
  }

  public Palette (PApplet context, Color baseColor, int size, Harmony harmony) {
    super(context);
    this.baseColor = baseColor.getRGB();
    this.colors = new int[size];
    switch (harmony) {
      // TODO
    }
  }

  public Palette (PApplet context, String baseColor, int size, Harmony harmony) {
    super(context);

    this.baseColor = Color.decode(baseColor).getRGB();
    this.colors = new int[size];
    switch (harmony) {
      // TODO
    }
  }


  /**
   * Get Color Methods
   */

  public int getColor () { return getColor(context.noise(context.frameCount, context.random(1))); }

  public int getColor (float v) { return getColor(v, lerpMode); }

  public int getColor (float v, LerpMode mode) { return getColor(v, colors.length, mode); }

  public int getColor (float v, float max, LerpMode mode) {
    v = constrainValue(v, max, mode);

    float c1 = context.floor(v * colors.length);
    float c2 = context.ceil(v * colors.length);

    if (c1 == c2) { c2++; }

    v = context.map(v, c1 / colors.length, c2 / colors.length, 0, 1);

    if (c2 == colors.length) c2 = 0;

    return context.lerpColor(colors[(int) c1], colors[(int) c2], v);
  }


  public int getColorDiscrete (float v) { return getColorDiscrete(v, lerpMode); }

  public int getColorDiscrete (float v, int size) { return getColorDiscrete(v, size, lerpMode); }

  public int getColorDiscrete (float v, LerpMode mode) { return getColorDiscrete(v, colors.length, mode); }

  public int getColorDiscrete (float v, int size, LerpMode mode) {
    return getColor(context.floor(v * size) / (float) size, mode);
  }


  /**
   * Special Color Methods
   */

  public void setForeground (int c) { this.foreground = c; }

  public void setBackground (int c) { this.background = c; }

  public int fg () { return foreground; }

  public int bg () { return background; }

  public int lightest () { int[] cs = colors.clone(); Arrays.sort(cs); return cs[cs.length - 1]; }

  public int darkest () { int[] cs = colors.clone(); Arrays.sort(cs); return cs[0]; }

  public void drawBackground () { drawBackground(255); }

  public void drawBackground (float alpha) {
    context.pushStyle();
    context.noStroke();
    context.fill(background, alpha);
    context.rect(0, 0, context.width, context.height);
    context.popStyle();
  }

  /**
   * Private Methods
   */

  private float constrainValue (float v, float max, LerpMode mode) {
    v = context.abs(v);

    if (v > 1) { v = (v % max) / max; }

    switch (mode) {
      case Linear:
        break;
      case Exp:
        v = context.map(context.exp(v), context.exp(0), context.exp(1), 0, 1); break;
      case Log:
        v = context.map(context.log(v * 10), context.log(1), context.log(10), 0, 1); break;
    }

    v = v % 1.0f;

    return context.max(0f, v);
  }

  /**
   * Debug Methods
   */

  public void draw () { draw(40); }

  public void draw (float height) {
    context.pushStyle();
    context.noFill();
    context.strokeWeight(1);
    for (int x = 0; x < context.width; x++) {
      float v = (float) x / context.width;
      int c = getColorDiscrete(v, 36);
      context.stroke(c);
      context.line(x, 0, x, context.height);
    }
    context.popStyle();
  }
}
