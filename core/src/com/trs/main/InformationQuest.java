/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.trs.main.worldobjects.MovingNpc;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 *
 * What is this Quest?
 * 
 * This quest is simply about talking. For tesing purposes this Quest only safes one certain Npc.
 * As soon as you are talking to him this quest is counting as finished
 * 
 */
public class InformationQuest extends Quest{
    
    private int[] informationNpcId;
    private int[] informationNpcMapId;
    private boolean[] talked;
    
    private int questId;
    
    
    private boolean rightOrderRequired;
    
    public InformationQuest(int questId, String questText, int[] informationNpcId, int[] informationNpcMapId, boolean rightOrderRequired, String name){
        
        
        this.questId = questId;
        
        this.informationNpcId = informationNpcId;
        this.informationNpcMapId = informationNpcMapId;
        
        talked = new boolean[informationNpcId.length];
        for(int i = 0; i < informationNpcId.length; i++){
            talked[i] = false;
        }
        this.rightOrderRequired = rightOrderRequired;
        
        setQuestName(name);
        setQuestText(questText);
        
    }
    
    public void talk(MovingNpc a){
        for(int i = 0; i < getTalked().length; i++){
            if(getInformationNpcId()[i] == a.id && getInformationNpcMapId()[i] == a.mapId){
                getTalked()[i] = true;
                break;
            }
        }
    }
    
    
    public boolean hasSpecialDialogue(int NpcId, int mapId){
        if(!isFinished()){
            if(!isRightOrderRequired()){
                for(int i = 0; i < getTalked().length; i++){
                    if(getInformationNpcId()[i] == NpcId && getInformationNpcMapId()[i] == mapId && !getTalked()[i]){
                        return true;
                    }
                }
            }
            else{
                int nextToTalk = 0;
                for(int i = 0; i < getTalked().length; i++){
                    if(!getTalked()[i]){
                        nextToTalk = i;
                        break;
                    }
                }
                if(getInformationNpcId()[nextToTalk] == NpcId && getInformationNpcMapId()[nextToTalk] == mapId){
                    return true;
                } 
            }
            return false;
        }
        else return false;
    }
            
    public String getDialoguePath(int NpcId, int mapId){
        return "quests/informationQuests/"+getQuestId()+"/dialogues/map"+mapId+"/npc"+NpcId+"/dialogue.txt";
    }
    
    @Override
    public void updateQuest() {
        if(!isFinished()){
            setFinished(true);
            for(int i = 0; i < getTalked().length; i++){
                if(!getTalked()[i]){ 
                    setFinished(false);
                    break;
                }
            }
        }
    }

    @Override
    public void print(){
        for(int i = 0; i < getInformationNpcId().length; i++){
            System.out.print(getInformationNpcId()[i] + " ");
            System.out.print(getInformationNpcMapId()[i] + " ");
            System.out.print(getTalked()[i] + " ");
            System.out.println();
        }
    }
    
    @Override
    public boolean finished() {
        return isFinished();
    }

    /**
     * @return the informationNpcId
     */
    public int[] getInformationNpcId() {
        return informationNpcId;
    }

    /**
     * @param informationNpcId the informationNpcId to set
     */
    public void setInformationNpcId(int[] informationNpcId) {
        this.informationNpcId = informationNpcId;
    }

    /**
     * @return the informationNpcMapId
     */
    public int[] getInformationNpcMapId() {
        return informationNpcMapId;
    }

    /**
     * @param informationNpcMapId the informationNpcMapId to set
     */
    public void setInformationNpcMapId(int[] informationNpcMapId) {
        this.informationNpcMapId = informationNpcMapId;
    }

    /**
     * @return the talked
     */
    public boolean[] getTalked() {
        return talked;
    }

    /**
     * @param talked the talked to set
     */
    public void setTalked(boolean[] talked) {
        this.talked = talked;
    }

    /**
     * @return the questId
     */
    public int getQuestId() {
        return questId;
    }

    /**
     * @param questId the questId to set
     */
    public void setQuestId(int questId) {
        this.questId = questId;
    }

    /**
     * @return the rightOrderRequired
     */
    public boolean isRightOrderRequired() {
        return rightOrderRequired;
    }

    /**
     * @param rightOrderRequired the rightOrderRequired to set
     */
    public void setRightOrderRequired(boolean rightOrderRequired) {
        this.rightOrderRequired = rightOrderRequired;
    }
    
    

}
