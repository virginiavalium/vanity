package vanity.core;

import java.util.Random;

public class Utils {
  public static boolean chance (float n) {
    return new Random().nextFloat() < n / 100;
  }
}
