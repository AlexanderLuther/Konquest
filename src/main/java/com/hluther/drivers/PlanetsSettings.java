package com.hluther.drivers;

import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author helmuth
 */
public class PlanetsSettings {
    
    private Planet planet;
    
    /*
    Metodo encargado de crear una nueva instancia de la clase Planet. Agrega la instancia a 
    la lista planets e incrementa en 1 el contador planetsCounter.
    */
    public int addPlanet(List<Planet> planets, int planetsCounter){
        planet = new Planet("P" + planetsCounter, 0, 0, 0.0, true, false);
        planet.setOwner("Neutral");
        planets.add(planet);
        return planetsCounter + 1;
    } 
    
    /*
    Metodo encargado de actualizar la informacion de un planeta.
    Obtiene los datos contenidos dentro de las celdas de la tabla correspondiente a la fila seleccionada y los 
    almacena en las variables name y spaceShips, owner, production y deathRate. Valida que name no tenga mas de 
    3 caracteres y no este vacio. Valida que deathRate no sea mayor a 0.99999999 y que la longitud total de todos 
    sus digitos no sea mayor a 8. Modifica el valor de los atributos del objeto planet seleccionado en la tabla.
    */
    public void updatePlanet(DefaultTableModel planetsTableModel, JTable planetsTable, List<Planet> planets){
        try{
            String name = planetsTableModel.getValueAt(planetsTable.getSelectedRow(), 0).toString();
            int spaceShips =  Integer.parseInt(planetsTableModel.getValueAt(planetsTable.getSelectedRow(), 1).toString());
            String owner =  planetsTableModel.getValueAt(planetsTable.getSelectedRow(), 2).toString();
            int production = Integer.parseInt(planetsTableModel.getValueAt(planetsTable.getSelectedRow(), 3).toString());
            double deathRate = Double.parseDouble(planetsTableModel.getValueAt(planetsTable.getSelectedRow(), 4).toString());
            if(name.length() > 3){
                name = name.substring(0, 3);
            }
            if(name.isBlank()){
                throw new IllegalArgumentException();
            }
            if(deathRate > 0.999999999999){
                throw new NumberFormatException();
            }
            if(String.valueOf(deathRate).length() > 8){
                deathRate = Double.parseDouble(String.valueOf(deathRate).substring(0, 8));
            }
            if(owner.equals("Neutral")){
                planets.get(planetsTable.getSelectedRow()).setNeutral(true);
            }
            else{
                planets.get(planetsTable.getSelectedRow()).setNeutral(false);
            }
            planets.get(planetsTable.getSelectedRow()).setName(name);
            planets.get(planetsTable.getSelectedRow()).setOwner(owner);
            planets.get(planetsTable.getSelectedRow()).setSpaceShips(spaceShips);
            planets.get(planetsTable.getSelectedRow()).setProduction(production);
            planets.get(planetsTable.getSelectedRow()).setDeathRate(deathRate);
        }
        catch(NumberFormatException e){}
        catch(IllegalArgumentException e){}
    }
    
     /*
    Metodo encargado de eliminar un planeta.
        1. Valida si se ha seleccionado un elemento de la tabla. De ser asi elimina de la lista planets el elemento ubicado
           en el indice igual a la fila seleccionada.
        2. De lo contrario valida que la tabla no se encuentre vacia. Si no esta vacia elimina el ultimo elemento de la 
           lista planets.
        3. Si la tabla esta vacia no realiza ninguna accion.
    */
    public void deletePlanet(JTable planetsTable, List<Planet> planets){
        if (planetsTable.getSelectedRow() == -1){
            if (!(planetsTable.getRowCount() <= 0)){
                planets.remove(planets.size()-1);        
            }
        }
        else{
            planets.remove(planetsTable.getSelectedRow());
        }
    }
    
    /*
    Metodo encargado de establecer la produccion inicial de los planetas neutrales.
    Itera por cada elemento  de la lista planets y se valida si el planeta es neutral, de ser asi si su produccion es -1 se modifica 
    determinando si el valor del parametro mapProduction es o no -1, si no es -1, se modifica dandole el valor contenido dentro del 
    parametro production. Si es valor si es -1 se establece el valor de production en 0.
    */
    public List<Planet> setInitialProduction(List<Planet> planets, int mapProduction){    
        for(int i = 0; i < planets.size(); i++){
            if(planets.get(i).isNeutral()){
                if(planets.get(i).getProduction() == -1){
                    if(mapProduction  != -1){                     
                        planets.get(i).setProduction(mapProduction);
                    }
                    else{
                        planets.get(i).setProduction(0);
                    }

                }   
            }
        }
        return planets;
    } 
    
    /*
    Metodo encargado de asignar a cada planeta su propietario. Recibe como parametros una instancia del objeto planeta y un listado
    de jugadores. Establece el valor del propietario en Neutral e itera por cada uno de los jugadores obteniendo todos los nombres 
    de los planetas de los que son propietarios. Itera por cada nombre de planeta y si el nombre es igual al nombre del planeta que
    se recibe como parametro entonces se establece el nombre propietario del planeta al nombre del jugador actual en la iteracion. 
    */
    public void setInitialOwner(Planet planet, List<Player> players){
        planet.setOwner("Neutral");
        //Itera por cada uno de los jugadores
        for(int j = 0; j < players.size(); j++){
            //Obtiene los nombres de los planetas que le pertenecen al jugador.
            String[] planetName = players.get(j).getPlanetsName().split("|");
            //Itera por cada nombre de planeta y lo compara con el nombre del planete actual
            for(int k = 0; k < planetName.length; k++){
                if(planetName[k].equals(planet.getName())){
                    planet.setOwner(players.get(j).getName());
                }
            }       
        }
    }
    
    /*
    Metodo encargado de restablecer el propietario de un planeta cuando este es eliminado. Recibe como parametros un listado de 
    planetas y el nombre del jugador eliminado. Itera por cada uno de los planetas y si el nombre del propietario del planeta 
    actual es igual al nombre del jugador eliminado se restablece el nombre del propietario a Neutral.
    */
    public void removeDeletedOwner(List<Planet> planets, String playerName){       
        for(int i = 0; i < planets.size(); i++){
            if(planets.get(i).getOwner().equals(playerName)){
                planets.get(i).setOwner("Neutral");
            }
        }
    }
    
}