package com.hluther.entityclasses;

/**
 *
 * @author helmuth
 */
public class FleetInformation {
    
    private int initialTurn;
    private int arrivalTurn;
    private int initialRow;
    private int initialColumn;
    private int targetRow;
    private int targetColumn;
    private int spaceShipsAmount;
    private Player attackingPlayer;

    public FleetInformation(int initialTurn, int arrivalTurn, int initialRow, int initialColumn, int targetRow, int targetColumn, int spaceShipsAmount, Player attackingPlayer) {
        this.initialTurn = initialTurn;
        this.arrivalTurn = arrivalTurn;
        this.initialRow = initialRow;
        this.initialColumn = initialColumn;
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;
        this.spaceShipsAmount = spaceShipsAmount;
        this.attackingPlayer = attackingPlayer;
    }

    public int getInitialTurn() {
        return initialTurn;
    }

    public void setInitialTurn(int initialTurn) {
        this.initialTurn = initialTurn;
    }

    public int getArrivalTurn() {
        return arrivalTurn;
    }

    public void setArrivalTurn(int arrivalTurn) {
        this.arrivalTurn = arrivalTurn;
    }

    public int getInitialRow() {
        return initialRow;
    }

    public void setInitialRow(int initialRow) {
        this.initialRow = initialRow;
    }

    public int getInitialColumn() {
        return initialColumn;
    }

    public void setInitialColumn(int initialColumn) {
        this.initialColumn = initialColumn;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(int targetColumn) {
        this.targetColumn = targetColumn;
    }

    public int getSpaceShipsAmount() {
        return spaceShipsAmount;
    }

    public void setSpaceShipsAmount(int spaceShipsAmount) {
        this.spaceShipsAmount = spaceShipsAmount;
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public void setAttackingPlayer(Player attackingPlayer) {
        this.attackingPlayer = attackingPlayer;
    }

    
    
}
