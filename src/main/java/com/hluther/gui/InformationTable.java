package com.hluther.gui;

import com.hluther.entityclasses.FleetDTO;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
/**
 *
 * @author helmuth
 */
public class InformationTable extends javax.swing.JDialog {
    
    private List<FleetDTO> fleet;
    private ObservableList<FleetDTO> observableList;
    private Konquest konquets;
    
    public InformationTable(Konquest parent, boolean modal) {
        super(parent, modal);
        this.konquets = parent;
        this.fleet = new ArrayList<>();
        this.observableList = ObservableCollections.observableList(fleet);
        initComponents();
        fleetsTable.setShowVerticalLines(true);
        this.setLocationRelativeTo(parent);
    }
    
    public ObservableList<FleetDTO> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<FleetDTO> observableList) {
        this.observableList = observableList;
    }
    
    public void updateObservableList(List<FleetDTO> prestamoListObservable) {
        this.observableList.clear();
        this.observableList.addAll(prestamoListObservable);
    }
    
    
    //Metodo encargado de remover todos los elementos dentro de la lista observable.
    public void clearObservableList(){
        observableList.clear();
    }
    
    //Metodo encargado de inicializar la lista observable.
    public void fillTable(){
        fleet = konquets.getFleets();
        updateObservableList(fleet); 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fleetsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informacion de la flota");
        setMinimumSize(new java.awt.Dimension(950, 450));

        jPanel1.setMinimumSize(new java.awt.Dimension(950, 40));
        jPanel1.setPreferredSize(new java.awt.Dimension(950, 40));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        closeButton.setText("Cerrar");
        closeButton.setMaximumSize(new java.awt.Dimension(120, 25));
        closeButton.setMinimumSize(new java.awt.Dimension(120, 25));
        closeButton.setPreferredSize(new java.awt.Dimension(120, 25));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jPanel1.add(closeButton, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        fleetsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 255)));
        fleetsTable.setFont(new java.awt.Font("Serif", 1, 13)); // NOI18N
        fleetsTable.setForeground(new java.awt.Color(102, 0, 255));

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${observableList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, fleetsTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fleetNumber}"));
        columnBinding.setColumnName("No. de flota");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${initialPlanetName}"));
        columnBinding.setColumnName("Planeta inicial");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${targetPlanetName}"));
        columnBinding.setColumnName("Planeta objetivo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${spaceShipsAmount}"));
        columnBinding.setColumnName("Naves");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${initialPlanetDeathRate}"));
        columnBinding.setColumnName("Porcentaje de Muertes");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${arrivalTurn}"));
        columnBinding.setColumnName("Turno de llegada");
        columnBinding.setColumnClass(Long.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(fleetsTable);

        jPanel2.add(jScrollPane1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Metodo encargado de cerra el JDialog
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(InformationTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformationTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformationTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformationTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InformationTable dialog = new InformationTable(new Konquest(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTable fleetsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
