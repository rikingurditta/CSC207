package com.group0565.bomberGame.bombs;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public abstract class Bomb extends GameObject {

    private int strength  = 1;
    private int simotanousNumBomb = 1;


    public Bomb(Vector position, int z) { super(position, z); }

    public void increaseStregth(){
        strength += 1;
    }
    public void decreaseStregth(){
        strength -= 1;
    }
    public void increasesimotanousNumBomb(){
        simotanousNumBomb += 1;
    }
    public void decreasesimotanousNumBomb(){
        simotanousNumBomb -= 1;
    }
}
