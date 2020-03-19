package com.hluther.drivers;

import com.hluther.entityclasses.Player;
import com.hluther.gui.Konquest;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author helmuth
 */
public class GameSettingsDriver {
    
    private Player player;
    
    /*
    Metodo encargado de crear una nueva instancia de la clase Player. Agrega la instancia a 
    la lista players e incrementa en 1 el contador playersCounter.
    */
    public int addPlayer(List<Player> players, String type, int playersCounter){
        player = new Player("Player" + playersCounter, "", type.toUpperCase());
        players.add(player);
        return playersCounter++;
    }    
    
    /*
    Metodo encargado de actualizar la informacion de un jugador.
    Obtiene los datos contenidos dentro de las celdas de la tabla correspondiente a la fila seleccionada y los 
    almacena en las variables name y type. Valida que name no tenga mas de 10 caracteres y no este vacio y 
    modifica el valor de los atributos del objeto player seleccionado en la tabla.
    */
    public void updatePlayer(DefaultTableModel playersTableModel, JTable playersTable, List<Player> players){
        String name = playersTableModel.getValueAt(playersTable.getSelectedRow(), 0).toString();
        String type = playersTableModel.getValueAt(playersTable.getSelectedRow(), 1).toString();
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
        }
        else{
            players.remove(playersTable.getSelectedRow());
        }
    }
    
    /*
    Metodo encargado de actualizar los valores de la instancia de la clase Map que recibe como parametro, en base a los 
    diferentes parametros que recibe. Devuelve la misma instancia con sus valores actualizados.
    */
    public Map updateMap(Map map, JTextField id ,JSpinner rows, JSpinner columns, JSpinner completion, JSpinner neutralPlanets, JSpinner production, JCheckBox completionC, JCheckBox accumulate, JCheckBox blindMap, JCheckBox random, JCheckBox ships, JCheckBox statistics){
        map.setName(id.getText().toString());
        map.setRows(Integer.parseInt(rows.getValue().toString()));
        map.setColumns(Integer.parseInt(columns.getValue().toString()));
        if(completionC.isSelected()){
            map.setCompletion(Integer.parseInt(completion.getValue().toString()));
        }
        else{
            map.setCompletion(-1);
        }
        map.setAccumulate(accumulate.isSelected());
        map.setBlindMap(blindMap.isSelected());
        map.setRandom(random.isSelected());
        if(random.isSelected()){
            map.setNeutralPlanets(Integer.parseInt(neutralPlanets.getValue().toString()));
        }
        else{
            map.setNeutralPlanets(-1);
        }
        map.setShowSpaceShips(ships.isSelected());
        map.setShowStatistics(statistics.isSelected());
        map.setProduction(Integer.parseInt(production.getValue().toString()));
        return map;
    }
        
}
