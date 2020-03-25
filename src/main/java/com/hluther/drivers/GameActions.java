package com.hluther.drivers;

import com.hluther.gui.Konquest;
import com.hluther.gui.Square;
/**
 *
 * @author helmuth
 */
public class GameActions {
    
    private boolean measureDistance = false;
    private boolean sendSpaceShips = false;
    private int selectedSquareCounter = 0;
    private Konquest konquest;

    public GameActions(Konquest konquest) {
        this.konquest = konquest;
    }
    
    //------------------------------------------ Getters y Setters de la clase. ------------------------------------------
    public boolean isMeasureDistance() {
        return measureDistance;
    }

    public void setMeasureDistance(boolean measureDistance){
        this.measureDistance = measureDistance;
    }

    public boolean isSendSpaceShips() {
        return sendSpaceShips;
    }

    public void setSendSpaceShips(boolean sendSpaceShips) {
        this.sendSpaceShips = sendSpaceShips;
    }

    public int getSelectedSquareCounter() {
        return selectedSquareCounter;
    }

    public void setSelectedSquareCounter(int selectedSquareCounter) {
        this.selectedSquareCounter = selectedSquareCounter;
    }
    
    //------------------------------------------ Metodos de la clase. ------------------------------------------
    
    public void executeAction(){
        if(measureDistance){
            switch(selectedSquareCounter){
                case 2:
                    konquest.printMeasureDistanceMessages(1);
                break;
                case 3:
                    konquest.printMeasuredDistance();
                    konquest.printTurnValues();
                    reestoreValues();
                break;
            }           
        }
        else if(sendSpaceShips){
        
        }
    }
    
    /*
    Metodo encargado de calcular la distancia entre dos planetas. Recibe como parametros dos casillas, las cuales 
    contienen una coordenada x y una cordenada y que son las que se utilizan para calcular la distancia utilizando 
    la formula de distancia entre puntos. Redondea a dos cifras decimales el resultado y lo devuelve.
    */
    public double calculateDistance(Square initialSquare, Square targetSquare){
        double distance = Math.round(Math.sqrt((Math.pow((targetSquare.getCoordinateX() - initialSquare.getCoordinateX()), 2)) + Math.pow((targetSquare.getCoordinateY() - initialSquare.getCoordinateY()), 2)) * 100d) / 100d;
        return distance;
    }
    
    /*
    Metodo encargado de calcular el turno de llegada. Recibe como parametros el turno actual y una distancia.
    Redondea al entero mas cercano la distancia y la suma al turno actual. Devuelve el resultado de esa suma.
    */
    public long getArrivalTurn(int actualTurn, double distance){
        return actualTurn + Math.round(distance);
    }
    
    /*
    Metodo encargado de verificar si se debe realizar el cambio de turno o finalizar la partida en 
    base a la cantidad de turnos jugados.
        1. Valida si el atributo completion de la instancia map es igual a -1.
            Si es true:
                Compara si el turno actual es igual al turno de finalizacion.
                    Si ambos turnos son iguales se retorna true.
                    Si no son iguales se devuelve false
            Si es false:
                Se retorna false.
    */
    public boolean verifyCompletion(int actualTurn, int completionTurn){
        if(completionTurn != -1){
            if(actualTurn == completionTurn){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    //Metodo encargado de finalizar un turno
    public void endTurn(){
        reestoreValues();
    }
    
    //Metodo encargado de cancelar una accion seleccioana.
    public void cancelAction(){
        reestoreValues();
        konquest.printTurnValues();
    }
    
    //Metodo encargado de restaurar a sus valores iniciales la variables de la clase.
    private void reestoreValues(){
        selectedSquareCounter = 0;
        measureDistance = false;
        sendSpaceShips =false;
    } 
}
