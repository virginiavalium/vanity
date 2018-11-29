package vanity.core;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.FloatDict;

import static processing.core.PConstants.TWO_PI;

public class Texturizer extends ProcessingBehaviour {

  private PGraphics pg;
  private FloatDict settings;

  private int mode = 0;


  public static final int GRAIN  = 1 << 0;
  public static final int CLOUDS = 1 << 1;
  public static final int STAINS = 1 << 2;
  public static final int FIBERS = 1 << 3;

  private static final float DEFAULT_ALPHA = 20.0f;
  private static final int   DEFAULT_MODE = GRAIN | CLOUDS | STAINS | FIBERS;


  public Texturizer(PApplet context) {
    super(context);

    this.mode = DEFAULT_MODE;
    this.settings = new FloatDict();

    pg = context.createGraphics(context.width, context.height);
  }

  public Texturizer(PApplet context, int mode) {
    super(context);

    this.mode = mode;
    this.settings = new FloatDict();

    pg = context.createGraphics(context.width, context.height);
  }


  public Texturizer(PApplet context, FloatDict settings) {
    super(context);

    this.mode = DEFAULT_MODE;
    this.settings = settings;

    pg = context.createGraphics(context.width, context.height);
  }

  public Texturizer(PApplet context, int mode, FloatDict settings) {
    super(context);

    this.mode = mode;
    this.settings = settings;

    pg = context.createGraphics(context.width, context.height);
  }


  public void texturize () {
    pg.beginDraw();

    if (grain())  texturizeGrain();
    if (clouds()) texturizeClouds();
    if (stains()) texturizeStains();
    if (fibers()) texturizeFibers();

    pg.endDraw();
    context.image(pg, 0, 0);
  }

  private void texturizeGrain () {
    float alpha   = settings.get("grain_alpha", DEFAULT_ALPHA);
    float spacing = settings.get("grain_spacing", 1.0f);

    boolean with_offset = settings.get("grain_offset", 0.0f) == 1f;

    for (int x = 0; x < pg.width; x += spacing) {
      for (int y = 0; y < pg.height; y += spacing) {
        float noiseValue = with_offset ? context.noise(x, y, context.random(pg.height)) : context.random(1f);
        pg.set(x, y, context.color(255f * noiseValue, alpha));
      }
    }
  }

  private void texturizeClouds () {
    float alpha      = settings.get("clouds_alpha", DEFAULT_ALPHA);
    float smoothness = settings.get("clouds_smoothness", 50.0f);

    float z = context.random(pg.height);

    for (int x = 0; x < pg.width; x++) {
      for (int y = 0; y < pg.height; y++) {
        float noiseValue = context.noise(x / smoothness, y / smoothness, z / smoothness);
        pg.set(x, y, context.color(255f * noiseValue, alpha));
      }
    }
  }

  private void texturizeStains () {
    float alpha     = settings.get("stains_alpha", DEFAULT_ALPHA);
    float count     = settings.get("stains_count", 5f);
    float remaining = settings.get("stains_count", 5f);
    float scale     = settings.get("stains_scale", 20f);
    float minParts  = settings.get("stains_min_parts", 3f);
    float maxParts  = settings.get("stains_max_parts", 8f);
    float grayscale = settings.get("stains_grayscale", 0f);

    float maxVertices  = settings.get("stains_max_verts", 8f);
    float minVertices  = settings.get("stains_min_verts", 5f);

    float padding = settings.get("stains_padding_x", pg.height / 10);

    float minDistance = settings.get("stains_min_distance", pg.height / 20);

    float lastX = 0, lastY = 0;

    pg.noStroke();

    while (remaining > 0) {
      float x = pg.width  / 2 + context.random(0, pg.width  / 2 - padding);
      float y = pg.height / 2 + context.random(0, pg.height / 2 - padding);

      boolean distanceCondition = remaining == count || context.dist(lastX, lastY, x, y) > minDistance;
      boolean positiveValue     = context.noise(x / pg.height, y / pg.height) > 0.5;

      if (distanceCondition && positiveValue) {
        lastX = x;
        lastY = y;

        pg.pushMatrix();
        pg.translate(x, y);

        float parts = context.random(minParts, maxParts);

        for (int j = 0; j < parts; j++) {
          float size        = context.random(1, 3) * (j == 0 ? scale : scale / 5);
          float mappedAlpha = context.map(size, 0, 30, context.min(alpha, DEFAULT_ALPHA), context.max(alpha, 64));

          float ix = context.randomGaussian() * size;
          float iy = context.randomGaussian() * size;

          pg.translate(ix, iy);
          pg.fill(grayscale, mappedAlpha);
          pg.beginShape();

          float step = TWO_PI / context.random(minVertices, maxVertices);
          float a = 0;

          pg.curveVertex(context.cos(a) * size, context.sin(a) * size);
          for (; a < TWO_PI; a += step) {
            float rx = context.randomGaussian() * size / 5;
            float ry = context.randomGaussian() * size / 5;

            float posx = rx + context.cos(a) * size;
            float posy = ry + context.sin(a) * size;

            pg.curveVertex(posx, posy);
          }
          pg.curveVertex(context.cos(a) * size, context.sin(a) * size);

          pg.endShape(context.CLOSE);
        }

        pg.popMatrix();

        remaining--;
      }
    }
  }

  private void texturizeFibers () {
    float alpha   = settings.get("fibers_alpha");
    float minStep = settings.get("fibers_minstep");
    float maxStep = settings.get("fibers_maxstep");
    float minSize = settings.get("fibers_minsize");
    float maxSize = settings.get("fibers_maxsize");
    float scale   = settings.get("fibers_scale");

    pg.strokeWeight(.8f);

    for (int x = 0; x < pg.width; x += context.random(minStep, maxStep)) {
      for (int y = 0; y < pg.height; y += context.random(minStep, maxStep)) {
        if (Utils.chance(85)) {
          float positionX = x;
          float positionY = y;
          float iterations = context.random(5);
          // float scale  = random(200, 300);
          float size   = context.random(minSize, maxSize);
          float offset = context.random(TWO_PI);
          pg.beginShape(context.LINES);

          for (int i = 0; i < iterations; i++) {
            int original     = context.get(x, y);
            int blackOrWhite = context.color(Utils.chance(50) ? 0 : 255); // TODO refactor into method in Utils
            pg.stroke(context.lerpColor(original, blackOrWhite, context.random(.3f, .7f)), alpha);
            float noiseValue = offset + context.noise(positionX / scale, positionY / scale) * TWO_PI;
            float noiseX = positionX + context.cos(noiseValue) * size;
            float noiseY = positionY + context.sin(noiseValue) * size;
            pg.vertex(positionX, positionY);
            pg.vertex(noiseX, noiseY);
            positionX = noiseX;
            positionY = noiseY;
          }
          pg.endShape();
        }
      }
    }
  }


  private boolean grain() {
    return (mode & GRAIN) == GRAIN;
  }

  private boolean clouds() {
    return (mode & CLOUDS) == CLOUDS;
  }

  private boolean stains() {
    return (mode & STAINS) == STAINS;
  }

  private boolean fibers() {
    return (mode & FIBERS) == FIBERS;
  }
}
