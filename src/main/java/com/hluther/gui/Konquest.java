package com.hluther.gui;

import com.hluther.drivers.AnalisysDriver;
import com.hluther.drivers.FilesDriver;
import com.hluther.drivers.MapConfigFileDriver;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author helmuth
 */
public class Konquest extends javax.swing.JFrame {
    
    private AnalisysDriver analisisDriver = new AnalisysDriver();
    private MapConfigFileDriver mapConfigFileDriver = new MapConfigFileDriver();
    private FilesDriver filesDriver = new FilesDriver();
    
    private GameSettings gameCreator;
    private BackGroundImage backGroundImage;
    /**
     * Creates new form Konquest
     */
    public Konquest() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        try {
            backGroundImage = new BackGroundImage(ImageIO.read(this.getClass().getResource("/konquestBackGround.jpg")));     
            this.backGroundPanel.setBorder(backGroundImage);
        } 
        catch (IOException ex) {
            System.out.println("Imagen no encontrada");
        }
    }
 
    
    public MapConfigFileDriver getMapConfigFileDriver() {
        return mapConfigFileDriver;
    }

    public AnalisysDriver getAnalisisDriver() {
        return analisisDriver;
    }

    public FilesDriver getFilesDriver() {
        return filesDriver;
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        gameCreator = new GameSettings(this, true);
        gameCreator.setVisible(true);
    }//GEN-LAST:event_newGameActionPerformed

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
    private javax.swing.JPanel backGroundPanel;
    private javax.swing.JMenu game;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JMenu move;
    private javax.swing.JMenuItem newGame;
    // End of variables declaration//GEN-END:variables
}
