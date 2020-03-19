package com.hluther.drivers;

import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.MapData;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author helmuth
 */
public class MapConfigFileDriver {
    
    private List<Planet> planets = new ArrayList<Planet>();
    private List<Player> players = new ArrayList<Player>();
    private List<MapData> mapData = new ArrayList<MapData>();
    private List<String> messages = new ArrayList<String>();
    private Planet planet;
    private Player player;
    private MapData data;
    private Map map;
    private String msg;
    private boolean dataExist = false;

    /*
    Metodo encargado de crear una nueva instancia de la clase Planet. Recibe como parametros todos los atributos de la nueva
    instancia. Por ultimo agrega a la lista planets el objeto creado.
    */
    public void createPlanet(String name, int spaceShips, int production, double deathRate, boolean neutral, boolean generalProduction){
        planet = new Planet(name, spaceShips, production, deathRate, neutral, generalProduction);
        planets.add(planet);
    }
    
    /*
    Metodo encargado de crear una nueva instancia de la clase Player. Recibe como parametros todos los atributos de la nueva
    instancia. Por ultimo agrega a la lista players el objeto creado.
    */
    public void createPlayer(String name, String planets, String type){
        player = new Player(name, planets, type);
        players.add(player);
    }
    
    /*
    Metodo encargado de crear una nueva instancia de la clase MapData y agregarla a la lista mapData. Recibe como parametros
    todos los atributos de la nueva instancia. Valida que dentro de la lista mapData no exista algun objeto que contenga el
    aributo name igual al parametro name que se recibe, si ambos son iguales se crea un mensaje de error y se agrega a la lista
    messages. De lo contario se crea la nueva instancia.
    */
    public void createMapData(String name, String singleData){
        dataExist = false;
        for(int i = 0; i< mapData.size(); i++){
            if(mapData.get(i).getName().equals(name)){
                dataExist = true;            
                msg = "Se detecto mas de una vez el elemento \""+mapData.get(i).getName()+"\". Solamente se considera la primera ocurrencia, se descarto \""+name+":"+singleData+"\".";
                messages.add(msg);
                break;
            }
        }       
        if(!dataExist){
            data = new MapData(name, singleData);
            mapData.add(data);
        }
    }
    
    /*
    Metodo encargado de crear una nueva instancia de la clase Map. Inicializa los atributos de la nueva instancia y recorre la lista
    mapData para asignar los valores contenidos dentro del atributo data de cada objeto dentro de la lista a su correspondiente atributo
    determinado por el atributo name de los objetos de la lista.
    */
    public void createMap(){
        String name = "";
        int rows = -1;
        int columns = -1;
        boolean showSpaceShips = false;
        boolean showStatistics = false;
        int production = -1;
        boolean random = false;
        int neutralPlanets = -1;
        boolean blindMap= false;
        boolean accumulate = false;
        int completion = -1;
        for(int i = 0; i < mapData.size(); i++){
            switch(mapData.get(i).getName()){
                case "id":
                    name = mapData.get(i).getData();
                break;
                case "rows":
                    rows = Integer.parseInt(mapData.get(i).getData());
                break;    
                case "columns":
                    columns = Integer.parseInt(mapData.get(i).getData());
                break;    
                case "showSpaceShips":
                    if(mapData.get(i).getData().equals("true")) showSpaceShips = true;                    
                break;    
                case "showStatistics":
                    if(mapData.get(i).getData().equals("true")) showStatistics = true;
                break;    
                case "production":
                    production = Integer.parseInt(mapData.get(i).getData());
                break;    
                case "random":
                    if(mapData.get(i).getData().equals("true")) random = true;
                break;    
                case "neutralPlanets":
                    neutralPlanets = Integer.parseInt(mapData.get(i).getData());
                break;    
                case "blindMap":
                    if(mapData.get(i).getData().equals("true")) blindMap = true;
                break;    
                case "accumulate":
                    if(mapData.get(i).getData().equals("true")) accumulate = true;
                break;    
                case "completion":
                    completion = Integer.parseInt(mapData.get(i).getData());
                break;    
            }
        }
        this.setErrors(name, rows, columns, production, neutralPlanets, random);    
        map = new Map(name, rows, columns, showSpaceShips, showStatistics, production, random, neutralPlanets, blindMap, accumulate, completion);
    }
    
    /*
    Metodo encargado de verificar que los elementos obligatorios del archivo de entrada existan, de lo contrario crea un mensaje de error
    y lo almacena en la lista messages.
    */
    private void setErrors(String name, int rows, int columns, int production, int neutralPlanets, boolean random){
        if(name.equals("")){
            msg = "No se especifico un nombre para el mapa.";
            messages.add(msg);
        }
        if(rows == -1){
            msg = "No se especifico la cantidad de filas del mapa.";
            messages.add(msg);
        }
        if(columns == -1){
            msg = "No se especifico la cantidad de columnas del mapa.";
            messages.add(msg);
        }
        if(production == -1){
            msg = "No se especifico la cantidad de produccion para los planetas neutrales.";
            messages.add(msg);
        }
        if(random == true && neutralPlanets == -1){
            msg = "No se especifico la cantidad de planetas neutrales a crear.";
            messages.add(msg);
        }
    }
    
    public List<Planet> getPlanets() {
        return planets;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public List<String> getMessages() {
        return messages;
    }

    public Map getMap() {
        return map;
    }
      
    /*
    Metodo encargado de limpiar las listas de la clase.
    */
    public void clearLists(){
        this.planets.clear();
        this.players.clear();
        this.mapData.clear();
        this.messages.clear();
    }   
}