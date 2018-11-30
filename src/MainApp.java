import processing.core.PApplet;
import processing.core.PVector;
import vanity.actors.VertexDancer2D;
import vanity.core.Texturizer;
import vanity.core.RandomUtils;
import vanity.core.VanityApplet;

public class MainApp extends VanityApplet {

  VertexDancer2D[] dancers;

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
    addTexture(Texturizer.CLOUDS);
    addTexture(Texturizer.STAINS);
    addTextureSetting(Texturizer.STAINS, "alpha", 50);

    dancers = new VertexDancer2D[8];
    for (int i = 0; i < dancers.length; i++) {
      dancers[i] = new VertexDancer2D(this,
              new PVector(width / 2, height / 2),
              RandomUtils.vector2D(), new PVector(20, 20),
              new PVector(5, 5), 2f, 100000000000000f);
    }
  }

  public void setup() {
    init();
    background(255);
    translateToCenter();

    // generate();
  }

  public void generate () {
    texturize();
    save();
  }

  public void draw () {
    stroke(0);
    noFill();

    for (VertexDancer2D d : dancers) {
      d.update();
    }
  }

  public void keyPressed () {
    if (key == 'g') generate();
  }
}
