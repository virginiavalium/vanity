import processing.core.PApplet;
import processing.data.FloatDict;
import vanity.core.Palette;
import vanity.core.Texturizer;
import vanity.core.VanityApplet;

import java.util.HashMap;

public class MainApp extends VanityApplet {

  public static void main (String[] args) {
    PApplet.main("MainApp", args);
  }

  public void settings () {
    size(800, 600);
  }

  public void init () {
    initialize();

    addPalette("test","256eff-46237a-3ddc97-fcfcfc-ff495c");
    // choosePalette("test"); // Not necessary since we only have one, it sets automatically
    addTexture(Texturizer.FIBERS);
    addTextureSetting(Texturizer.FIBERS, "alpha", 40);
  }

  public void setup() {
    init();

    background(0);
    debugPalette();
    texturize();
    save();
  }

  public void draw () {
  }
}
