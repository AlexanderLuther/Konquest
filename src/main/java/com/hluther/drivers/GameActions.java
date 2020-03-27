package com.hluther.drivers;

import com.hluther.entityclasses.FleetDTO;
import com.hluther.gui.Konquest;
import com.hluther.gui.Square;
import com.hluther.entityclasses.Player;
import java.util.ArrayList;
import java.util.List;
import com.hluther.entityclasses.Planet;
/**
 *
 * @author helmuth
 */
public class GameActions {
    
    private boolean measureDistance = false;
    private boolean sendSpaceShips = false;
    private int selectedSquareCounter = 0;
    private Konquest konquest;
    private int fleetsSent = 0;
    private FleetDTO fleet;
    private List<FleetDTO> fleets = new ArrayList<>();
    
    
    private Square targetSquare;
    private Planet initialPlanet;
    private Planet targetPlanet;
    private int spaceShipsAmount;
    private double initialPlanetPowerAttack;
    private double targetPlanetPowerAttack;
    private Player attackingPlayer;
    
       
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
    
    /*
    Metodo encargado de determinar la accion que se esta realizando.
    Establece si es una medicion de distancia o un envio de naves y posteriormente
    llama a los metodos necesarios para realizar el proceso.
    */
    public void executeAction(Square selectedSquare, Player currentPlayer){
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
            switch(selectedSquareCounter){
                case 2:
                    if(selectedSquare.getPlanet().getOwner().equals(currentPlayer.getName())){
                        konquest.printMeasureDistanceMessages(1);
                    }
                    else{
                        selectedSquareCounter = 1;
                    }
                break;
                case 3:
                    selectedSquareCounter = 4;
                    konquest.printSendSpaceShipsMessages(2);
                    konquest.activateSpaceShipsAmountArea();
                break;
            } 
        }
    }
    
    /*
    Metodo encargado de incrementar la cantidad de naves de un planeta en base a su produccion.
    Valida si la produccion es o no acumulativa, de ser acumulativa agrega una unidad a la produccion 
    de cada planeta al pasar un turno. Suma a la cantidad actual de naves que tiene un planeta la 
    cantidad de naves que dicta su produccion.
    */
    public void increaseSpaceShips(List<Player> players, List<Planet> neutralPlanets){
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < players.get(i).getPlanets().size(); j++){
                if(konquest.getMap().isAccumulate()){
                    players.get(i).getPlanets().get(j).setProduction(players.get(i).getPlanets().get(j).getProduction() + 1);
                }
                players.get(i).getPlanets().get(j).setSpaceShips(players.get(i).getPlanets().get(j).getSpaceShips() + players.get(i).getPlanets().get(j).getProduction());
            }
        }
        for(int i = 0; i < neutralPlanets.size(); i++){
            if(konquest.getMap().isAccumulate()){
                neutralPlanets.get(i).setProduction(neutralPlanets.get(i).getProduction() + 1);
            }
            neutralPlanets.get(i).setSpaceShips(neutralPlanets.get(i).getSpaceShips() + neutralPlanets.get(i).getProduction());
        }
    }
        
    /*
    Metodo encargado de realizar los movimientos respectivos en el turno de llegada de una flota a un planeta.
        1. Obtiene las flotas e itera por cada una de las flotas.
        2. Solo se realizaran acciones sobre las flotas cuyo turno de llegada sea igual al turno actual.
        3. Se lea signa un valor proveniente de los atributos de la flota en turno a las instancias y variables de la clase.
        4. Se establece si el envio de naves es un ataque o un envio de refuerzos.
            Envio de refuerzos:
                Se le aumenta a la cantidad de naves del planeta objetivo la cantidad de naves enviadas.
                Se lanza un mensaje informativo.
            Ataque:
                5. Se calcula el poder de ataque de el planeta inicial y el planeta objetivo.
                6. Se establece si el planeta objetivo fue conquistado o se defendio exitosamente.
                    Planeta conquistado:
                        Se modifica el propietario del planeta.
                        Se establece la cantidad de naves resultantes.
                        Se establece el color del planeta.
                        Se agrega al listado de planetas del jugador el nuevo planeta.
                    Planeta defendido:
                        Se establece la cantidad de naves restantes en el planeta objetivo.
                        Se lanza un mensaje informativo.
                7. Se establece si el planeta atacado es un planeta neutral o un planeta con algun propietario.
                    Neutral:
                        Se establece en false el valor booleano neutral de planeta.
                        Se remueve el planeta de la lista de planetas neutrales.
                    Con Propietario:
                        Se remueve el planeta de la lista de planetas del jugador.
        8. Se recarga la interfaz grafica de la casilla objetivo.
           Se remueve la flota actual de la lista fleets.
           Se reduce en uno el contador de iteraciones i.
    */
    
    public void validateArrivalSpaceShips(int currentTurn, List<Player> players, List<Planet> neutralPlanets){
        //----------------------------------------  1   ----------------------------------------//  
        fleets = konquest.getFleets();
        for(int i = 0; i < fleets.size(); i++){
            //----------------------------------------  2   ----------------------------------------//  
            if(fleets.get(i).getArrivalTurn() == currentTurn){
            //----------------------------------------  3   ----------------------------------------//  
                initialPlanet = fleets.get(i).getInitialSquare().getPlanet();
                targetPlanet = fleets.get(i).getTargetSquare().getPlanet();
                spaceShipsAmount = fleets.get(i).getSpaceShipsAmount();
                targetSquare = fleets.get(i).getTargetSquare();
                attackingPlayer = fleets.get(i).getAttackingPlayer();
                
                //----------------------------------------  4 Envio de refuerzos   ----------------------------------------//   
                if(attackingPlayer.getName().equals(targetPlanet.getOwner())){
                    targetPlanet.setSpaceShips(targetPlanet.getSpaceShips() + spaceShipsAmount);
                    konquest.printArrivalSpaceShipsMessages(0, attackingPlayer.getName(), targetPlanet.getName(), spaceShipsAmount, attackingPlayer.getColor());
                }
                //----------------------------------------  4 Ataque  ----------------------------------------//   
                else{
                    //----------------------------------------  5   ----------------------------------------//
                    initialPlanetPowerAttack = initialPlanet.getDeathRate() * spaceShipsAmount;
                    targetPlanetPowerAttack = targetPlanet.getDeathRate() * targetPlanet.getSpaceShips();
                    //----------------------------------------  6 Planeta conquistado  ----------------------------------------//
                    if(initialPlanetPowerAttack > targetPlanetPowerAttack){               
                        targetPlanet.setOwner(fleets.get(i).getAttackingPlayer().getName());
                        if(targetPlanet.getSpaceShips() == 0){
                            targetPlanet.setSpaceShips(spaceShipsAmount);
                        }
                        else{
                            targetPlanet.setSpaceShips((int)Math.round(((initialPlanet.getDeathRate() * spaceShipsAmount) - targetPlanet.getDeathRate() * targetPlanet.getSpaceShips())));
                        }
                        targetSquare.setColor(fleets.get(i).getAttackingPlayer().getColor());
                        //Agregar al jugador el nuevo planeta
                        for(int j = 0; j < players.size(); j++){
                            if(fleets.get(i).getAttackingPlayer() == players.get(j)){
                                players.get(j).getPlanets().add(targetPlanet);
                            }
                         }    
                        //----------------------------------------  7 Planeta neutral   ----------------------------------------//
                        if(targetPlanet.isNeutral()){  
                            targetPlanet.setNeutral(false);
                            //Remover el planeta de los planetas neutrales
                            for(int j = 0; j < neutralPlanets.size(); j++){
                                if(targetPlanet == neutralPlanets.get(j)){
                                    neutralPlanets.remove(j);
                                }
                            }
                        }
                        //----------------------------------------  7 Planeta con propietario   ----------------------------------------//   
                        else{
                            //Remover el planeta del jugador propietario
                            for(int j = 0; j < players.size(); j++){
                                for(int k = 0; k < players.get(j).getPlanets().size(); k++){
                                    if(targetPlanet == players.get(j).getPlanets().get(k)){
                                        players.get(j).getPlanets().remove(k);
                                    }
                                }
                            }
                        }
                        konquest.printArrivalSpaceShipsMessages(2, attackingPlayer.getName(), targetPlanet.getName(), spaceShipsAmount, attackingPlayer.getColor());
                    }
                    //----------------------------------------  6 Planeta defendido  ----------------------------------------//
                    else{
                        targetPlanet.setSpaceShips((int)Math.round((targetPlanet.getDeathRate() * targetPlanet.getSpaceShips()) - (initialPlanet.getDeathRate() * spaceShipsAmount)));
                        konquest.printArrivalSpaceShipsMessages(1, attackingPlayer.getName(), targetPlanet.getName(), spaceShipsAmount, attackingPlayer.getColor());
                    }
                }
                targetSquare.reload();    
                fleets.remove(i);
                i--;   
            }
        }
    }    
            
    /*
    Metodo encargado de procesar el envio de naves de un planeta a otro.
        1. Resta al planeta inicial la cantidad de naves que se estan enviando.
        2. Restablece los valores de la clase a los valores iniciales.
        3. Modifica los elementos graficos de informacion del planeta inicial.
        4. Aumenta en una unidad el contador de flotas(fleetSent).
        5. Crea una nueva instancia de la clase FleetDTO y la agrega a las lista Fleets.
    */
    public void sendSpaceShips(int amount, Square initialSquare, Square targetSquare, int currentTurn, Player currentPlayer){
        subtractPlanets(initialSquare, amount);
        reestoreValues();
        initialSquare.reload();
        fleetsSent++;
        fleet = new FleetDTO(fleetsSent, initialSquare, targetSquare, amount, getArrivalTurn(currentTurn, calculateDistance(initialSquare, targetSquare)), currentPlayer);
        konquest.getFleets().add(fleet);
    }
    
    /*
    Metodo encargado de restar a un planeta determinada cantidad de naves.
    Recibe como parametros la casilla que contiene al planeta y la cantidad de naves a restar.
    */
    public void subtractPlanets(Square selectedSquare, int amount){
        selectedSquare.getPlanet().setSpaceShips(selectedSquare.getPlanet().getSpaceShips() - amount);
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
    public boolean verifyTurnCompletion(int actualTurn, int completionTurn){
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
    
    /*
    Metodo encargado de establecer al ganador del juego, o los jugadores que empataron
    al momento de terminarse el juego por finalizacion de turnos.
    Devuelve un String que contiene al ganador o los jugadores que empataron.
    */
    public String getWinner(List<Player> players){
        int amount = 0;
        String winner = "";
        boolean noWinner = false;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPlanets().size() > amount){
                amount = players.get(i).getPlanets().size();
            }
            else if(players.get(i).getPlanets().size() < amount){
                players.remove(i);
                i--;
            }
            else{
                noWinner = true;
            }
        }
        for(int i = 0; i < players.size(); i++){
            if(amount  == players.get(i).getPlanets().size()){
                winner = winner +"-"+ players.get(i).getName();
            }
        }
        if(noWinner){
            return "Ha sido un empate entre " + winner.replaceFirst("-", "");
        }
        else{
            return "El ganador es " + winner.replace("-", "");
        }
    }
    
    /*
    Metodo encargado de establecer si algun jugador ya ha conquistado todos los planetas. Verifica que no exista ningun 
    planeta nuetral en la lista neutralPlanets, si aun existen devuelve false, de lo contrario itera por cada uno
    de los jugadores y obtiene la longitud de la lista planets. Si un jugador posee los planetas todos los demas 
    tendran un 0 en su longitud, entonces si alguno de los jugadores no tiene 0 en su longitud se incrementa el 
    contador counter en uno. Si al final el contador es igual a 1, solo existe un jugador con todos los planetas
    por lo cual se debe terminar el juego y se devuelve true, caso contrario se devuelve false.
    */
    public boolean verifyPlanetsCompletion(List<Player> players, List<Planet> neutralPlanets){
        if(neutralPlanets.size() == 0){
            int counter = 0;
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getPlanets().size() != 0){
                    counter++;
                }
            }
            if(counter == 1){
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
    public void reestoreValues(){
        selectedSquareCounter = 0;
        measureDistance = false;
        sendSpaceShips =false;
        konquest.desactivateSpaceShipsAmountArea();
    } 
}