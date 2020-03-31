package com.hluther.gui;

import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private boolean interactive;
    private Konquest konquest;
    private List<Player> players;
    private List<Planet> neutralPlanets;
    private Map map;
    private Square selectedSquare;
    
    /*
    Constructor de la clase.
    Llama a inicializar la clase y a la cracion del tablero.
    */
    public Board(int rows, int columns, boolean interactive, Konquest konquest) {
        this.rows = rows;
        this.columns = columns;
        this.konquest = konquest;
        this.interactive = interactive;
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
    
    public Square getSelectedSquare(){
        return selectedSquare;
    }
    
    public void setSelectedSquare(Square selectedSquare){
        this.selectedSquare = selectedSquare;
        konquest.setSelectedSquare(selectedSquare);
    }

    public boolean isInteractive() {
        return interactive;
    }

    public Square[][] getSquares() {
        return squares;
    }
    
    
    //Metodo encargado de actualizar todas las casilla del tablero que contienen un planeta.
    public void reload(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if(squares[i][j].getPlanet() != null){
                    squares[i][j].reload();
                }
            }
        }
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
        6. Si el mapa es random se agrega a la lista neutralPlanets la cantidad de planetas que indica el mapa.
        7. Se itera por cada planeta neutral y se realizan los pasos 3, 4, y 5.
    */
    private void addPlanets(){
        int x;
        int y;        
        for(Player currentPlayer : players){
            for(Planet currentPlanet : currentPlayer.getPlanets()){
                if(interactive){
                    x = getRandomInt(rows, 0);
                    y = getRandomInt(columns, 0);
                    while(squares[x][y].getPlanet() != null){
                        x = getRandomInt(rows, 0);
                        y = getRandomInt(columns, 0);
                    }
                    currentPlanet.setRow(x);
                    currentPlanet.setColumn(y);
                }
                else{
                    x = currentPlanet.getRow();
                    y = currentPlanet.getColumn();
                }
                squares[x][y].setPlanet(currentPlanet);
                squares[x][y].setColor(currentPlayer.getColor());
                squares[x][y].setImage(getRandomInt(4, 0));
                squares[x][y].setGuiInformation();
            } 
        }
        if(interactive){
            if(map.isRandom()){
                for(int j = 0; j < map.getNeutralPlanets(); j++){
                    Planet planet = new Planet(getRandomName(), 5, getRandomInt(11, 1), getRandomDouble(), true, false);
                    planet.setOwner("Neutral");
                    neutralPlanets.add(planet); 
                }    
            }
        }   
        for(Planet currentPlanet : neutralPlanets){
            if(interactive){
                x = getRandomInt(rows, 0);
                y = getRandomInt(columns, 0);
                while(squares[x][y].getPlanet() != null){
                    x = getRandomInt(rows, 0);
                    y = getRandomInt(columns, 0);
                }
                currentPlanet.setRow(x);
                currentPlanet.setColumn(y);
            }
            else{
                x = currentPlanet.getRow();
                y = currentPlanet.getColumn();
            }
            squares[x][y].setPlanet(currentPlanet);
            squares[x][y].setImage(getRandomInt(4, 0));
            squares[x][y].setGuiInformation();
        }
    }
    
    /*
    Metodo encargado de devolver un numero aleatorio entre 0 y el limite que recibe como parametro.
    */
    private int getRandomInt(int limit, int type){
        if(type == 0) return random.nextInt(limit);
        else return random.nextInt(limit)+5; 
    }
    
    /*
    Metodo encargado de devolver un numero aleatorio entre 0.200 y 0.899 con presicion de 6 cifras decimales.
    */
    private double getRandomDouble(){
        double value = random.nextDouble();
        if(value > 0.899 || value < 0.200){
            value = getRandomDouble();
        }
        return new BigDecimal(value).setScale(6, RoundingMode.HALF_EVEN).doubleValue();
    }
    
    //Metodo encargado de generar nombres de longitud 3 al azar.
    private String getRandomName(){
        char n;
        Random rnd = new Random();
        String name = new String();
        for (int i = 0; i < 3 ; i++) {
            n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
        name += n;
        }
        return name;            
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