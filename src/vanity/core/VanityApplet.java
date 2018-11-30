package vanity.core;

import processing.core.PApplet;
import processing.data.FloatDict;

import java.util.HashMap;

public abstract class VanityApplet extends PApplet {
  public static boolean DEBUG = false;

  private Saver saver;

  private HashMap<String, Palette> palettes;
  private Palette palette;
  private String chosenPalette;

  private Texturizer texturizer;
  private FloatDict texturizerSettings;


  public void initialize () {
    saver = new Saver(this);

    palettes = new HashMap<>();

    texturizerSettings = new FloatDict();
    texturizer = new Texturizer(this, texturizerSettings);
  }

  public void init() {
    initialize();
  }

  public void settings () {
    super.settings();
  }


  public void addPalette (String name, String colorsString) {
    palettes.put(name, new Palette(this, colorsString));

    if (palettes.size() == 1) choosePalette(name);
  }

  public void removePalette(String name) {
    palettes.remove(name);
  }

  public void choosePalette (String name) {
    chosenPalette = name;
  }

  public void debugPalette () {
    getPalette().draw(40);
  }

  public Palette getPalette() {
    return palettes.get(chosenPalette);
  }


  public void addTexture(int mode) {
    texturizer.addMode(mode);
  }

  public void addTextureSetting(int mode, String setting, float value) {
    texturizer.addSetting(mode, setting, value);
  }

  public void texturize () { texturizer.texturize(); }


  public void save () {
    saver.save();
  }

  public void saveAs (String filename) {
    saver.save(filename);
  }

  public void saveAs (String filename, String extension) {
    saver.save(filename, extension);
  }


  public void translateToCenter () {
    translate(width / 2.0f, height / 2.0f);
  }
}
