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
    
    private String questName;
    private String questText;
    private boolean finished;
    private Quest followingQuest;
    
    
    
    abstract public void updateQuest();
    public abstract boolean finished();
    public abstract void print();

    /**
     * @return the questName
     */
    public String getQuestName() {
        return questName;
    }

    /**
     * @param questName the questName to set
     */
    public void setQuestName(String questName) {
        this.questName = questName;
    }

    /**
     * @return the questText
     */
    public String getQuestText() {
        return questText;
    }

    /**
     * @param questText the questText to set
     */
    public void setQuestText(String questText) {
        this.questText = questText;
    }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * @return the followingQuest
     */
    public Quest getFollowingQuest() {
        return followingQuest;
    }

    /**
     * @param followingQuest the followingQuest to set
     */
    public void setFollowingQuest(Quest followingQuest) {
        this.followingQuest = followingQuest;
    }
    
    
}
