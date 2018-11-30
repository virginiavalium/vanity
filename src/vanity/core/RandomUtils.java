package vanity.core;

import processing.core.PVector;

import java.util.Random;

public class RandomUtils {
  private static Random r;

  private static Random r () {
    if (r == null) r = new Random();
    return r;
  }

  public static boolean chance (float n) {
    return r().nextFloat() < n / 100;
  }

  public static PVector vector2D() {
    float x = r().nextFloat();
    float y = r().nextFloat();

    return new PVector(x, y);
  }

  public static PVector vector3D() {
    float x = r().nextFloat();
    float y = r().nextFloat();
    float z = r().nextFloat();

    return new PVector(x, y, z);
  }
}
