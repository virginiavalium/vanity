package vanity.core;

import java.awt.color.ColorSpace;

public class Color extends java.awt.Color {
  public Color (int r, int g, int b) {
    super(r, g, b);
  }

  public Color (int r, int g, int b, int a) {
    super(r, g, b, a);
  }

  public Color (int rgb) {
    super(rgb);
  }

  public Color (int rgba, boolean hasalpha) {
    super(rgba, hasalpha);
  }

  public Color (float r, float g, float b) {
    super(r, g, b);
  }

  public Color (float r, float g, float b, float a) {
    super(r, g, b, a);
  }

  public Color (ColorSpace cspace, float[] components, float alpha) {
    super(cspace, components, alpha);
  }

  public static int[] toIntArray (Color[] original) {
    int[] array = new int[original.length];
    for (int i = 0; i < original.length; i++) { array[i] = original[i].getRGB(); }
    return array;
  }

  public static Color[] toColorArray (int[] original) {
    Color[] array = new Color[original.length];
    for (int i = 0; i < original.length; i++) { array[i] = new Color(original[i]); }
    return array;
  }
}
