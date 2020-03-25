package com.hluther.gui;

import com.hluther.drivers.Analisys;
import com.hluther.drivers.Files;
import com.hluther.drivers.GameActions;
import com.hluther.drivers.MapConfigFile;
import com.hluther.drivers.Turn;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 *
 * @author helmuth
 */
public class Konquest extends javax.swing.JFrame {
    
    private Analisys analisisDriver = new Analisys();
    private MapConfigFile mapConfigFileDriver = new MapConfigFile();
    private Turn turnsDriver;
    private GameActions gameActionsDriver;
    private Files filesDriver = new Files();
    private List<Planet> neutralPlanets = new ArrayList<Planet>();
    private List<Player> players = new ArrayList<Player>();
    private Map map = new Map();        
    private GameSettings gameCreator;
    private BackGroundImage backGroundImage;
    private Board board;
    private Square initialSquare;
    private Square targetSquare;
    private String color = "";
    private int selectedSquareCounter = 0;
    private boolean endAction = false;
    
    //----------------------------------------- Constructor de la clase. -----------------------------------------//
    public Konquest() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        messagesPane.setVisible(false);
        actionsPane.setVisible(false);
        optionsBar.setVisible(false);
        spaceShipsAmount.setEditable(false);
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

    public void setSelectedSquare(Square selectedSquare) {
        //Asignar valor a el planeta y modificar el contador
        switch(selectedSquareCounter){
            case 1:
                this.initialSquare = selectedSquare;
                selectedSquareCounter++;
            break;
            case 2:
                this.targetSquare = selectedSquare;
                selectedSquareCounter++;
            break;
        }
        
        //Establecer si se esta midiendo distancia o si se estan enviando naves.
        if(gameActionsDriver.isMeasureDistance()){
            switch(selectedSquareCounter){
                case 2:
                    printMeasureDistanceMessages(1);
                break;
                case 3:
                    JOptionPane.showMessageDialog(this, "La distancia es de: " + gameActionsDriver.calculateDistance(initialSquare, targetSquare));
                    printTurnValues();
                    gameActionsDriver.setMeasureDistance(false);
                    selectedSquareCounter = 0;
                break;
            }           
        }
        else if(gameActionsDriver.isSendSpaceShips()){
        
        }
    }
    
    //----------------------------------------- Metodos de la clase. -----------------------------------------//
    public void startGame(){
        if(board != null){
            boardPanel.remove(board);
            boardPanel.revalidate();
        }
        //Hacer visibles las areas de utilidades
        messagesPane.setVisible(true);
        actionsPane.setVisible(true);
        optionsBar.setVisible(true);
        board = new Board(map.getRows(), map.getColumns(), this);
        boardPanel.add(board);
        this.repaint();
        
        turnsDriver = new Turn(players);
        gameActionsDriver = new GameActions();
        selectedSquareCounter = 0;
        verifyCompletion();        
    }
    
    
    /*
    Metodo encargado de verificar si se debe realizar el cambio de turno o finalizar la partida en 
    base a la cantidad de turnos jugados.
        1. Valida si el atributo completion de la instancia map es igual a -1.
            Si es true:
                Compara si el turno actual es igual al turno de finalizacion.
                    Si ambos turnos son iguales se procede a finalizar la partida.
                    Si no son iguales se realiza una llamado al  metodo changeTurn().
            Si es false:
                Llama al metodo changeTurn().
    */
    private void verifyCompletion(){
        if(map.getCompletion() != -1){
            if(turnsDriver.getTurn() == map.getCompletion()){
                JOptionPane.showMessageDialog(this, "Finalizar juego");
            }
            else{
                changeTurn();
                setPlayerNameColor();
                printTurnValues();
            }
        }
        else{
            changeTurn();
            setPlayerNameColor();
            printTurnValues();
        }
    }
     
    //Metodo encargado de realizar el cambio de turno.
    private void changeTurn(){
        turnsDriver.setTurn();
    }
    
    //Metodo encargado de llenar las areas de mensajes correspondientes al turno.
    private void printTurnValues(){  
        optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione una accion. </font></html>");
        turnLabel.setText("Turno No."+turnsDriver.getTurn());
    }
    
    private void printMeasureDistanceMessages(int type){
        switch(type){
            case 0:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta inicial. </font></html>");
            break;
            case 1:
                optionsLabel.setText("<html> <font size =5 color=\""+color+"\">"+turnsDriver.getActualPlayer().getName()+"</font><font color=\"white\">: Selecione el planeta final. </font></html>");
            break;
        }
    
    }
    
    //Metodo encargado de establecer el color del nombre del jugador en turno.
    private void setPlayerNameColor(){
        if(turnsDriver.getActualPlayer().getColor() == Color.blue) color = "blue";
        else if(turnsDriver.getActualPlayer().getColor() == Color.yellow) color = "yellow";
        else if(turnsDriver.getActualPlayer().getColor() == Color.green) color = "lime";
        else if(turnsDriver.getActualPlayer().getColor() == Color.red) color = "red";
        else if(turnsDriver.getActualPlayer().getColor() == Color.orange) color = "orange";
        else if(turnsDriver.getActualPlayer().getColor() == Color.lightGray) color = "silver";
        else if(turnsDriver.getActualPlayer().getColor() == Color.cyan) color = "aqua";
        else if(turnsDriver.getActualPlayer().getColor() == Color.darkGray) color = "gray";
        else if(turnsDriver.getActualPlayer().getColor() == Color.white) color = "white";
        else if(turnsDriver.getActualPlayer().getColor() == Color.magenta) color = "fuchsia";
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        backGroundPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        optionsBar = new javax.swing.JToolBar();
        measureDistanceMenuItem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        endTurnMenuItem = new javax.swing.JButton();
        messagesPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        turnLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagesArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        actionsPane = new javax.swing.JPanel();
        optionsLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        spaceShipsAmount = new javax.swing.JTextField();
        cancelActionButton = new javax.swing.JButton();
        panel123 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        game = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        endGame = new javax.swing.JMenuItem();

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

        measureDistanceMenuItem.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        measureDistanceMenuItem.setForeground(new java.awt.Color(0, 51, 102));
        measureDistanceMenuItem.setText("Medir distancia");
        measureDistanceMenuItem.setActionCommand("");
        measureDistanceMenuItem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        measureDistanceMenuItem.setBorderPainted(false);
        measureDistanceMenuItem.setFocusPainted(false);
        measureDistanceMenuItem.setMaximumSize(new java.awt.Dimension(170, 20));
        measureDistanceMenuItem.setMinimumSize(new java.awt.Dimension(170, 20));
        measureDistanceMenuItem.setPreferredSize(new java.awt.Dimension(170, 20));
        measureDistanceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                measureDistanceMenuItemActionPerformed(evt);
            }
        });
        optionsBar.add(measureDistanceMenuItem);

        jButton2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("Enviar naves");
        jButton2.setActionCommand("");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton2.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 20));
        optionsBar.add(jButton2);

        jButton3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setText("Consultar flota");
        jButton3.setActionCommand("");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton3.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 20));
        optionsBar.add(jButton3);

        endTurnMenuItem.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        endTurnMenuItem.setForeground(new java.awt.Color(0, 51, 102));
        endTurnMenuItem.setText("Fin del turno");
        endTurnMenuItem.setActionCommand("");
        endTurnMenuItem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        endTurnMenuItem.setBorderPainted(false);
        endTurnMenuItem.setFocusPainted(false);
        endTurnMenuItem.setMaximumSize(new java.awt.Dimension(150, 20));
        endTurnMenuItem.setMinimumSize(new java.awt.Dimension(150, 20));
        endTurnMenuItem.setPreferredSize(new java.awt.Dimension(150, 20));
        endTurnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTurnMenuItemActionPerformed(evt);
            }
        });
        optionsBar.add(endTurnMenuItem);

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

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 100));

        spaceShipsAmount.setBackground(new java.awt.Color(0, 0, 0));
        spaceShipsAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        spaceShipsAmount.setMinimumSize(new java.awt.Dimension(150, 35));
        spaceShipsAmount.setOpaque(false);
        spaceShipsAmount.setPreferredSize(new java.awt.Dimension(150, 35));
        spaceShipsAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spaceShipsAmountKeyTyped(evt);
            }
        });
        jPanel5.add(spaceShipsAmount);

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
        jPanel5.add(cancelActionButton);

        jPanel4.add(jPanel5, java.awt.BorderLayout.LINE_END);

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

        game.setForeground(new java.awt.Color(102, 0, 255));
        game.setText("     Juego     ");
        game.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N

        newGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGame.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        newGame.setForeground(new java.awt.Color(102, 0, 255));
        newGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        newGame.setText("Nuevo");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        game.add(newGame);

        endGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        endGame.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        endGame.setForeground(new java.awt.Color(102, 0, 255));
        endGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detener.png"))); // NOI18N
        endGame.setText("Finalizar Partida");
        game.add(endGame);

        jMenuBar1.add(game);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Metodo encargado de abrir el JDialog gameCreator. Posteriomente obtiene los valores proporcionados por el
    JDialog y los almacena en las listas players, neutralPlanets y la instancia de la clase Map map solo si el 
    valor startGame del JDialog es true.. 
    */
    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        gameCreator = new GameSettings(this, true);
        gameCreator.setVisible(true);
        if(gameCreator.isStartGame()){
            neutralPlanets = gameCreator.getPlanets();
            players = gameCreator.getPlayers();
            map = gameCreator.getMap();
            startGame();
        }
    }//GEN-LAST:event_newGameActionPerformed
    //Metodo encargado de limitar los caracteres ingresados en un JTextArea a solamente numeros.
    private void spaceShipsAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spaceShipsAmountKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume(); 
    }//GEN-LAST:event_spaceShipsAmountKeyTyped
    
    //Metodo encargado de llamar al metodo printTurnValues().
    private void cancelActionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionButtonActionPerformed
        selectedSquareCounter = 0;
        gameActionsDriver.setMeasureDistance(false);
        gameActionsDriver.setSendSpaceShips(false);
        printTurnValues();
    }//GEN-LAST:event_cancelActionButtonActionPerformed
    /*
    Metodo encargado de llamar al metodo setMeasureDistance de la clase GameActions y le envia como 
    parametro el valor booleano true. Llama al metodo printMeasureDistanceMessages enviando como 
    parametro el valor entero 0 e incrementa en 1 el valor del contador selectedSquareCounter.
    */
    private void measureDistanceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_measureDistanceMenuItemActionPerformed
        gameActionsDriver.setMeasureDistance(true);
        printMeasureDistanceMessages(0);
        selectedSquareCounter++;
    }//GEN-LAST:event_measureDistanceMenuItemActionPerformed

    
    private void endTurnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTurnMenuItemActionPerformed
        selectedSquareCounter = 0;
        gameActionsDriver.setMeasureDistance(false);
        gameActionsDriver.setSendSpaceShips(false);
        verifyCompletion();
    }//GEN-LAST:event_endTurnMenuItemActionPerformed

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
    private javax.swing.JMenuItem endGame;
    private javax.swing.JButton endTurnMenuItem;
    private javax.swing.JMenu game;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton measureDistanceMenuItem;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JTextArea messagesArea;
    private javax.swing.JPanel messagesPane;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JToolBar optionsBar;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JPanel panel123;
    private javax.swing.JTextField spaceShipsAmount;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}
