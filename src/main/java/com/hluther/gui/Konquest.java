package com.hluther.gui;

import com.hluther.drivers.Analisys;
import com.hluther.drivers.Files;
import com.hluther.drivers.GameActions;
import com.hluther.drivers.MapConfigFile;
import com.hluther.drivers.Turn;
import com.hluther.entityclasses.FleetDTO;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
/**
 *
 * @author helmuth
 */
public class Konquest extends javax.swing.JFrame {
    
    private Analisys analisisDriver = new Analisys();
    private MapConfigFile mapConfigFileDriver = new MapConfigFile();
    private Turn turnsDriver;
    private GameActions gameActionsDriver;
    private InformationTable informationTable;
    private Files filesDriver = new Files();
    private List<Planet> neutralPlanets = new ArrayList<Planet>();
    private List<Player> players = new ArrayList<Player>();
    private List<FleetDTO> fleets = new ArrayList<FleetDTO>();
    private Map map = new Map();        
    private GameSettings gameCreator;
    private BackGroundImage backGroundImage;
    private Board board;
    private Square initialSquare;
    private Square targetSquare;
    private String color = "";
    
    //----------------------------------------- Constructor de la clase. -----------------------------------------//
    public Konquest() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        messagesPane.setVisible(false);
        actionsPane.setVisible(false);
        optionsBar.setVisible(false);
        spaceShipsAmount.setEditable(false);
        measureMenuItem.setEnabled(false);
        fleetMenuItem.setEnabled(false);
        endGameMenuItem.setEnabled(false);
        sendShipsMenuItem.setEnabled(false);
        endTurnMenuItem.setEnabled(false);
        try {
            backGroundImage = new BackGroundImage(ImageIO.read(this.getClass().getResource("/konquestBackGround.jpg")));     
            this.backGroundPanel.setBorder(backGroundImage);
        } 
        catch (IOException ex) {
            System.out.println("Imagen no encontrada");
        }
    }
  
    //----------------------------------------- Getters y Setters de la clase. -----------------------------------------//
    public MapConfigFile getMapConfigFileDriver() {
        return mapConfigFileDriver;
    }
    
    public Analisys getAnalisisDriver() {
        return analisisDriver;
    }
    
    public Files getFilesDriver() {
        return filesDriver;
    }
    
    public List<Planet> getNeutralPlanets() {
        return neutralPlanets;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public Map getMap() {
        return map;
    }
    
    public JPasswordField getSpaceShipsAmount() {
        return spaceShipsAmount;
    }

    public List<FleetDTO> getFleets() {
        return fleets;
    }
    
    public void setSelectedSquare(Square selectedSquare) {
        switch(gameActionsDriver.getSelectedSquareCounter()){
            case 1:
                this.initialSquare = selectedSquare;
                gameActionsDriver.setSelectedSquareCounter(2);
            break;
            case 2:
                if(selectedSquare != initialSquare){
                    this.targetSquare = selectedSquare;
                    gameActionsDriver.setSelectedSquareCounter(3);
                }
            break;
        }
        gameActionsDriver.executeAction(selectedSquare, turnsDriver.getActualPlayer());
    }
    
    //----------------------------------------- Metodos de la clase. -----------------------------------------//
    
    /*Metodo encargado de iniciar un nuevo juego.
        1. Crea nuevas instancias del tablero(Board), controlador del juego(GameActions) y controlador de turnos(Turn).
        2. Hace visibles las areas de utilidades.
        3. Si ya existia un tablero dentro del boardPanel entonces se remueve. Se agrega el nuevo tablero al boardPanel.
        5. Se establece el primer turno, el color del nombre del jugador en turno y se imprimen mensajes correspondientes
           al turno actual.
    */
    public void startGame(){
        //-------------------------     1   -------------------------//   
        board = new Board(map.getRows(), map.getColumns(), this);
        gameActionsDriver = new GameActions(this);
        turnsDriver = new Turn(players);
        informationTable = new InformationTable(this, true);
        //-------------------------     2   -------------------------//   
        messagesPane.setVisible(true);
        actionsPane.setVisible(true);
        optionsBar.setVisible(true);
        measureMenuItem.setEnabled(true);
        fleetMenuItem.setEnabled(true);
        endGameMenuItem.setEnabled(true);
        sendShipsMenuItem.setEnabled(true);
        endTurnMenuItem.setEnabled(true);
        //-------------------------     3   -------------------------//   
        boardPanel.removeAll();
        boardPanel.revalidate();
        boardPanel.add(board);
        this.repaint();
        //-------------------------     4   -------------------------//   
        turnsDriver.setTurn();
        color = getTextColor(turnsDriver.getActualPlayer().getColor());
        printTurnValues();       
    }
    
    //Metodo encargado de establecer el color del nombre del jugador en turno.
    public String getTextColor(Color playerColor){
        if(playerColor == Color.blue) return "blue";
        else if(playerColor == Color.yellow) return "yellow";
        else if(playerColor == Color.green) return "lime";
        else if(playerColor == Color.red) return "red";
        else if(playerColor == Color.orange) return "orange";
        else if(playerColor == Color.lightGray) return "silver";
        else if(playerColor == Color.cyan) return "aqua";
        else if(playerColor == Color.darkGray) return "gray";
        else if(playerColor == Color.white) return "white";
        else if(playerColor == Color.magenta) return "fuchsia";
        else return "black";
    }
    
    //Metodo encargado de finalizar el turno
    private void endTurn(){
        gameActionsDriver.endTurn();
        turnsDriver.setTurn();
        if(!gameActionsDriver.verifyTurnCompletion(turnsDriver.getTurn(), map.getCompletion())){
            gameActionsDriver.validateArrivalSpaceShips(turnsDriver.getTurn(), players, neutralPlanets);
            //Validar final por conquista total. 
            if(!gameActionsDriver.verifyPlanetsCompletion(players, neutralPlanets)){
                if(turnsDriver.getActualIndex() == 0){
                    gameActionsDriver.increaseSpaceShips(players, neutralPlanets);
                    board.reload();
                }
                color = getTextColor(turnsDriver.getActualPlayer().getColor());
                printTurnValues();
            }
            else{
                JOptionPane.showMessageDialog(this, "<html><center><font size=4 color=purple>En hora buena, has conquistado todos los planetas!. Fin del juego.<br>" + gameActionsDriver.getWinner(players) +"</font></center></html>");
                desactivateGameArea();
            } 
        }
        else{
            JOptionPane.showMessageDialog(this, "<html><center><font size=4 color=purple>Se ha alcanzado el limite de turnos. Fin del juego.<br>" + gameActionsDriver.getWinner(players) +"</font></center></html>");
            desactivateGameArea();
        }
    }   
    
    //Metodo encargado de reestablecer a los valores iniciales los componentes graficos del area de juegos.
    private void desactivateGameArea(){
        messagesPane.setVisible(false);
        actionsPane.setVisible(false);
        optionsBar.setVisible(false);
        spaceShipsAmount.setEditable(false);
        measureMenuItem.setEnabled(false);
        fleetMenuItem.setEnabled(false);
        endGameMenuItem.setEnabled(false);
        sendShipsMenuItem.setEnabled(false);
        endTurnMenuItem.setEnabled(false);
        boardPanel.removeAll();
        boardPanel.revalidate();
        fleets.clear();
        messagesArea.setText("");
    }
    
    //Metodo encargado de pemitir el ingreso de numeros en el JPasswordField
    public void activateSpaceShipsAmountArea(){
        spaceShipsAmount.setEditable(true);
        spaceShipsAmount.setBackground(Color.green);
        spaceShipsAmount.requestFocus();
    }
  
    //Metodo encargado de bloquear el ingreso de numeros en el JPasswordField
    public void desactivateSpaceShipsAmountArea(){
        spaceShipsAmount.setEditable(false);
        spaceShipsAmount.setBackground(Color.black);
        spaceShipsAmount.requestFocus();
        spaceShipsAmount.setText("");
    }
    
    //Metodo encargado de iniciar con el proceso de medicion de distancia. 
    private void measureDistance(){
        gameActionsDriver.setMeasureDistance(true);
        gameActionsDriver.setSelectedSquareCounter(1);
        printMeasureDistanceMessages(0);
    }
    
    //Metodo encargado de iniciar con el proceso de envio de naves.
    private void sendSpaceShips(){
        gameActionsDriver.setSendSpaceShips(true);
        gameActionsDriver.setSelectedSquareCounter(1);
        printSendSpaceShipsMessages(0);
    }
     
    //Metodo encargado de mostrar en pantalla el resultado de una medicion de distancias.
    public void printMeasuredDistance(){
        double distance = gameActionsDriver.calculateDistance(initialSquare, targetSquare);
        JOptionPane.showMessageDialog(this, "<html><center><font color = \"purple\"> La distancia del planeta <b>"+initialSquare.getPlanet().getName()+"</b> al planeta <b>"+targetSquare.getPlanet().getName()+"</b> es de <b>" + distance + "</b> años luz."
                                      + "<br> Una nave partiendo en este turno llegaria en el turno <b>"+gameActionsDriver.getArrivalTurn(turnsDriver.getTurn(), distance)+"</b> </font></center></html>");
    }
    
     //Metodo encargado de llenar las areas de mensajes con valores correspondientes al turno.
    public void printTurnValues(){  
        optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione una accion. </font></html>");
        turnLabel.setText("Turno No."+turnsDriver.getTurn());
    }
    
    //Metodo encargado de llenar las areas de mensajes con valores correspondientes a la medicion de una distancia.
    public void printMeasureDistanceMessages(int type){
        switch(type){
            case 0:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta inicial. </font></html>");
            break;
            case 1:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta final. </font></html>");
            break;
        }
    }
    
    //Metodo encargado de llenar las areas de mensajes con valores correspondientes al envio de naves.
    public void printSendSpaceShipsMessages(int type){
        switch(type){
            case 0:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta origen. </font></html>");
            break;
            case 1:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta a atacar. </font></html>");
            break;
            case 2:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Ingrese la cantidad de naves y presione enter. </font></html>");
            break;
        } 
    }
    
     //Metodo encargado de llenar las areas de mensajes con valores correspondientes al resultado del envio de naves.
    public void printArrivalSpaceShipsMessages(int type, String attackingPlayer, String targetPlanet, int amount, Color playerColor){
        switch(type){
            case 0: 
                JOptionPane.showMessageDialog(this, "<html><font color=purple> Han llegado refuerzos (<b>"+amount+"</b> naves) al planeta <font color ="+getTextColor(playerColor)+"><b>"+targetPlanet+"</b></font></font></html>");
                messagesArea.setText(messagesArea.getText() + "\n --Turno " + turnsDriver.getTurn() + ":--\n Han llegado refuerzos ("+amount+" naves) al planeta "+targetPlanet+"");
            break;
            case 1:
                JOptionPane.showMessageDialog(this, "<html><font color=purple> El planeta <b>"+targetPlanet+"</b> se ha defendido del ataque de <font color="+getTextColor(playerColor)+"><b>"+attackingPlayer+"</b></font></font></html>");
                messagesArea.setText(messagesArea.getText() + "\n --Turno " + turnsDriver.getTurn() + ":--\n El planeta "+targetPlanet+" se ha defendido del ataque de "+attackingPlayer);
            break;  
            case 2:
                JOptionPane.showMessageDialog(this, "<html><font color=purple> El planeta <b>"+targetPlanet+"</b> ha sido conquistado por <font color="+getTextColor(playerColor)+"><b>"+attackingPlayer+"</b></font></font></html>");
                messagesArea.setText(messagesArea.getText() + "\n --Turno " + turnsDriver.getTurn() + ":--\n El planeta "+targetPlanet+" ha sido conquistado por "+attackingPlayer);
            break; 
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        backGroundPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        optionsBar = new javax.swing.JToolBar();
        measureDistanceButton = new javax.swing.JButton();
        sendShipsButton = new javax.swing.JButton();
        fleetButton = new javax.swing.JButton();
        endTurnButton = new javax.swing.JButton();
        messagesPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        turnLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagesArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        actionsPane = new javax.swing.JPanel();
        optionsLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        variousPanel = new javax.swing.JPanel();
        spaceShipsAmount = new javax.swing.JPasswordField();
        cancelActionButton = new javax.swing.JButton();
        panel123 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        game = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        endGameMenuItem = new javax.swing.JMenuItem();
        measureMenuItem = new javax.swing.JMenuItem();
        sendShipsMenuItem = new javax.swing.JMenuItem();
        fleetMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        endTurnMenuItem = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Konquest");
        setMinimumSize(new java.awt.Dimension(990, 700));

        backGroundPanel.setAutoscrolls(true);
        backGroundPanel.setEnabled(false);
        backGroundPanel.setLayout(new java.awt.BorderLayout());

        menuPanel.setMaximumSize(new java.awt.Dimension(32767, 20));
        menuPanel.setMinimumSize(new java.awt.Dimension(100, 20));
        menuPanel.setPreferredSize(new java.awt.Dimension(2880, 20));
        menuPanel.setLayout(new java.awt.GridLayout(1, 0));

        optionsBar.setRollover(true);

        measureDistanceButton.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        measureDistanceButton.setForeground(new java.awt.Color(0, 51, 102));
        measureDistanceButton.setText("Medir distancia");
        measureDistanceButton.setActionCommand("");
        measureDistanceButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        measureDistanceButton.setBorderPainted(false);
        measureDistanceButton.setFocusPainted(false);
        measureDistanceButton.setMaximumSize(new java.awt.Dimension(170, 20));
        measureDistanceButton.setMinimumSize(new java.awt.Dimension(170, 20));
        measureDistanceButton.setPreferredSize(new java.awt.Dimension(170, 20));
        measureDistanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                measureDistanceButtonActionPerformed(evt);
            }
        });
        optionsBar.add(measureDistanceButton);

        sendShipsButton.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        sendShipsButton.setForeground(new java.awt.Color(0, 51, 102));
        sendShipsButton.setText("Enviar naves");
        sendShipsButton.setActionCommand("");
        sendShipsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        sendShipsButton.setBorderPainted(false);
        sendShipsButton.setFocusPainted(false);
        sendShipsButton.setMaximumSize(new java.awt.Dimension(150, 20));
        sendShipsButton.setMinimumSize(new java.awt.Dimension(150, 20));
        sendShipsButton.setPreferredSize(new java.awt.Dimension(150, 20));
        sendShipsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendShipsButtonActionPerformed(evt);
            }
        });
        optionsBar.add(sendShipsButton);

        fleetButton.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        fleetButton.setForeground(new java.awt.Color(0, 51, 102));
        fleetButton.setText("Consultar flota");
        fleetButton.setActionCommand("");
        fleetButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        fleetButton.setBorderPainted(false);
        fleetButton.setFocusPainted(false);
        fleetButton.setMaximumSize(new java.awt.Dimension(150, 20));
        fleetButton.setMinimumSize(new java.awt.Dimension(150, 20));
        fleetButton.setPreferredSize(new java.awt.Dimension(150, 20));
        fleetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fleetButtonActionPerformed(evt);
            }
        });
        optionsBar.add(fleetButton);

        endTurnButton.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        endTurnButton.setForeground(new java.awt.Color(0, 51, 102));
        endTurnButton.setText("Fin del turno");
        endTurnButton.setActionCommand("");
        endTurnButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        endTurnButton.setBorderPainted(false);
        endTurnButton.setFocusPainted(false);
        endTurnButton.setMaximumSize(new java.awt.Dimension(150, 20));
        endTurnButton.setMinimumSize(new java.awt.Dimension(150, 20));
        endTurnButton.setPreferredSize(new java.awt.Dimension(150, 20));
        endTurnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTurnButtonActionPerformed(evt);
            }
        });
        optionsBar.add(endTurnButton);

        menuPanel.add(optionsBar);

        backGroundPanel.add(menuPanel, java.awt.BorderLayout.PAGE_START);

        messagesPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        messagesPane.setMinimumSize(new java.awt.Dimension(10, 150));
        messagesPane.setPreferredSize(new java.awt.Dimension(100, 150));
        messagesPane.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Mensajes");
        messagesPane.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        turnLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        turnLabel.setForeground(new java.awt.Color(0, 51, 102));
        turnLabel.setText("Turno No.");
        messagesPane.add(turnLabel, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        messagesArea.setEditable(false);
        messagesArea.setBackground(new java.awt.Color(0, 0, 0));
        messagesArea.setColumns(20);
        messagesArea.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        messagesArea.setForeground(new java.awt.Color(255, 255, 255));
        messagesArea.setRows(5);
        jScrollPane1.setViewportView(messagesArea);

        messagesPane.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        backGroundPanel.add(messagesPane, java.awt.BorderLayout.PAGE_END);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        actionsPane.setBackground(new java.awt.Color(0, 0, 0));
        actionsPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        actionsPane.setMinimumSize(new java.awt.Dimension(10, 50));
        actionsPane.setPreferredSize(new java.awt.Dimension(100, 50));
        actionsPane.setLayout(new java.awt.BorderLayout());

        optionsLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        optionsLabel.setForeground(new java.awt.Color(255, 255, 255));
        optionsLabel.setText("Seleccione una accion");
        optionsLabel.setPreferredSize(new java.awt.Dimension(500, 19));
        actionsPane.add(optionsLabel, java.awt.BorderLayout.LINE_START);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        variousPanel.setOpaque(false);
        variousPanel.setPreferredSize(new java.awt.Dimension(300, 100));

        spaceShipsAmount.setBackground(new java.awt.Color(0, 0, 0));
        spaceShipsAmount.setForeground(new java.awt.Color(0, 51, 0));
        spaceShipsAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        spaceShipsAmount.setMinimumSize(new java.awt.Dimension(150, 35));
        spaceShipsAmount.setOpaque(false);
        spaceShipsAmount.setPreferredSize(new java.awt.Dimension(150, 35));
        spaceShipsAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                spaceShipsAmountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spaceShipsAmountKeyTyped(evt);
            }
        });
        variousPanel.add(spaceShipsAmount);

        cancelActionButton.setBackground(new java.awt.Color(0, 0, 0));
        cancelActionButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        cancelActionButton.setForeground(new java.awt.Color(255, 0, 0));
        cancelActionButton.setText("Cancelar Accion");
        cancelActionButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        cancelActionButton.setBorderPainted(false);
        cancelActionButton.setMinimumSize(new java.awt.Dimension(135, 35));
        cancelActionButton.setOpaque(true);
        cancelActionButton.setPreferredSize(new java.awt.Dimension(135, 35));
        cancelActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionButtonActionPerformed(evt);
            }
        });
        variousPanel.add(cancelActionButton);

        jPanel4.add(variousPanel, java.awt.BorderLayout.LINE_END);

        actionsPane.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.add(actionsPane, java.awt.BorderLayout.PAGE_START);

        panel123.setOpaque(false);
        panel123.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 100));
        panel123.add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel3.setMinimumSize(new java.awt.Dimension(100, 10));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 100));
        panel123.add(jPanel3, java.awt.BorderLayout.LINE_END);

        boardPanel.setOpaque(false);
        boardPanel.setLayout(new java.awt.GridLayout(1, 0));
        panel123.add(boardPanel, java.awt.BorderLayout.CENTER);

        jPanel2.add(panel123, java.awt.BorderLayout.CENTER);

        backGroundPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(backGroundPanel, java.awt.BorderLayout.CENTER);

        jMenuBar1.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 14)); // NOI18N

        jMenu2.setForeground(new java.awt.Color(102, 0, 255));
        jMenu2.setText("Archivo");
        jMenu2.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 1, 13)); // NOI18N
        jMenuBar1.add(jMenu2);

        game.setForeground(new java.awt.Color(102, 0, 255));
        game.setText("     Juego     ");
        game.setFont(new java.awt.Font("Serif", 1, 13)); // NOI18N

        newGameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        newGameMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        newGameMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivo-nuevo.png"))); // NOI18N
        newGameMenuItem.setText("Nuevo");
        newGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameMenuItemActionPerformed(evt);
            }
        });
        game.add(newGameMenuItem);

        endGameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        endGameMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        endGameMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        endGameMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eliminar.png"))); // NOI18N
        endGameMenuItem.setText("Finalizar partida");
        game.add(endGameMenuItem);

        measureMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        measureMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 13)); // NOI18N
        measureMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        measureMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cinta-metrica.png"))); // NOI18N
        measureMenuItem.setText("Medir distancia");
        measureMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                measureMenuItemActionPerformed(evt);
            }
        });
        game.add(measureMenuItem);

        sendShipsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        sendShipsMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 13)); // NOI18N
        sendShipsMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        sendShipsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/enviar.png"))); // NOI18N
        sendShipsMenuItem.setText("Enviar naves");
        sendShipsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendShipsMenuItemActionPerformed(evt);
            }
        });
        game.add(sendShipsMenuItem);

        fleetMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        fleetMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 13)); // NOI18N
        fleetMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        fleetMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/consultar.png"))); // NOI18N
        fleetMenuItem.setText("Consultar flota");
        fleetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fleetMenuItemActionPerformed(evt);
            }
        });
        game.add(fleetMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 13)); // NOI18N
        exitMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-sesion.png"))); // NOI18N
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        game.add(exitMenuItem);

        jMenuBar1.add(game);

        jMenu1.setForeground(new java.awt.Color(102, 0, 255));
        jMenu1.setText("Mover");
        jMenu1.setFont(new java.awt.Font("Serif", 1, 13)); // NOI18N

        endTurnMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        endTurnMenuItem.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 13)); // NOI18N
        endTurnMenuItem.setForeground(new java.awt.Color(102, 0, 255));
        endTurnMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adelante.png"))); // NOI18N
        endTurnMenuItem.setText("Fin del turno");
        endTurnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTurnMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(endTurnMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Metodo encargado de abrir el JDialog gameCreator. Posteriomente obtiene los valores proporcionados por el
    JDialog y los almacena en las listas players, neutralPlanets y la instancia de la clase Map map solo si el 
    valor startGame del JDialog es true. Por ultimo llama al metodo starGame().
    */
    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
        gameCreator = new GameSettings(this, true);
        gameCreator.setVisible(true);
        if(gameCreator.isStartGame()){
            neutralPlanets = gameCreator.getPlanets();
            players = gameCreator.getPlayers();
            map = gameCreator.getMap();
            startGame();
        }
    }//GEN-LAST:event_newGameMenuItemActionPerformed
    
    //Metodo encargado de cancelar la accion que fue seleccionada.
    private void cancelActionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionButtonActionPerformed
        gameActionsDriver.cancelAction();
    }//GEN-LAST:event_cancelActionButtonActionPerformed
    
    //Metodo encargado de llamar al metodo measureDistance
    private void measureDistanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_measureDistanceButtonActionPerformed
        measureDistance();
    }//GEN-LAST:event_measureDistanceButtonActionPerformed

    //Metodo encargado de llamar al metodo endTurn().
    private void endTurnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTurnButtonActionPerformed
        endTurn();
    }//GEN-LAST:event_endTurnButtonActionPerformed

    //Metodo encargado de cerrar el juego
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    //Metodo encargado de llamar al metodo measureDistance
    private void measureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_measureMenuItemActionPerformed
        measureDistance();
    }//GEN-LAST:event_measureMenuItemActionPerformed

    //Metodo encargado de llamar al metodo endTurn().
    private void endTurnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTurnMenuItemActionPerformed
        endTurn();
    }//GEN-LAST:event_endTurnMenuItemActionPerformed
    
    //Metodo encargado de llamar al metodo sendSpaceShips().    
    private void sendShipsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendShipsButtonActionPerformed
        sendSpaceShips();
    }//GEN-LAST:event_sendShipsButtonActionPerformed

    //Metodo encargado de limitar los caracteres ingresados en un JTextArea a solamente numeros.
    private void spaceShipsAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spaceShipsAmountKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume(); 
    }//GEN-LAST:event_spaceShipsAmountKeyTyped

    /*
    Metodo encargado de detectar cuando se presiona la tecla enter dentro del area de texto que recibe la cantidad de naves.
        1. Valida que la tecla presionada sea enter y que el area de texto sea editable en ese momento. Si no es asi no se 
           realiza ninguna accion.
        2. Valida que la cantidad de naves disponibles en el planeta no sea 0. Si es 0 no se realiza ninguna accion.
        3. Valida que la cantidad de naves disponibles en el planeta sea mayor o igual a la cantidad de naves ingresada.
           Si no es asi no se realiza ninguna accion.
        4. Se obtiene la cantidad de naves.
        5. Se llama al metodo sendSpaceShips enviando como parametros la casilla inicial, la casilla objetivo y la cantidad de naves.
        6. Se restablecen los valores iniciales de las areas de texto del turno.
    */
    private void spaceShipsAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spaceShipsAmountKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && spaceShipsAmount.isEditable()){
            try{
                if(initialSquare.getPlanet().getSpaceShips() != 0){
                    if(initialSquare.getPlanet().getSpaceShips() >= Integer.parseInt(spaceShipsAmount.getText())){
                        gameActionsDriver.sendSpaceShips(Integer.parseInt(spaceShipsAmount.getText()), initialSquare, targetSquare, turnsDriver.getTurn(), turnsDriver.getActualPlayer());
                        printTurnValues();
                    }   
                }
            }
            catch(NullPointerException e){}
            catch(NumberFormatException e){}
        }
    }//GEN-LAST:event_spaceShipsAmountKeyPressed
  
    //Metodo encargado de llamar al metodo sendSpaceShips().
    private void sendShipsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendShipsMenuItemActionPerformed
        sendSpaceShips();
    }//GEN-LAST:event_sendShipsMenuItemActionPerformed

    private void fleetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fleetButtonActionPerformed
        informationTable.fillTable();
        informationTable.setVisible(true);
    }//GEN-LAST:event_fleetButtonActionPerformed

    private void fleetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fleetMenuItemActionPerformed
        informationTable.fillTable();
        informationTable.setVisible(true);
    }//GEN-LAST:event_fleetMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Konquest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Konquest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Konquest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Konquest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Konquest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPane;
    private javax.swing.JPanel backGroundPanel;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JButton cancelActionButton;
    private javax.swing.JMenuItem endGameMenuItem;
    private javax.swing.JButton endTurnButton;
    private javax.swing.JMenuItem endTurnMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton fleetButton;
    private javax.swing.JMenuItem fleetMenuItem;
    private javax.swing.JMenu game;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton measureDistanceButton;
    private javax.swing.JMenuItem measureMenuItem;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JTextArea messagesArea;
    private javax.swing.JPanel messagesPane;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JToolBar optionsBar;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JPanel panel123;
    private javax.swing.JButton sendShipsButton;
    private javax.swing.JMenuItem sendShipsMenuItem;
    private javax.swing.JPasswordField spaceShipsAmount;
    private javax.swing.JLabel turnLabel;
    private javax.swing.JPanel variousPanel;
    // End of variables declaration//GEN-END:variables
}
