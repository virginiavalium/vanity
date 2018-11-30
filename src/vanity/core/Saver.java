package vanity.core;

import java.io.File;
import java.util.Date;

public class Saver extends VanityBehaviour {
  public static final String DEFAULT_FOLDER    = "generated";
  public static final String DEFAULT_EXTENSION = ".tif";
  public static final String DEFAULT_PREFIX    = "out";

  public Saver (VanityApplet context) {
    super(context);
  }

  public void save () {
    Date now = new Date();
    String filename  = String.format("%s_%d_%d", DEFAULT_PREFIX, (int)now.getTime(), (int)context.random(9999));
    String extension = DEFAULT_EXTENSION;
    save(filename, extension);
  }

  public void save (String filename) {
    File dir = new File(DEFAULT_FOLDER);
    if (!dir.exists()) { dir.mkdir(); }

    context.saveFrame(String.format("%s%s%s", DEFAULT_FOLDER, File.separatorChar, filename));
  }

  public void save (String filename, String extension) {
    save(String.format("%s.%s", filename, extension));
  }
}
