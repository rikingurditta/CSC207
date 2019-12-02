package com.group0565.tsu.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.Grade;

import java.util.HashMap;

public class GradeRenderer extends MenuObject {
  private HashMap<Grade, ThemedPaintCan> paints = new HashMap<>();
  private Source<Grade> gradeSource;
  private String last = "";

  public GradeRenderer(Vector size, Source<Grade> gradeSource) {
    super(size);
    this.gradeSource = gradeSource;
  }

  public GradeRenderer(Source<Grade> gradeSource) {
    super();
    this.gradeSource = gradeSource;
  }

  @Override
  public void init() {
    super.init();
    for (Grade grade : Grade.values()) {
      ThemedPaintCan paint = grade.getPaintCan().clone();
      paints.put(grade, paint);
      paint.setTextSize(getSize().getY());
    }
  }

  @Override
  public void update(long ms) {
    super.update(ms);
    Grade grade = gradeSource.getValue();
    if (grade == null) return;
    if (!last.equals(gradeSource.getValue().getString())) {
      ThemedPaintCan paint = paints.get(grade);
      paint.setTextSize(getSize().getY());
      this.setSize(
          getSize()
              .newSetX(paint.getPaint().getTextBounds(gradeSource.getValue().getString()).getX()));
    }
    last = gradeSource.getValue().getString();
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    Grade grade = gradeSource.getValue();
    if (grade == null) return;
    canvas.drawText(grade.getString(), pos, paints.get(grade));
  }
}
