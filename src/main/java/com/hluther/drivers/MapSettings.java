package com.hluther.drivers;

import com.hluther.entityclasses.Map;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
/**
 *
 * @author helmuth
 */
public class MapSettings {
    
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
    
    /*
    Metodo encargado de validar que la cantidad de planetas no sea mayor a la cantidad de 
    espacios disponibles(filas * columnas). Establece si se crearan planetas de forma 
    aleatoria, de ser asi suma a la cantidad de planetas existentes la cantidad que se 
    debera crear.
    */
    public boolean validateIntegrity(Map map, int planetsAmount){
        if(map.isRandom()){
            return ((map.getRows() * map.getColumns()) >= (planetsAmount + map.getNeutralPlanets()));
        }
        else{
            return ((map.getRows() * map.getColumns()) >= planetsAmount);
        }
    }
}
