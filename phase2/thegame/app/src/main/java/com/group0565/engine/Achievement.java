package com.group0565.engine;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.engine.render.ClippedTextRenderer;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.PaintCan;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.LinearTemporalInterpolator;
import com.group0565.math.Vector;
import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

/**
 * An Achievement
 */
public class Achievement extends GameMenu {
    /**Set of Assets*/
    private static final String SET = "Engine";
    //Constants for Paint Assets
    private static final String UNLOCK_STRING_NAME = "AchievementUnlock";
    private static final String PAINT_FOLDER = "Achievements.";
    private static final String BG_Paint = PAINT_FOLDER + "Background";
    private static final String TEXT_Paint = PAINT_FOLDER + "Text";
    //Timing Constants
    private static final long TRANSITION_TIME = 750;
    private static final long FDOWN_TIME = TRANSITION_TIME;
    private static final long UNLOCK_TIME = FDOWN_TIME + 1000;
    private static final long NAME_TIME = UNLOCK_TIME + 1000;
    private static final long FUP_TIME = NAME_TIME + TRANSITION_TIME;

    //Icon Constants
    private static final String ICON_NAME = "Icon";
    //String Constants
    private static final String STRING_NAME = "String";
    private static final float STRING_HBUFFER = 25;

    /**Internal name of the Achievement**/
    private String name;
    /**Name of the achievement to display**/
    private String displayName;
    /**Description of the achievement**/
    private String description;
    /**Whether or not the achievement is hidden**/
    private boolean hidden;

    /**Data to load bitmap with*/
    private String set = null;
    private String sheet = null;
    private int tilex = 0;
    private int tiley = 0;


    private LinearTemporalInterpolator interpolator = null;
    private long timer;

    /**Whether or not this achievement has been unlocked*/
    private boolean unlocked = false;

    /**Paint for the background*/
    private PaintCan bgPaint;

    /**
     * Creates a new Achievement
     * @param size The size of the dropdown
     * @param name The internal name of the Achievement
     * @param displayName The displayed name of the Achievement
     * @param set The set to obtain Bitmap from
     * @param sheet The tilesheet to obtain Bitmap from
     * @param tilex The tile coordinate to obtain Bitmap from
     * @param tiley The tile coordinate to obtain Bitmap from
     */
    public Achievement(Vector size, String name, String displayName, String description, boolean hidden, String set, String sheet, int tilex, int tiley) {
        super(size);
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.hidden = hidden;
        this.set = set;
        this.sheet = sheet;
        this.tilex = tilex;
        this.tiley = tiley;
        this.setEnable(false);
    }

    /**
     * Initilize the Achievement
     */
    public void init(){
        super.init();
        GameAssetManager assetManager = getEngine().getGameAssetManager();
        this.bgPaint = new ThemedPaintCan(SET, BG_Paint).init(getGlobalPreferences(), assetManager);
        PaintCan textPaint = new ThemedPaintCan(SET, TEXT_Paint).init(getGlobalPreferences(), assetManager);
        Source<String> unlockText = new LanguageText(getGlobalPreferences(), assetManager, SET, UNLOCK_STRING_NAME);
        Source<Bitmap> icon = () -> assetManager.getTileSheet(set, sheet).getTile(tilex, tiley);
        // @formatter:off
        this.build()
            //Icon
            .add(ICON_NAME, new BitmapDrawer(new Vector(getSize().getY()), icon))
            .addAlignment(Left, THIS, Left)
            .addAlignment(VCenter, THIS, VCenter)

            //Text
            .add(STRING_NAME, new ClippedTextRenderer(() -> (timer < UNLOCK_TIME ? unlockText.getValue() : displayName), getSize().getX(), textPaint).build()
                .addOffset(STRING_HBUFFER, 0)
                .close())
            .addAlignment(Left, ICON_NAME, Right)
            .addAlignment(VCenter, ICON_NAME, VCenter)
        .close();
        // @formatter:on

        Vector restPos = new Vector((getEngine().getSize().getX() - getSize().getX()) / 2f, -getSize().getY());
        this.interpolator = new LinearTemporalInterpolator(restPos, new Vector(restPos.getX(), 0), TRANSITION_TIME);
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (isEnable())
            timer += ms;
        else
            timer = 0;
        long at;
        if (timer < FDOWN_TIME)
            at = timer;
        else if(timer < NAME_TIME)
            at = TRANSITION_TIME;
        else if(timer < FUP_TIME)
            at = TRANSITION_TIME - (timer - NAME_TIME);
        else{
            at = 0;
            setEnable(false);
        }
        setRelativePosition(interpolator.interpolate(at));
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name The new value for name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        canvas.drawRect(pos, size, bgPaint);
    }

    /**
     * Getter for displayName.
     *
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter for displayName.
     *
     * @param displayName The new value for displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter for unlocked.
     *
     * @return unlocked
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Getter for description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description
     *
     * @param description The new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for hidden
     *
     * @return hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Setter for hidden
     *
     * @param hidden The new value for hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Unlocks this Achievement
     */
    public void unlock() {
        if (!this.unlocked) {
            this.unlocked = true;
            this.setEnable(true);
        }
    }
}
