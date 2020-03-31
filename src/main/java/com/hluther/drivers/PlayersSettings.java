package com.hluther.drivers;

import com.hluther.entityclasses.Player;
import com.hluther.entityclasses.Planet;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author helmuth
 */
public class PlayersSettings {
    
    private Player player;
    
    /*
    Metodo encargado de crear una nueva instancia de la clase Player. Agrega la instancia a 
    la lista players e incrementa en 1 el contador playersCounter.
    */
    public int addPlayer(List<Player> players, String type, int playersCounter){
        if(players.size() < 10){
            player = new Player("Player" + playersCounter, "", type.toUpperCase());
            players.add(player);
            return playersCounter + 1;
        }
        return playersCounter;
    }    
    
    /*
    Metodo encargado de actualizar la informacion de un jugador.
    Obtiene los datos contenidos dentro de las celdas de la tabla correspondiente a la fila seleccionada y los 
    almacena en las variables name y type. Valida que name no tenga mas de 10 caracteres y no este vacio y 
    modifica el valor de los atributos del objeto player seleccionado en la tabla.
    */
    public void updatePlayer(DefaultTableModel playersTableModel, JTable playersTable, List<Player> players){
        String name = playersTableModel.getValueAt(playersTable.getSelectedRow(), 1).toString();
        String type = playersTableModel.getValueAt(playersTable.getSelectedRow(), 2).toString();
        if(name.length() > 10){
            name = name.substring(0, 10);
        }        
        if(name.equals("")){
            name = "No Name";
        }
        players.get(playersTable.getSelectedRow()).setName(name);
        players.get(playersTable.getSelectedRow()).setType(type);
    }
    
    /*
    Metodo encargado de eliminar un jugador.
        1. Valida si se ha seleccionado un elemento de la tabla. De ser asi elimina de la lista players el elemento ubicado
           en el indice igual a la fila seleccionada.
        2. De lo contrario valida que la tabla no se encuentre vacia. Si no esta vacia elimina el ultimo elemento de la 
           lista players.
        3. Si la tabla esta vacia no realiza ninguna accion.
    */
    public void deletePlayer(JTable playersTable, List<Player> players){
        if (playersTable.getSelectedRow() == -1){
            if (!(playersTable.getRowCount() <= 0)){
                players.remove(players.size()-1);
            }
            else{
            }
        }
        else{
            players.remove(playersTable.getSelectedRow());
        }
    }  
     
    public void assignPlanets(List<Planet> planets, List<Player> players){       
        for(int i = 0; i < planets.size(); i++){
            for(Player currentPlayer : players){
                if(planets.get(i).getOwner().equals(currentPlayer.getName())){
                    currentPlayer.getPlanets().add(planets.get(i));
                    planets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
    
    /*
    Metodo encargado de verificar que cada jugador tenga por lo menos un planeta asignado.
    Recibe como paremtros dos listas, una de jugadores y una de planetas. Crea dos listas nuevas, auxPlayers y auxPlanets y las llena 
    con los datos de las listas players y planets respectivamente. Al llenar la lista auxPlayers tambien remueve todos los planetas 
    contenidos dentro del atributo planets de el jugador en turno en la iteracion. Llama al metodo assignPlanets y posteriormente 
    itera por cada uno de los jugadores dentro de la lista auxPlayers, si alguno de los jugadores tiene vacio el atributo planets
    se establece en true el valor de la variable booleana empty, por ultimo se devuelve la variable empty cuyo valor al inicio es false.
    */
    public boolean verifyPlanets(List<Planet> planets, List<Player> players){
        List<Planet> auxPlanets = new ArrayList<>();
        List<Player> auxPlayers = new ArrayList<>();
        boolean empty = false;
        for(int i = 0; i < planets.size(); i++){
            auxPlanets.add(planets.get(i));
        }
        for(int i = 0; i < players.size(); i++){
            auxPlayers.add(players.get(i));
            auxPlayers.get(i).getPlanets().clear();
        }
        assignPlanets(auxPlanets, auxPlayers);
        for(int i = 0; i < auxPlayers.size(); i++){
            if(auxPlayers.get(i).getPlanets().isEmpty()){
                empty = true;
            }
        }
        return empty;
    }
    
    /*
    Metodo encargado de asinar un color a cada uno de los jugadores.
    */
    public void assignColors(List<Player> players){
        Color color = Color.white;
        for(int i = 0; i < players.size(); i++){
            switch(i){
                case 0:
                    color = Color.blue;
                 break;
                case 1:
                    color = Color.yellow;
                break;
                case 2:
                    color = Color.green;
                break;
                case 3:
                    color = Color.red;
                break;
                case 4:
                    color = Color.orange;
                break;
                case 5:
                    color = Color.lightGray;
                break;
                case 6:
                    color = Color.cyan;
                break;
                case 7:
                    color = Color.darkGray;
                break;
                case 8:
                    color = Color.white;
                break;
                case 9:
                    color = Color.magenta;
                break;      
            }
        players.get(i).setColor(color);
        }
    }
    
    /*
    Metodo encargado de valida que el nombre de cada jugador sea unico. Si encuentra un nombre repetido
    devuelve true, de lo contrario devuelve false.
    */
    public boolean validateNames(List<Player> players){
        for(Player currentPlayer : players){
            for(Player comparationPlayer : players){
                if(currentPlayer != comparationPlayer){
                    if(currentPlayer.getName().equals(comparationPlayer.getName())){
                        return true;
                    }
                }
            }
        }     
        return false;
    }
}
