package vanity.actors;

import processing.core.PVector;
import vanity.core.VanityApplet;

import java.util.ArrayList;

import static processing.core.PConstants.*;

public class VertexDancer2D extends Dancer2D {
  private int nVertices;
  private ArrayList<PVector> vertices;
  private ArrayList<Float>   angles;

  public VertexDancer2D (VanityApplet context, PVector origin, PVector direction, PVector scale, PVector speed,
                         float angularSpeed, float lifetime) {
    super(context, origin, direction, scale, speed, angularSpeed, lifetime);

    generateAngles();
    generateVertices();

    movement.transform.rotate(context.random(TWO_PI));
  }

  private void generateAngles () {
    float total = 0;

    vertices = new ArrayList<>();
    angles = new ArrayList<>();
    angles.add(0.0f);

    nVertices = 0;

    while (total < TWO_PI) {
      float r = context.random(PI);
      angles.add(r);
      total += r;
      nVertices++;
    }

    float lastAngle = angles.get(nVertices - 1);
    angles.set(nVertices - 1, total - TWO_PI);
  }

  private void generateVertices () {
    for (int i = 0; i < angles.size(); i++) {
      float angle = angles.get(i);

      float x = context.cos(angle) * movement.transform.scale.x;
      float y = context.sin(angle) * movement.transform.scale.y;

      x -= context.abs(context.randomGaussian() * movement.transform.scale.x);
      y -= context.abs(context.randomGaussian() * movement.transform.scale.y);

      vertices.add(new PVector(x, y));
    }
  }

  public void update () {
    move();
    draw();
  }

  public void move () {
    // movement.rotateDirection(context.randomGaussian() * movement.angularSpeed);
    movement.transform.position.add(1, 1);
    // movement.move();
  }

  public void draw () {
    movement.transform.beginMatrix();
    context.beginShape();
    for (PVector v : vertices) { context.vertex(v.x, v.y); }
    context.endShape(CLOSE);
    movement.transform.endMatrix();
  }
}
