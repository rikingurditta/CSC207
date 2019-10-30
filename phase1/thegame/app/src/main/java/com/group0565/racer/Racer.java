package com.group0565.racer;

import com.example.thegame.TheGameApplication;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.errorHandlers.ExceptionErrorHandler;
import com.group0565.math.Vector;

import org.xml.sax.ErrorHandler;

public class Racer extends GameObject {

    String appearance = "A";

    Racer(GameObject parent, Vector position, boolean relative, double z) {
        super(parent, position, relative, z);
    }
}
