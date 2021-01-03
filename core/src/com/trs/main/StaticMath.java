package com.trs.main;

/**
 *
 * @author jonathan
 */
public class StaticMath {
    
    public static float calculateAngle(float xPos1, float yPos1, float xPos2, float yPos2){
        float deltaX = xPos2 - xPos1;
        float deltaY = yPos2 - yPos1;

        double alpha;
        if(deltaY == 0){
            if(deltaX > 0){
                alpha = 0;
            }
            else{
                alpha = Math.PI;
            }
        }
        else if(deltaX == 0 && deltaY >= 0){
            alpha = Math.PI / 2;
        }
        else if(deltaX == 0 && deltaY < 0){
            alpha = Math.PI / -2;
        }
        else{
            alpha = Math.abs(Math.atan(deltaY / deltaX));

            if(deltaX < 0 && deltaY < 0){
                alpha = Math.PI + alpha;
            }
            else if(deltaX < 0 && deltaY > 0){
                alpha = Math.PI - alpha;
            }
            else if(deltaX > 0 && deltaY < 0){
                alpha = 2*Math.PI - alpha;
            }
        }
        
        return (float) alpha;
    }
    
    public static float calculateDistance(float xPos1, float yPos1, float xPos2, float yPos2){
    	float deltaX = xPos2 - xPos1;
        float deltaY = yPos2 - yPos1;
        
        float distance;
        double angle = calculateAngle(xPos1, yPos1, xPos2, yPos2);
        
        if(angle == 0 || angle == Math.PI) {
        	distance = deltaX;
        }
        else {
        	distance = Math.abs(deltaX / (float) Math.cos(angle));
        }
        
        return (float) Math.sqrt((float) Math.pow(deltaX, 2) + (float) Math.pow(deltaY, 2));
        //return distance;
    }
}
