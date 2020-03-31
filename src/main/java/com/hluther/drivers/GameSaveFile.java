package com.hluther.drivers;

import com.hluther.entityclasses.FleetInformation;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author helmuth
 */
public class GameSaveFile {
    
    private List<Planet> planets = new ArrayList<Planet>();
    private List<Player> players = new ArrayList<Player>();
    private List<FleetInformation> fleets = new ArrayList<FleetInformation>();
    private Planet planet;
    private Player player;
    private FleetInformation fleet;
    private Map map;
    
    public List<Planet> getPlanets() {
        return planets;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }

    public List<FleetInformation> getFleets() {
        return fleets;
    }

    
    //Metodo encargado de crear una nueva instancia de la clase Planet y agregarlo a la lista planets.
    public void createPlanet(String name, String owner, int spaceShips, int production, double deathRate, boolean neutral, int row, int column){
        planet = new Planet(name, owner, spaceShips, production, row, column, deathRate, neutral);
        planets.add(planet); 
    }
    
    //Metodo encargado de crear una nueva instancia de la clase Player y agregarlo a la lista players.
    public void createPlayer(String name, String type, String colorName){
        player = new Player(name, type, getColor(colorName));
        players.add(0,player);
    }
    
    //Metodo encargado de crear una nueva instancia de la clase Map.
    public void createMap(int rows, int columns, boolean blindMap, boolean acumulate, boolean showSpaceShips, boolean showStatistics, int completion){
        map = new Map(rows, columns, showSpaceShips, showStatistics, blindMap, acumulate, completion);
    }
    
    //Metodo encargado de crear una nueva instancia de la clase FleetInformation.
    public void createFleet(int initialTurn, int arrivalTurn, int initialRow, int initialColumn, int targetRow, int targetColumn, int spaceShipsAmount, String attackingPlayer){  
        for(Player currentPlayer : players){
            if(currentPlayer.getName().equals(attackingPlayer)){
                player = currentPlayer;
                break;
            }
        }
        fleet = new FleetInformation(initialTurn, arrivalTurn, initialRow, initialColumn, targetRow, targetColumn, spaceShipsAmount, player);
        fleets.add(0, fleet);
    }
    
    //Metodo encargado de agregar a la lista de planetas de cada jugador los planetas de los cuales son propietarios.
    public void assignPlanets(){
        for(Player currentPlayer: players){
            for(int i =0; i < planets.size(); i++){
                if(planets.get(i).getOwner().equals(currentPlayer.getName())){
                    currentPlayer.getPlanets().add(planets.get(i));
                    planets.remove(i);
                    i--;
                }
            }
        }
    }
    
    //Metodo encargado de retornar un color en base al String recibe como parametro.
    private Color getColor(String colorName){
        return switch (colorName) {
            case "azul" -> Color.blue;
            case "blanco" -> Color.white;
            case "amarillo" -> Color.yellow;
            case "verde" -> Color.green;
            case "rojo" -> Color.red;
            case "naranja" -> Color.orange;
            case "grisClaro" -> Color.lightGray;
            case "cyan" -> Color.cyan;
            case "grisOscuro" -> Color.darkGray;
            default -> Color.magenta;
        };
    }
    
    //Metodo encargado de limpiar las listas de la clase.
    public void clearLists(){
        this.planets.clear();
        this.players.clear();
        this.fleets.clear();
    }     
}
