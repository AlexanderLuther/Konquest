package com.hluther.entityclasses;

import com.hluther.gui.Square;

/**
 *
 * @author helmuth
 */
public class FleetDTO {
    
    int fleetNumber;
    String initialPlanetName;
    String targetPlanetName;
    int spaceShipsAmount;
    double initialPlanetDeathRate;
    long arrivalTurn;
    private Square initialSquare;
    private Square targetSquare;
    private Player attackingPlayer;

    public FleetDTO(int fleetNumber, Square initialSquare, Square targetSquare, int spaceShipsAmount, long arrivalTurn, Player attackingPlayer) {
        this.fleetNumber = fleetNumber;
        this.initialSquare = initialSquare;
        this.targetSquare = targetSquare;
        this.spaceShipsAmount = spaceShipsAmount;
        this.arrivalTurn = arrivalTurn;
        this.attackingPlayer = attackingPlayer;
        this.initialPlanetName = initialSquare.getPlanet().getName();
        this.targetPlanetName = this.targetSquare.getPlanet().getName();
        this.initialPlanetDeathRate = initialSquare.getPlanet().getDeathRate();
    }

    public int getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(int fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getInitialPlanetName() {
        return initialPlanetName;
    }

    public void setInitialPlanetName(String initialPlanet) {
        this.initialPlanetName = initialPlanet;
    }

    public String getTargetPlanetName() {
        return targetPlanetName;
    }

    public void setTargetPlanetName(String targetPlanet) {
        this.targetPlanetName = targetPlanet;
    }

    public int getSpaceShipsAmount() {
        return spaceShipsAmount;
    }

    public void setSpaceShipsAmount(int spaceShips) {
        this.spaceShipsAmount = spaceShips;
    }

    public double getInitialPlanetDeathRate() {
        return initialPlanetDeathRate;
    }

    public void setInitialPlanetDeathRate(double initialPlanetDeathRate) {
        this.initialPlanetDeathRate = initialPlanetDeathRate;
    }

    public long getArrivalTurn() {
        return arrivalTurn;
    }

    public void setArrivalTurn(long arrivalTurn) {
        this.arrivalTurn = arrivalTurn;
    }

    public Square getInitialSquare() {
        return initialSquare;
    }

    public void setInitialSquare(Square initialSquare) {
        this.initialSquare = initialSquare;
    }

    public Square getTargetSquare() {
        return targetSquare;
    }

    public void setTargetSquare(Square targetSquare) {
        this.targetSquare = targetSquare;
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public void setAttackingPlayer(Player attackingPlayer) {
        this.attackingPlayer = attackingPlayer;
    }
    
}
