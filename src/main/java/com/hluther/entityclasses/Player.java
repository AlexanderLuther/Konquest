package com.hluther.entityclasses;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author helmuth
 */
public class Player {

    private String name;
    private List<Planet> planets = new ArrayList<Planet>();
    private String planetsName;
    private String type;
    private Color color;
    
    public Player(String name, String planetsName, String type) {
        this.name = name;
        this.planetsName = planetsName;
        this.type = type;
    }

    public Player(String name, String type, Color color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanetsName() {
        return planetsName;
    }

    public void setPlanetsName(String planetsName) {
        this.planetsName = planetsName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
