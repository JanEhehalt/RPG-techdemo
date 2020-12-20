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
            if(deltaX < 0){
                alpha = Math.PI;
            }
            else{
                alpha = 0;
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
    
    public static double calculateDistance(int xPos1, int yPos1, int xPos2, int yPos2, double angle){
        float deltaX = xPos2 - xPos1;
        float deltaY = yPos2 - yPos1;
        
        double distance = Math.abs((deltaY / Math.sin(angle)));
        return distance;
    }
}
