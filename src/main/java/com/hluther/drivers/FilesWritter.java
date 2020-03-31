package com.hluther.drivers;

import com.hluther.entityclasses.FleetDTO;
import java.util.List;
import com.hluther.entityclasses.Player;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Map;

/**
 *
 * @author helmuth
 */
public class FilesWritter {
    
    private TextWritter textWritter;

    public FilesWritter(TextWritter textWritter) {
        this.textWritter = textWritter;
    }
    
    /*
    Metodo encargado de mandar a escribir el estado inicial del juego.
    Escribe los atributos iniciales del mapa, de cada jugador y de cada planeta.
    */
    public void writeInitialState(List<Player> players, List<Planet> neutralPlanets, Map map){
        textWritter.openGlobalStructure();
        textWritter.writeMapStructure(map);
        textWritter.openPlanetsStructure();
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < players.get(i).getPlanets().size(); j++){
                if(j == players.get(i).getPlanets().size() - 1 && i == players.size() - 1){
                    textWritter.writePlanetsStructure(players.get(i).getPlanets().get(j), true);
                }
                else{
                    textWritter.writePlanetsStructure(players.get(i).getPlanets().get(j), false);
                }
            }
        }
        if(!neutralPlanets.isEmpty()){
            textWritter.openNeutralPlanetsStructure();
        }
        for(int i = 0; i < neutralPlanets.size(); i++){
            if(i == neutralPlanets.size() - 1){
                textWritter.writePlanetsStructure(neutralPlanets.get(i), true);              
            }
            else{
                textWritter.writePlanetsStructure(neutralPlanets.get(i), false);
            }
        }
        textWritter.openPlayerStructure();
        for(int i = 0; i < players.size(); i++){
            if(i == players.size() - 1){
                textWritter.writePlayerStructure(players.get(i), true);
            }
            else{
                textWritter.writePlayerStructure(players.get(i), false);
            }
        }
    }
    
    public void writeSending(FleetDTO fleet, boolean openStructure){
        if(openStructure){
            textWritter.openSendingStructure();
        }
        textWritter.writeSendingStructure(fleet);
    }
    
    public void closeAll(){
        textWritter.closeSendingStructure();
        textWritter.closeGlobalStructure();
    }
    
    public void closeGlobalEstructure(){
        textWritter.closeGlobalStructure();
    }
    
    public String getData(){
        return textWritter.getText();
    }
}
