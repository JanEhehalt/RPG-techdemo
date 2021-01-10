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
    
    int[] informationNpcId;
    int[] informationNpcMapId;
    boolean[] talked;
    
    int questId;
    
    boolean rightOrderRequired;
    
    public InformationQuest(int questId, String questText, int[] informationNpcId, int[] informationNpcMapId, boolean rightOrderRequired){
        
        this.questId = questId;
        
        this.informationNpcId = informationNpcId;
        this.informationNpcMapId = informationNpcMapId;
        
        talked = new boolean[informationNpcId.length];
        for(int i = 0; i < informationNpcId.length; i++){
            talked[i] = false;
        }
        this.rightOrderRequired = rightOrderRequired;
        
        this.questText = questText;
        
    }
    
    public void talk(MovingNpc a){
        for(int i = 0; i < talked.length; i++){
            if(informationNpcId[i] == a.id && informationNpcMapId[i] == a.mapId){
                talked[i] = true;
                break;
            }
        }
    }
    
    
    public boolean hasSpecialDialogue(int NpcId, int mapId){
        if(!finished){
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
        else return false;
    }
            
    public String getDialoguePath(int NpcId, int mapId){
        return "quests/informationQuests/"+questId+"/dialogues/map"+mapId+"/npc"+NpcId+"/dialogue.txt";
    }
    
    @Override
    public void updateQuest() {
        if(!finished){
            finished = true;
            for(int i = 0; i < talked.length; i++){
                if(!talked[i]){ 
                    finished = false;
                    break;
                }
            }
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
    public boolean finished() {
        return finished;
    }

}
