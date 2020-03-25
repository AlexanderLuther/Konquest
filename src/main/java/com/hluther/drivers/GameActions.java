package com.hluther.drivers;

import com.hluther.gui.Square;
/**
 *
 * @author helmuth
 */
public class GameActions {
    
    private boolean measureDistance = false;
    private boolean sendSpaceShips = false;

    public boolean isMeasureDistance() {
        return measureDistance;
    }

    public void setMeasureDistance(boolean measureDistance) {
        this.measureDistance = measureDistance;
    }

    public boolean isSendSpaceShips() {
        return sendSpaceShips;
    }

    public void setSendSpaceShips(boolean sendSpaceShips) {
        this.sendSpaceShips = sendSpaceShips;
    }
    
    
    public double calculateDistance(Square initialSquare, Square targetSquare){
        return Math.sqrt((Math.pow((targetSquare.getCoordinateX() - initialSquare.getCoordinateX()), 2)) + Math.pow((targetSquare.getCoordinateY() - initialSquare.getCoordinateY()), 2));
    }
    
}
