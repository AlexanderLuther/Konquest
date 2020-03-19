package com.hluther.entityclasses;
/**
 *
 * @author helmuth
 */
public class Map {
    
    //Atributos obligatorios
    private String name;
    private int rows;
    private int columns;
    private boolean showSpaceShips;
    private boolean showStatistics;
    private int production;
    //Atributos opcionales
    private boolean random;
    private int neutralPlanets;
    private boolean blindMap;
    private boolean accumulate;
    private int completion;

    public Map() {
    }

    public Map(String name, int rows, int columns, boolean showSpaceShips, boolean showStatistics, int production, boolean random, int neutralPlanets, boolean blindMap, boolean accumulate, int completion) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.showSpaceShips = showSpaceShips;
        this.showStatistics = showStatistics;
        this.production = production;
        this.random = random;
        this.neutralPlanets = neutralPlanets;
        this.blindMap = blindMap;
        this.accumulate = accumulate;
        this.completion = completion;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean isShowSpaceShips() {
        return showSpaceShips;
    }

    public void setShowSpaceShips(boolean showSpaceShips) {
        this.showSpaceShips = showSpaceShips;
    }

    public boolean isShowStatistics() {
        return showStatistics;
    }

    public void setShowStatistics(boolean showStatistics) {
        this.showStatistics = showStatistics;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public int getNeutralPlanets() {
        return neutralPlanets;
    }

    public void setNeutralPlanets(int neutralPlanets) {
        this.neutralPlanets = neutralPlanets;
    }

    public boolean isBlindMap() {
        return blindMap;
    }

    public void setBlindMap(boolean blindMap) {
        this.blindMap = blindMap;
    }

    public boolean isAccumulate() {
        return accumulate;
    }

    public void setAccumulate(boolean accumulate) {
        this.accumulate = accumulate;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }
    
}
