package com.hluther.drivers;

import com.hluther.entityclasses.FleetDTO;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Color;
/**
 *
 * @author helmuth
 */
public class TextWritter {
    
    String data = "";
    
    public void openGlobalStructure(){
       data = data + "{\n";
    }
    
    public void closeGlobalStructure(){
        data = data + "}";
    }
    
    //Metodo encargado de crear el texto correspondiente a la estructura mapa.
    public void writeMapStructure(Map map){
        data = data + "\tMAPA:{\n" 
                    + "\t\ttama\u00f1o:{\n" 
                    + "\t\t\tfilas: "+map.getRows()+",\n" 
                    + "\t\t\tcolumnas: "+map.getColumns()+"\n" 
                    + "\t\t},\n" 
                    + "\t\tmapaCiego: "+map.isBlindMap()+",\n" 
                    + "\t\tacumular: "+map.isAccumulate()+",\n" 
                    + "\t\tNEUTRALES:{\n" 
                    + "\t\t\tmostrarNaves: "+map.isShowSpaceShips()+",\n" 
                    + "\t\t\tmostrarEstadisticas: "+map.isShowStatistics()+"\n" 
                    + "\t\t}";
        if(map.getCompletion() > 1){
            data = data + ",\n" 
                        + "\t\tfinalizacion: "+map.getCompletion()+"\n" 
                        + "\t},\n";
        }
        else{
            data = data + "\t\n},\n";
        }
    }
    
    public void openPlanetsStructure(){
        data = data + "\tPLANETAS:[\n"; 
    }
    
    public void openNeutralPlanetsStructure(){
        data = data + "\tPLANETAS_NEUTRALES:[\n"; 
    }
        
    //Metodo encargado de crear el texto correspondiente a la estructrura planeta.
    public void writePlanetsStructure(Planet planet, boolean lastPlanet){    
        data = data  + "\t\t{\n"   
                     + "\t\t\tnombre: \""+planet.getName()+"\",\n"
                     + "\t\t\tpropietario: \""+planet.getOwner()+"\",\n"
                     + "\t\t\tnaves: "+planet.getSpaceShips()+",\n" 
                     + "\t\t\tproduccion: "+planet.getProduction()+",\n" 
                     + "\t\t\tporcentajeMuertes: "+planet.getDeathRate()+",\n" 
                     + "\t\t\tneutral: "+planet.isNeutral()+",\n"
                     + "\t\t\tfila: "+planet.getRow()+",\n"
                     + "\t\t\tcolumna: "+planet.getColumn()+"\n";    
        if(lastPlanet){
            data = data + "\t\t}\n"  
                         + "\t],\n"; 
        }
        else{
            data = data  + "\t\t},\n";
                        
        }
    }
    
    public void openPlayerStructure(){
        data = data + "\tJUGADORES:[\n"; 
    }
    
    //Metodo encargado de crear el texto correspondiente a la estructrura jugador.
    public void writePlayerStructure(Player player, boolean lastPlayer){
        data = data  + "\t\t{\n"   
                     + "\t\t\tnombre: \""+player.getName()+"\",\n"
                     + "\t\t\ttipo: "+player.getType()+",\n"
                     + "\t\t\tcolor: "+getColorName(player.getColor())+"\n";
        if(lastPlayer){
            data = data + "\t\t}\n"
                        + "\t]\n"; 
        }
        else{
            data = data  + "\t\t},\n";               
        }
    }
    
    
    public void openSendingStructure(){
        data = data.substring(0, data.length() - 1);  
        data = data + ",\n"
                    + "\tENVIOS:[\n"; 
    }
    
    public void closeSendingStructure(){
        data = data.substring(0, data.length() - 2);  
        data = data + "\n\t]\n"; 
    }
    
    public void writeSendingStructure(FleetDTO fleet){
        data = data  + "\t\t{\n"   
                     + "\t\t\tturnoEnvio: "+fleet.getInitialTurn()+",\n"
                     + "\t\t\tturnoLlegada: "+fleet.getArrivalTurn()+",\n"
                     + "\t\t\tfilaInicial: "+fleet.getInitialSquare().getRow()+",\n" 
                     + "\t\t\tcolumnaInicial: "+fleet.getInitialSquare().getColumn()+",\n" 
                     + "\t\t\tfilaObjetivo: "+fleet.getTargetSquare().getRow()+",\n" 
                     + "\t\t\tcolumnaObjetivo: "+fleet.getTargetSquare().getColumn()+",\n"     
                     + "\t\t\tnaves: "+fleet.getSpaceShipsAmount()+",\n" 
                     + "\t\t\tnombre: \""+fleet.getAttackingPlayer().getName()+"\"\n"
                     + "\t\t},\n";
    }
    
    //Metodo encargado de retornar un String con el nombre del color que recibe como parametro.
    private String getColorName(Color playerColor){
        if(playerColor == Color.blue) return "azul";
        else if(playerColor == Color.yellow) return "amarillo";
        else if(playerColor == Color.green) return "verde";
        else if(playerColor == Color.red) return "rojo";
        else if(playerColor == Color.orange) return "naranja";
        else if(playerColor == Color.lightGray) return "grisClaro";
        else if(playerColor == Color.cyan) return "cyan";
        else if(playerColor == Color.darkGray) return "grisOscuro";
        else if(playerColor == Color.white) return "blanco";
        else if(playerColor == Color.magenta) return "magenta";
        else return "black";
    }
    
    public String getText(){
        return data;
    }
}