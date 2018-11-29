package vanity.core;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.data.FloatDict;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static processing.core.PConstants.TWO_PI;

public class Texturizer extends VanityBehaviour {

  private PGraphics pg;
  private FloatDict settings;

  private int mode = 0;

  private static final String[] MODES = new String[] { "grain", "clouds", "stains", "fibers" };

  public static final int GRAIN  = 1 << 0;
  public static final int CLOUDS = 1 << 1;
  public static final int STAINS = 1 << 2;
  public static final int FIBERS = 1 << 3;

  private static final float DEFAULT_ALPHA = 20.0f;
  private static final int   DEFAULT_MODE = 0;


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
    float scale     = settings.get("stains_scale", pg.height / 5f);
    float minStainsPerGroup  = settings.get("stains_minparts", 1f);
    float maxStainsPerGroup  = settings.get("stains_maxparts", 5f);
    float grayscale = settings.get("stains_grayscale", 0f);

    float minVertices  = settings.get("stains_minverts", 6f);
    float maxVertices  = settings.get("stains_maxverts", 18f);

    float padding = settings.get("stains_padding", pg.height / 50);

    float minDistance = settings.get("stains_mindistance", pg.height / 10);

    float randomness = settings.get("stains_randomness", 0.2f);

    PVector lastGroupCenter = new PVector();

    pg.noStroke();

    while (remaining > 0) {
      PVector groupCenter = new PVector(context.random(-pg.width  / 2 - padding, pg.width  / 2 - padding),
                                        context.random(-pg.height / 2 - padding, pg.height / 2 - padding));

      boolean firstStainGroup = remaining == count;
      boolean distanceOk      = context.dist(lastGroupCenter.x, lastGroupCenter.y, groupCenter.x, groupCenter.y) > minDistance;
      boolean positiveValue   = context.noise(groupCenter.x / pg.width, groupCenter.y / pg.height) > 0.5;

      if ((firstStainGroup || distanceOk) && positiveValue) {
        lastGroupCenter = groupCenter.copy();

        pg.pushMatrix();
        pg.translate(pg.width / 2 + groupCenter.x, pg.height / 2 + groupCenter.y);

        int stainsInGroup = (int) context.random(minStainsPerGroup, maxStainsPerGroup);

        for (int stain = 0; stain < stainsInGroup; stain++) {
          float size        = context.random(1, 3) * (stain == 0 ? scale : scale / 5);
          float mappedAlpha = context.map(size, 1, scale * 3, 2, alpha);

          int vertsNumber = (int) context.random(minVertices, maxVertices);

          PVector stainPosition = new PVector(context.randomGaussian() * size, context.randomGaussian() * size);

          ArrayList<PVector> vertices = new ArrayList<>();
          for (int i = 0; i < vertsNumber; i++) {
            float angle    = TWO_PI / vertsNumber * i;
            float radius   = size / 2 * randomness;
            PVector vertex = new PVector(context.cos(angle) * radius, context.sin(angle) * radius);
            vertex.add(
                     context.randomGaussian() * radius / 10,
                     context.randomGaussian() * radius / 10);
            vertices.add(vertex);
          }

          pg.translate(stainPosition.x, stainPosition.y);
          pg.fill(grayscale, mappedAlpha);
          pg.beginShape();

          for (PVector v : vertices) { pg.curveVertex(v.x, v.y); }
          for (int i = 0; i < 4; i++) { pg.curveVertex(vertices.get(i).x, vertices.get(i).y); }

          pg.endShape();
        }

        pg.popMatrix();

        remaining--;
      }
    }
  }

  private void texturizeFibers () {
    float alpha   = settings.get("fibers_alpha", DEFAULT_ALPHA);
    float minStep = settings.get("fibers_minstep", 1);
    float maxStep = settings.get("fibers_maxstep", 10);
    float minSize = settings.get("fibers_minsize", 5);
    float maxSize = settings.get("fibers_maxsize", 50);
    float scale   = settings.get("fibers_scale", 200);

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


  public void addMode(int mode) {
    this.mode |= mode;
  }

  public void addSetting (int mode, String setting, float value) {
    settings.add(String.join("_", MODES[modeToIndex(mode)], setting), value);
  }

  public void removeSetting (int mode, String setting) {
    settings.remove(String.join("_", MODES[modeToIndex(mode)], setting));
  }

  private int modeToIndex (int mode) {
    return (int) Math.round(Math.log(mode) / Math.log(2));
  }
}
