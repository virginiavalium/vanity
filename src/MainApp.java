import processing.core.PApplet;
import processing.data.FloatDict;
import vanity.core.Palette;
import vanity.core.Texturizer;

import java.util.HashMap;

public class MainApp extends PApplet {
  HashMap<String, Palette> palettes;
  Palette palette;
  Texturizer texturizer;

  public static void main (String[] args) {
    PApplet.main("MainApp", args);
  }

  public void settings () {
    size(800, 600);
  }

  public void init () {
    final String paletteName = "test";

    palettes = new HashMap<>();
    palettes.put("test", new Palette(this, "256eff-46237a-3ddc97-fcfcfc-ff495c"));

    palette = palettes.get(paletteName);

    FloatDict texturizerSettings = new FloatDict();

    texturizer = new Texturizer(this, Texturizer.STAINS, texturizerSettings);
  }

  public void setup() {
    init();
    background(0);
    palette.draw();
    texturizer.texturize();
  }

  public void draw () {
  }
}
