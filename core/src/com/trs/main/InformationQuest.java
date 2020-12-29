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
 * What is this Quest?
 * 
 * This quest is simply about talking. For tesing purposes this Quest only safes one certain Npc.
 * As soon as you are talking to him this quest is counting as finished
 * 
 */
public class InformationQuest extends Quest{
    
    int[] informationNpcId;
    int[] informationNpcMapId;
    boolean[] talked;
    
    int questId;
    
    boolean rightOrderRequired;
    
    InformationQuest(int questId, String questText/*, Quest[] followingQuest*/, int[] informationNpcId, int[] informationNpcMapId, boolean rightOrderRequired){
        
        this.questId = questId;
        
        this.informationNpcId = informationNpcId;
        this.informationNpcMapId = informationNpcMapId;
        
        talked = new boolean[informationNpcId.length];
        for(int i = 0; i < informationNpcId.length; i++){
            talked[i] = false;
        }
        this.rightOrderRequired = rightOrderRequired;
        
        this.questText = questText;
        
        /* TODO: More quest inside on Quest-string
        
        if(followingQuest.length > 1){
            Quest[] nextQuests = new Quest[followingQuest.length-1];
            for(int i = 1; i < followingQuest.length; i++){
                nextQuests[i-1] = followingQuest[i];
            }
            this.followingQuest = new InformationQuest();
        }
        */
    }
    
    
    boolean hasSpecialDialogue(int NpcId, int mapId){
        if(!rightOrderRequired){
            for(int i = 0; i < talked.length; i++){
                if(informationNpcId[i] == NpcId && informationNpcMapId[i] == mapId && !talked[i]){
                    return true;
                }
            }
        }
        else{
            int nextToTalk = 0;
            for(int i = 0; i < talked.length; i++){
                if(!talked[i]){
                    nextToTalk = i;
                    break;
                }
            }
            if(informationNpcId[nextToTalk] == NpcId && informationNpcMapId[nextToTalk] == mapId){
                return true;
            } 
        }
        return false;
    }
            
    String getDialoguePath(int NpcId, int mapId){
        return "quests/informationQuests/"+questId+"/dialogues/map"+mapId+"/npc"+NpcId+"/dialogue.txt";
    }
    
    @Override
    void updateQuest(Array<Actor> actors) {
        if(!finished){
            if(rightOrderRequired){
                int nextToTalk = 0;
                for(int i = 0; i < talked.length; i++){
                    if(!talked[i]){
                        nextToTalk = i;
                        break;
                    }
                }
                for(Actor a : actors){
                    if(a instanceof MovingNpc){
                        if(((MovingNpc)a).id == informationNpcId[nextToTalk] && ((MovingNpc)a).mapId == informationNpcMapId[nextToTalk]){
                            if(((MovingNpc)a).currentlyTalking){
                                talked[nextToTalk] = true;
                                break;
                            }
                        }
                    }
                }
            }
            else{
                for(Actor a : actors){
                    if(a instanceof MovingNpc){
                        for(int i = 0; i < informationNpcId.length; i++){
                            if(((MovingNpc)a).id == informationNpcId[i] && ((MovingNpc)a).mapId == informationNpcMapId[i]){
                                if(((MovingNpc)a).currentlyTalking){
                                    talked[i] = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            boolean finished = true;
            for(int i = 0; i < talked.length; i++){
                if(!talked[i]){ 
                    finished = false;
                    break;
                }
            }
            this.finished = finished;
        }
    }

    public void print(){
        for(int i = 0; i < informationNpcId.length; i++){
            System.out.print(informationNpcId[i] + " ");
            System.out.print(informationNpcMapId[i] + " ");
            System.out.print(talked[i] + " ");
            System.out.println();
        }
    }
    
    @Override
    boolean finished() {
        return finished;
    }

}
