import processing.core.PApplet;
import vanity.core.Palette;

import java.util.HashMap;

public class MainApp extends PApplet {
  HashMap<String, Palette> palettes;
  Palette palette;

  public static void main (String[] args) {
    PApplet.main("MainApp", args);
  }

  public void settings () {
    size(800, 600);
  }

  public void init () {
    final String paletteName = "test";

    palettes = new HashMap<String, Palette>();
    palettes.put("test", new Palette(this, "256eff-46237a-3ddc97-fcfcfc-ff495c"));

    palette = palettes.get(paletteName);
  }

  public void setup() {
    init();
    background(0);
  }

  public void draw () {
    palette.draw();
    stroke(palette.getColor(0.5f));
    line(0, 0, width, height);
  }
}
