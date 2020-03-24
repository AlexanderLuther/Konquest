package com.hluther.gui;

import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
/**
 *
 * @author helmuth
 */
public class Board extends JPanel{
    
    private Random random = new Random(System.currentTimeMillis());
    private Square[][] squares;
    private int rows;
    private int columns;
    private Konquest konquest;
    private List<Player> players;
    private List<Planet> neutralPlanets;
    private Map map;
    
    /*
    Constructor de la clase.
    Llama a inicializar la clase y a la cracion del tablero.
    */
    public Board(int rows, int columns, Konquest konquest) {
        this.rows = rows;
        this.columns = columns;
        this.konquest = konquest;
        this.map = konquest.getMap();
        this.players = konquest.getPlayers();
        this.neutralPlanets = konquest.getNeutralPlanets();
        initComponents();
        createBoard();
        addPlanets();
    }

    public Map getMap() {
        return map;
    }
    
    /*
    Metodo encargado de constriur el tablero.
    Crea una matriz de objetos de la clase Square, agrega dentro de cada espacio de la matriz una nueva instancia
    de la clase Square, dandole como atributos la cordena X, la cordenada Y y el tablero al que pertenece(board).
    */
    private void createBoard(){
        squares = new Square[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                squares[i][j] = new Square(i, j, this);
                this.add(squares[i][j]);
            }
        }
    }

    /*
    Metodo encargado de agregar un planeta dentro de una casilla de juego.
        1. Se itera por cada jugador.
        2. Se itera por cada planeta del que es propitario el jugador. 
        3. Se establecen los valores random iniciales de las cordenadas X y Y.
        4. Mientras la casilla no se encuentre vacia se obtendran nuevas coordenadas X y Y.
        5. Se agrega el planeta ademas se modifican varios atributos  de la casilla resultante.
        6. Se itera por cada planeta neutral y se realizan los pasos 3, 4, y 5.
    */
    private void addPlanets(){
        int x;
        int y;        
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < players.get(i).getPlanets().size(); j++){
                x = getRandomIndex(rows);
                y = getRandomIndex(columns);
                while(squares[x][y].getPlanet() != null){
                    x = getRandomIndex(rows);
                    y = getRandomIndex(columns);
                }   
                squares[x][y].setPlanet(players.get(i).getPlanets().get(j));
                squares[x][y].setColor(players.get(i).getColor());
                squares[x][y].setImage(getRandomIndex(4));
                squares[x][y].setGuiInformation();
                squares[x][y].setOpaque(true);
            }
        }
        for(int i = 0; i < neutralPlanets.size(); i++){  
            x = getRandomIndex(rows);
            y = getRandomIndex(columns);
            while(squares[x][y].getPlanet() != null){
                x = getRandomIndex(rows);
                y = getRandomIndex(columns);
            }   
            squares[x][y].setPlanet(neutralPlanets.get(i));
            squares[x][y].setImage(getRandomIndex(4));
            squares[x][y].setGuiInformation();
        }
    }
    
    /*
    Metodo encargado de devolver un numero aleatorio entre 0 y el limite que recibe como parametro.
    */
    private int getRandomIndex(int limit){
        return random.nextInt(limit);
    }

    /*
    Metodo encargado de establecer las configuraciones iniciales del tablero.
        1. Establece en false el valor opaque del tablero.
        2. Establece bordes de linea blancos alrededor del tablero
        3. Establece el tamano inicial del tablero.
        4. Establece el layout del tablero.
    */
    private void initComponents() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(700, 700));
        this.setLayout(new GridLayout(rows, columns));
    }    
}