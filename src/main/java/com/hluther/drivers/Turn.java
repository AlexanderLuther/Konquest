package com.hluther.drivers;

import java.util.List;
import com.hluther.entityclasses.Player;
/**
 *
 * @author helmuth
 */
public class Turn {
    
    private List<Player> players;
    private Player actualPlayer;
    private int turn;
    private int actualIndex;
    
    public Turn(List<Player> players){
        this.players = players;
        this.turn = 0;
        this.actualIndex = -1;
    }

    /*
    Metodo encargado de establecer el jugador en turno y el numero de turno actual.
        1. Si la variable de tipo entero actualIndex es igual a la longitud de la lista players menos una unidad 
           entonces se establece actualIndex en 0, de lo contrario se le suma una unidad al valor que posee.
        2. Se llama al metodo setActualPlayer enviandole como parametro la instancia Player de la lista en el
           indice dado por actualIndex.
        3. Si actualIndex es igual a 0 se llama al metodo setTurn enviando como parametro el valor contenido
           dentro de la variable turn mas una unidad.
    */
    public void setTurn(){
        if(actualIndex == players.size() - 1){
            actualIndex = 0;
        }
        else{
            actualIndex++;
        }
        setActualPlayer(players.get(actualIndex));
        if(actualIndex == 0){
            setTurn(turn+ 1);
        }
    }
    
    public Player getActualPlayer(){
        return actualPlayer;
    }
    
    public void setActualPlayer(Player actualPlayer){
        this.actualPlayer = actualPlayer;
    }
    
    public int getTurn(){
        return turn;
    }
    
    public void setTurn(int turn){
        this.turn = turn;
    }

    public int getActualIndex() {
        return actualIndex;
    }
}
