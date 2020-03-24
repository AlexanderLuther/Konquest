package com.hluther.gui;

import com.hluther.drivers.Analisys;
import com.hluther.drivers.Files;
import com.hluther.drivers.MapConfigFile;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author helmuth
 */
public class Konquest extends javax.swing.JFrame {
    
    private Analisys analisisDriver = new Analisys();
    private MapConfigFile mapConfigFileDriver = new MapConfigFile();
    private Files filesDriver = new Files();
    private List<Planet> neutralPlanets = new ArrayList<Planet>();
    private List<Player> players = new ArrayList<Player>();
    private Map map = new Map();        
    private GameSettings gameCreator;
    private BackGroundImage backGroundImage;
    private Board board;
    
    public Konquest() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        messagesPane.setVisible(false);
        actionsPane.setVisible(false);
        spaceShipsAmount.setEditable(false);
        try {
            backGroundImage = new BackGroundImage(ImageIO.read(this.getClass().getResource("/konquestBackGround.jpg")));     
            this.backGroundPanel.setBorder(backGroundImage);
        } 
        catch (IOException ex) {
            System.out.println("Imagen no encontrada");
        }
    }
    
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
    
    
  
    public void startGame(){
        if(board != null){
            boardPanel.remove(board);
            boardPanel.revalidate();
        }
        //Hacer visibles las areas de utilidades
        messagesPane.setVisible(true);
        actionsPane.setVisible(true);
        board = new Board(map.getRows(), map.getColumns(), this);
        boardPanel.add(board);
        this.repaint();
    }
    
 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        backGroundPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
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
        endTurnButton = new javax.swing.JButton();
        panel123 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        game = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        move = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

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

        jToolBar2.setRollover(true);

        jButton1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("Nuevo");
        jButton1.setActionCommand("");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(170, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(170, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(170, 20));
        jToolBar2.add(jButton1);

        jButton2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("Finalizar partida");
        jButton2.setActionCommand("");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton2.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 20));
        jToolBar2.add(jButton2);

        jButton3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setText("Fin del turno");
        jButton3.setActionCommand("");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton3.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 20));
        jToolBar2.add(jButton3);

        jButton4.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setText("Medir distancia");
        jButton4.setActionCommand("");
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton4.setBorderPainted(false);
        jButton4.setFocusPainted(false);
        jButton4.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton4.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton4.setPreferredSize(new java.awt.Dimension(150, 20));
        jToolBar2.add(jButton4);

        jButton5.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setText("Mostrar posiciones");
        jButton5.setActionCommand("");
        jButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton5.setBorderPainted(false);
        jButton5.setFocusPainted(false);
        jButton5.setMaximumSize(new java.awt.Dimension(150, 20));
        jButton5.setMinimumSize(new java.awt.Dimension(150, 20));
        jButton5.setPreferredSize(new java.awt.Dimension(150, 20));
        jToolBar2.add(jButton5);

        jButton6.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 51, 102));
        jButton6.setText("Vista general de la flota");
        jButton6.setActionCommand("");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setBorderPainted(false);
        jButton6.setFocusPainted(false);
        jButton6.setMaximumSize(new java.awt.Dimension(190, 20));
        jButton6.setMinimumSize(new java.awt.Dimension(190, 20));
        jButton6.setPreferredSize(new java.awt.Dimension(190, 20));
        jToolBar2.add(jButton6);

        menuPanel.add(jToolBar2);

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

        optionsLabel.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
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

        endTurnButton.setBackground(new java.awt.Color(0, 0, 0));
        endTurnButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        endTurnButton.setForeground(new java.awt.Color(0, 153, 0));
        endTurnButton.setText("Terminar Turno");
        endTurnButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        endTurnButton.setBorderPainted(false);
        endTurnButton.setMinimumSize(new java.awt.Dimension(135, 35));
        endTurnButton.setOpaque(true);
        endTurnButton.setPreferredSize(new java.awt.Dimension(135, 35));
        jPanel5.add(endTurnButton);

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

        jMenuBar1.add(game);

        move.setForeground(new java.awt.Color(102, 0, 255));
        move.setText("     Mover     ");
        move.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(102, 0, 255));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adelante.png"))); // NOI18N
        jMenuItem1.setText("Fin del turno");
        move.add(jMenuItem1);

        jMenuBar1.add(move);

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

    private void spaceShipsAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spaceShipsAmountKeyTyped

        char c = evt.getKeyChar();
        if(c < '0' || c > '9'){ evt.consume();}        // TODO add your handling code here:
    }//GEN-LAST:event_spaceShipsAmountKeyTyped

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
    private javax.swing.JButton endTurnButton;
    private javax.swing.JMenu game;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JTextArea messagesArea;
    private javax.swing.JPanel messagesPane;
    private javax.swing.JMenu move;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JPanel panel123;
    private javax.swing.JTextField spaceShipsAmount;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}
