package com.hluther.entityclasses;

/**
 *
 * @author helmuth
 */
public class Planet {

    private String name;
    private String owner;
    private int spaceShips;
    private int production;
    private int row;
    private int column;
    private double deathRate;
    private boolean neutral;
    private boolean generalProduction;

    public Planet(String name, int spaceShips, int production, double deathRate, boolean neutral, boolean generalProduction) {
        this.name = name;
        this.spaceShips = spaceShips;
        this.production = production;
        this.deathRate = deathRate;
        this.neutral = neutral;
        this.generalProduction = generalProduction;
    }

    public Planet(String name, String owner, int spaceShips, int production, int row, int column, double deathRate, boolean neutral) {
        this.name = name;
        this.owner = owner;
        this.spaceShips = spaceShips;
        this.production = production;
        this.row = row;
        this.column = column;
        this.deathRate = deathRate;
        this.neutral = neutral;
    }
    
    public boolean isNeutral() {
        return neutral;
    }

    public void setNeutral(boolean neutral) {
        this.neutral = neutral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpaceShips() {
        return spaceShips;
    }

    public void setSpaceShips(int spaceShips) {
        this.spaceShips = spaceShips;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(double deathRate) {
        this.deathRate = deathRate;
    }

    public boolean isGeneralProduction() {
        return generalProduction;
    }

    public void setGeneralProduction(boolean generalProduction) {
        this.generalProduction = generalProduction;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }  

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
