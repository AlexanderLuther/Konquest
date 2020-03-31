package com.hluther.drivers;

import com.hluther.entityclasses.FleetDTO;
import com.hluther.entityclasses.FleetInformation;
import com.hluther.gui.Konquest;
import com.hluther.gui.Square;
import com.hluther.entityclasses.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author helmuth
 */
public class ReplayActions {
    
    private GameActions gameActiosDriver;
    private List<FleetInformation> fleetsInformation = new ArrayList<>();
    private Konquest konquest;
    private FleetDTO fleet;

    public ReplayActions(GameActions gameActiosDriver, List<FleetInformation> fleetInformations, Konquest konquest) {
        this.gameActiosDriver = gameActiosDriver;
        this.fleetsInformation =fleetInformations;
        this.konquest = konquest;
    }
    
    public void createFleets(List<FleetDTO> fleets, Square[][] square){
        for(FleetInformation currentFleet : fleetsInformation){
            fleet = new FleetDTO(currentFleet.getInitialTurn(), square[currentFleet.getInitialRow()][currentFleet.getInitialColumn()], square[currentFleet.getTargetRow()][currentFleet.getTargetColumn()], currentFleet.getSpaceShipsAmount(), currentFleet.getArrivalTurn(), currentFleet.getAttackingPlayer());
            fleets.add(fleet);
        }
    }
    
    public void setSpaceShipsSendings(List<FleetDTO> fleets, int currentTurn){
        for(FleetDTO currentFleet : fleets){
            if(currentTurn == currentFleet.getInitialTurn()){
                konquest.printMessagesSendingSpaceShips(currentFleet.getAttackingPlayer().getName(), currentFleet.getInitialPlanetName(), currentFleet.getTargetPlanetName(), currentFleet.getSpaceShipsAmount());
                gameActiosDriver.sendSpaceShips(currentFleet.getSpaceShipsAmount(), currentFleet.getInitialSquare(), currentFleet.getTargetSquare(), currentTurn, currentFleet.getAttackingPlayer());
            }
        }
    }
    
    public void doFastReplay(List<FleetDTO> fleets, Turn turnsDriver, List<Player> players){
        int maxTurn = 0;
        for(FleetDTO curreFleet : fleets){
            if(curreFleet.getInitialTurn() > maxTurn){
                maxTurn = curreFleet.getInitialTurn();
            }
        }
        //Si el turno maximo es igual al turno actual dar el control de todo y dejar los replays
        while(turnsDriver.getTurn() < maxTurn){
                turnsDriver.setActualIndex(players.size() - 1);
                konquest.endTurn();
                setSpaceShipsSendings(fleets, turnsDriver.getTurn());
        }
    }
   
}
