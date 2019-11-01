package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.engine.gameobjects.Button;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.Grade;

class HistoryDisplayer extends Button {
    private static float CHEAT_SIZE = 50;
    private SessionHitObjects objects;
    private Bitmap cheat;
    private Paint rim;
    private Paint center;
    private Paint letterPaint;
    private Paint datePaint;
    private Paint scorePaint;
    private Paint comboPaint;
    private StatsMenu statsMenu;

    public HistoryDisplayer(StatsMenu statsMenu, Vector position, Vector size, SessionHitObjects objects) {
        super(position, size);
        this.statsMenu = statsMenu;
        this.objects = objects;
        this.rim = new Paint();
        this.rim.setARGB(255, 0, 0, 0);
        this.center = new Paint();
        this.letterPaint = new Paint();
        this.letterPaint.setTextSize(size.getY() * 0.75f);
        this.datePaint = new Paint();
        this.datePaint.setTextSize(size.getY() * 0.2f);
        this.datePaint.setARGB(255, 0, 0, 0);
        this.scorePaint = new Paint();
        this.scorePaint.setTextSize(size.getY() * 0.45f);
        this.scorePaint.setARGB(255, 0, 0, 0);
        this.comboPaint = new Paint();
        this.comboPaint.setTextSize(size.getY() * 0.45f);
        this.comboPaint.setARGB(255, 0, 0, 0);
    }

    @Override
    public void init() {
        this.cheat = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(21, 0);
        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = getAbsolutePosition().getX();
        float y = getAbsolutePosition().getY();
        float w = getSize().getX();
        float h = getSize().getY();
        canvas.drawRect(x, y, x + w, y + h, rim);
        if (getGlobalPreferences().theme == Themes.LIGHT) {
            center.setARGB(255, 255, 255, 255);
            this.datePaint.setARGB(255, 0, 0, 0);
            this.scorePaint.setARGB(255, 0, 0, 0);
            this.comboPaint.setARGB(255, 0, 0, 0);
        } else if (getGlobalPreferences().theme == Themes.DARK) {
            center.setARGB(255, 0, 0, 0);
            this.datePaint.setARGB(255, 255, 255, 255);
            this.scorePaint.setARGB(255, 255, 255, 255);
            this.comboPaint.setARGB(255, 255, 255, 255);
        }
        if (this.objects == statsMenu.getSelectedObject())
            this.center.setARGB(255, 128, 128, 128);
        canvas.drawRect(x + 10, y + 10, x + w - 10, y + h - 10, center);
        if (objects != null) {
            {
                Grade grade = Grade.num2Grade(objects.getGrade());
                letterPaint.setARGB(255, grade.getR(), grade.getG(), grade.getB());
                String gradeStr = grade.getString();
                Rect gradeRect = new Rect();
                this.letterPaint.getTextBounds(gradeStr, 0, gradeStr.length(), gradeRect);
                canvas.drawText(gradeStr, x + 10, y + 15 + gradeRect.height(), letterPaint);
                if (objects.hasCheats()) {
                    canvas.drawBitmap(cheat, null, new RectF(x + 10 + gradeRect.width() + 10,
                            y + 15 + gradeRect.height() - CHEAT_SIZE, x + 10 + gradeRect.width() + 10 + CHEAT_SIZE, y + 15 + gradeRect.height()), null);
                }
            }
            canvas.drawText(objects.getDatetime(), x + 10, y + getSize().getY() - 15, datePaint);
            {
                String scoreStr = String.valueOf(objects.getScore());
                Rect scoreRect = new Rect();
                this.scorePaint.getTextBounds(scoreStr, 0, scoreStr.length(), scoreRect);
                canvas.drawText(scoreStr, x + getSize().getX() - scoreRect.width() - 25, y + 15 + scoreRect.height(), scorePaint);
            }
            {
                String comboStr = String.valueOf(objects.getMaxCombo());
                Rect comboRect = new Rect();
                this.scorePaint.getTextBounds(comboStr, 0, comboStr.length(), comboRect);
                canvas.drawText(comboStr, x + getSize().getX() - comboRect.width() - 25, y + getSize().getY() - 15, comboPaint);
            }
        }
    }

    public SessionHitObjects getObjects() {
        return objects;
    }

    public void setObjects(SessionHitObjects objects) {
        this.objects = objects;
    }
}
