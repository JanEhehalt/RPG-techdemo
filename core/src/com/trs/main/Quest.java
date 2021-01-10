/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author janeh
 */
public abstract class Quest {
    
    String questText;
    boolean finished;
    Quest followingQuest;
    
    
    
    abstract public void updateQuest();
    public abstract boolean finished();
    public abstract void print();
}
