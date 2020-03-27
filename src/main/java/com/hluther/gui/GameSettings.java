package com.hluther.gui;

import com.hluther.drivers.MapSettings;
import com.hluther.drivers.PlanetsSettings;
import com.hluther.drivers.PlayersSettings;
import com.hluther.entityclasses.Map;
import com.hluther.entityclasses.Planet;
import com.hluther.entityclasses.Player;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
/**
 *
 * @author helmuth
 */
public class GameSettings extends javax.swing.JDialog {

    private List<Player> players = new ArrayList<>();
    private List<Planet> planets = new ArrayList<Planet>();
    private List<String> messages = new ArrayList<String>();
    private PlanetsSettings planetsSettings = new PlanetsSettings();
    private PlayersSettings playersSettings = new PlayersSettings();
    private MapSettings mapSettings = new MapSettings();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON","json");
    private DefaultTableModel playersTableModel = new DefaultTableModel();
    private DefaultTableModel planetsTableModel = new DefaultTableModel();
    private static String[] items = {"HUMANO","FACIL","DIFICIL"};
    private JComboBox<String> playersComboBox = new JComboBox<>(items);
    private JLabel playerColor = new JLabel();
    private JComboBox<String> planetsComboBox;
    private Konquest konquestFrame;
    private Map map = new Map();
    private JFileChooser fileChooser;
    private boolean cancelled = false;
    private int playersCounter = 1;
    private int planetsCounter = 1;
    private boolean startGame = false;
        
    public GameSettings(Konquest konquetsFrame, boolean modal) {
        super(konquetsFrame, modal);
        konquestFrame = konquetsFrame;
        initComponents();
        initializePlayersTable();
        initializePlanetsTable();
        this.setLocationRelativeTo(konquetsFrame);
        completionSpinner.setEnabled(false);
        neutralPlanetsSpinner.setEnabled(false);
        playerColor.setOpaque(true);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public Map getMap() {
        return map;
    }
    
    public boolean isStartGame(){
        return startGame;
    }
    
    //Metodo encargado de inicializar el area de jugadores
    public void initializePlayersArea(){
        clearPlayersTable();
        fillPlayersTable();
    }
    
    //Metodo encargado de inicializar el area de planetas
    public void initializePlanetsArea(boolean setOwners){
        initPlanetsComboBox();
        clearPlanetsTable();
        fillPlanetsTable(setOwners);
    }
       
    //Metodo encargado de inicializar el area de mapa
    public void initializeMapArea(){
        idTextField.setText(map.getName());          
        if(map.getRows() != -1) rowsSpinner.setValue(map.getRows());
        if(map.getColumns() != -1) columnsSpinner.setValue(map.getColumns());
        if(map.isBlindMap()) blindMapCheckBox.setSelected(true);
        if(map.isAccumulate()) accumulateCheckBox.setSelected(true);
        if(map.isShowSpaceShips()) showSpaceShipsCheckBox.setSelected(true);
        if(map.isShowStatistics()) showStatisticsCheckBox.setSelected(true);
        if(map.getProduction() != -1) productionSpinner.setValue(map.getProduction());
        if(map.getCompletion() != -1){
            completionCheckBox.setSelected(true);
            completionSpinner.setValue(map.getCompletion());
        }
        if(map.isRandom()){
            randomCheckBox.setSelected(true);
            if(map.getNeutralPlanets() != -1){
                neutralPlanetsSpinner.setValue(map.getNeutralPlanets());
            }
        }
    }
    
    /*
    Metodo encargado de establecer los valores de los parametros que recibe.
    */
    public void getLists(){
        map = konquestFrame.getMapConfigFileDriver().getMap();
        players = konquestFrame.getMapConfigFileDriver().getPlayers();
        planets = planetsSettings.setInitialProduction(konquestFrame.getMapConfigFileDriver().getPlanets(), map.getProduction());
        messages = konquestFrame.getMapConfigFileDriver().getMessages();
    }
    
//----------------------------------------Tabla de Jugadores----------------------------------------//   
    //Metodo encargado de establecer las cabeceras y el modelo de la table de jugadores.
    private void initializePlayersTable(){
        playersTable.setDefaultRenderer(Object.class, new TableRenderer());
        playersTableModel.setColumnIdentifiers(new String[]{"Color", "Nombre", "Tipo"});    
        playersTable.setModel(playersTableModel);
    }
    
    //Metodo encargado de llenar la tabla de jugadores.
    public void fillPlayersTable(){    
        for(int i = 0; i < players.size(); i++){  
            playersTableModel.addRow(new Object[]{playerColor, players.get(i).getName(), players.get(i).getType()});
            setComboBox(playersTable.getColumnModel().getColumn(2));
        }
    }
    
    //Metodo encargado de insertar un JComboBox en la segunda columna de cada fila de la tabla de jugadores.
    private void setComboBox(TableColumn column){
        column.setCellEditor(new  DefaultCellEditor(playersComboBox));
    }
    
    //Metodo encargado de limpiar todos los datos de la tabla de jugadores
    public void clearPlayersTable(){
        if(playersTableModel.getRowCount() >= 0){
            for (int i = 0; i < playersTableModel.getRowCount(); i++) {
                playersTableModel.removeRow(i);
                i--;
            }
        }
    }
    
    //Metodo encargado de agregar un jugador.
    private void addPlayer(String type){
        playersCounter = playersSettings.addPlayer(players, type, playersCounter);
        initializePlayersArea();
        initPlanetsComboBox();
        initializePlanetsArea(false);
    }
   
//----------------------------------------Tabla de Planetas----------------------------------------//   
    //Metodo encargado de establecer las cabeceras y el modelo de la table de jugadores.
    private void initializePlanetsTable(){
        planetsTableModel.setColumnIdentifiers(new String[]{"Nombre", "Naves", "Propietario", "Produccion", "Porcentaje de Muertes"}); 
        planetsTable.setModel(planetsTableModel);
    }
    
    //Metodo encargado de llenar la tabla de jugadores.
    public void fillPlanetsTable(boolean setOwners){  
        for(int i = 0; i < planets.size(); i++){  
            if(setOwners){
                planetsSettings.setInitialOwner(planets.get(i), players);
            }
            planetsTableModel.addRow(new Object[]{planets.get(i).getName(), planets.get(i).getSpaceShips(), planets.get(i).getOwner() , planets.get(i).getProduction(), planets.get(i).getDeathRate()});
            setComboBoxPlanets(planetsTable.getColumnModel().getColumn(2));
        }
    }
    
    //Metodo encargado de insertar un JComboBox en la segunda columna de cada fila de la tabla de jugadores.
    private void setComboBoxPlanets(TableColumn column){
        column.setCellEditor(new  DefaultCellEditor(planetsComboBox));
    }
    
    //Metodo encargado de limpiar todos los datos de la tabla de planetas
    public void clearPlanetsTable(){
        if(planetsTableModel.getRowCount() >= 0){
            for (int i = 0; i < planetsTableModel.getRowCount(); i++) {
                planetsTableModel.removeRow(i);
                i--;
            }
        }
    }
    
    /*
    Metodo encargado de crear un nuevo JComboBox y lo inicializa con cada uno de los nombres de los jugadores
    contenidos en la lista players, ademas de un item con valor "Neutral".
    */
    public void initPlanetsComboBox(){
        String[] items = new String[players.size() + 1];
        for(int i = 0; i < players.size(); i++){
            items[i+1] = players.get(i).getName();
        }
        items[0] = "Neutral";
        planetsComboBox  = new JComboBox<>(items);
    }
       
//----------------------------------------Area de Errores----------------------------------------//
    //Metodo encargado de insertar en el textArea messagesArea todos los elementos contenidos dentro de la lista messages.
    private void showMessages(){
        for(int i = 0; i < messages.size(); i++){
            messagesArea.setText(messagesArea.getText() + messages.get(i) + "\n");
        }
    }
    
//----------------------------------------Configuracion FileChooser----------------------------------------//
    //Metodo encargado de configurar el FileChooser
    public void configureFileChooser(JFileChooser fileChooser){
        fileChooser.revalidate();
        fileChooser.setApproveButtonText("Abrir");
        fileChooser.setDialogTitle("Abrir Archivo");
        fileChooser.setFileFilter(filter);
    }
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        messagesArea = new javax.swing.JTextArea();
        gameSettingsPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        addButton = new javax.swing.JComboBox<>();
        deletePlayer = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        playersTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        addPlanet = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        deletePlanet = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        planetsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        showStatisticsCheckBox = new javax.swing.JCheckBox();
        showSpaceShipsCheckBox = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        productionSpinner = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        randomCheckBox = new javax.swing.JCheckBox();
        jPanel25 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        neutralPlanetsSpinner = new javax.swing.JSpinner();
        jPanel13 = new javax.swing.JPanel();
        blindMapCheckBox = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        accumulateCheckBox = new javax.swing.JCheckBox();
        jPanel21 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        completionCheckBox = new javax.swing.JCheckBox();
        jPanel23 = new javax.swing.JPanel();
        columnsSpinner = new javax.swing.JSpinner();
        completionSpinner = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        idTextField = new javax.swing.JTextField();
        rowsSpinner = new javax.swing.JSpinner();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        importFile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        playButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Juego");
        setMinimumSize(new java.awt.Dimension(900, 600));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(900, 600));

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));

        messagesArea.setEditable(false);
        messagesArea.setBackground(new java.awt.Color(0, 0, 0));
        messagesArea.setColumns(20);
        messagesArea.setFont(new java.awt.Font("Serif", 1, 13)); // NOI18N
        messagesArea.setForeground(new java.awt.Color(204, 0, 0));
        messagesArea.setRows(5);
        messagesArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(messagesArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        gameSettingsPanel.setLayout(new javax.swing.BoxLayout(gameSettingsPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(174, 50));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        addButton.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        addButton.setForeground(new java.awt.Color(0, 51, 102));
        addButton.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agregar" }));
        addButton.setMinimumSize(new java.awt.Dimension(100, 25));
        addButton.setName("Agregar"); // NOI18N
        addButton.setPreferredSize(new java.awt.Dimension(100, 25));
        addButton.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                addButtonPopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                addButtonPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                addButtonPopupMenuWillBecomeVisible(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 33, 13, 0);
        jPanel4.add(addButton, gridBagConstraints);
        addButton.getAccessibleContext().setAccessibleName("Agregar");

        deletePlayer.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        deletePlayer.setForeground(new java.awt.Color(0, 51, 102));
        deletePlayer.setText("Eliminar");
        deletePlayer.setMinimumSize(new java.awt.Dimension(100, 25));
        deletePlayer.setPreferredSize(new java.awt.Dimension(100, 25));
        deletePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePlayerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 13, 40);
        jPanel4.add(deletePlayer, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel7.setPreferredSize(new java.awt.Dimension(280, 354));
        jPanel7.setLayout(new java.awt.BorderLayout());

        playersTable.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        playersTable.setForeground(new java.awt.Color(102, 0, 255));
        playersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        playersTable.setGridColor(new java.awt.Color(0, 0, 0));
        playersTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                playersTablePropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(playersTable);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Jugadores");
        jLabel1.setMaximumSize(new java.awt.Dimension(44, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(44, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(44, 30));
        jPanel7.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);

        gameSettingsPanel.add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        addPlanet.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        addPlanet.setForeground(new java.awt.Color(0, 51, 102));
        addPlanet.setText("Agregar");
        addPlanet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPlanetActionPerformed(evt);
            }
        });
        jPanel5.add(addPlanet, new java.awt.GridBagConstraints());

        jLabel17.setText("      ");
        jPanel5.add(jLabel17, new java.awt.GridBagConstraints());

        deletePlanet.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        deletePlanet.setForeground(new java.awt.Color(0, 51, 102));
        deletePlanet.setText("Eliminar");
        deletePlanet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePlanetActionPerformed(evt);
            }
        });
        jPanel5.add(deletePlanet, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 0, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Planetas");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel15.setPreferredSize(new java.awt.Dimension(52, 30));
        jPanel8.add(jLabel15, java.awt.BorderLayout.PAGE_START);

        planetsTable.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        planetsTable.setForeground(new java.awt.Color(102, 0, 255));
        planetsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        planetsTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                planetsTablePropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(planetsTable);

        jPanel8.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        gameSettingsPanel.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(225, 130));
        jPanel6.setRequestFocusEnabled(false);
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Planetas Neutrales");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        jPanel14.setPreferredSize(new java.awt.Dimension(227, 50));
        jPanel14.setLayout(new java.awt.BorderLayout());

        showStatisticsCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        showStatisticsCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        showStatisticsCheckBox.setText("Mostrar Estadisticas");
        jPanel14.add(showStatisticsCheckBox, java.awt.BorderLayout.CENTER);

        showSpaceShipsCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        showSpaceShipsCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        showSpaceShipsCheckBox.setText("Mostrar Naves");
        jPanel14.add(showSpaceShipsCheckBox, java.awt.BorderLayout.PAGE_START);

        jPanel15.add(jPanel14);

        jPanel16.setPreferredSize(new java.awt.Dimension(227, 30));
        jPanel16.setLayout(new java.awt.BorderLayout());

        productionSpinner.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        productionSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        productionSpinner.setEditor(new javax.swing.JSpinner.NumberEditor(productionSpinner, "##"));
        productionSpinner.setMaximumSize(new java.awt.Dimension(60, 25));
        productionSpinner.setMinimumSize(new java.awt.Dimension(60, 25));
        jPanel16.add(productionSpinner, java.awt.BorderLayout.CENTER);

        jLabel10.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Produccion: ");
        jPanel16.add(jLabel10, java.awt.BorderLayout.WEST);

        jPanel15.add(jPanel16);

        jPanel6.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Mapa");
        jLabel8.setMaximumSize(new java.awt.Dimension(38, 30));
        jLabel8.setMinimumSize(new java.awt.Dimension(38, 30));
        jLabel8.setPreferredSize(new java.awt.Dimension(38, 30));
        jPanel9.add(jLabel8, java.awt.BorderLayout.PAGE_START);

        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel20.setPreferredSize(new java.awt.Dimension(230, 150));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jPanel12.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel12.setMinimumSize(new java.awt.Dimension(227, 60));
        jPanel12.setPreferredSize(new java.awt.Dimension(227, 50));

        jPanel24.setMinimumSize(new java.awt.Dimension(70, 33));
        jPanel24.setPreferredSize(new java.awt.Dimension(200, 25));

        randomCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        randomCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        randomCheckBox.setText("Al Azar");
        randomCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        randomCheckBox.setMinimumSize(new java.awt.Dimension(50, 23));
        randomCheckBox.setPreferredSize(new java.awt.Dimension(120, 23));
        randomCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                randomCheckBoxItemStateChanged(evt);
            }
        });
        jPanel24.add(randomCheckBox);

        jPanel12.add(jPanel24);

        jPanel25.setPreferredSize(new java.awt.Dimension(230, 25));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel14.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setText(" Planetas Neutrales: ");
        jLabel14.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel25.add(jLabel14, java.awt.BorderLayout.LINE_START);

        neutralPlanetsSpinner.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        neutralPlanetsSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel25.add(neutralPlanetsSpinner, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel25);

        jPanel20.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel13.setMinimumSize(new java.awt.Dimension(227, 60));
        jPanel13.setPreferredSize(new java.awt.Dimension(227, 40));
        jPanel13.setLayout(new java.awt.BorderLayout());

        blindMapCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        blindMapCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        blindMapCheckBox.setText("Mapa Ciego");
        blindMapCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        blindMapCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        blindMapCheckBox.setPreferredSize(new java.awt.Dimension(110, 23));
        jPanel13.add(blindMapCheckBox, java.awt.BorderLayout.LINE_START);

        jLabel16.setText("     ");
        jPanel13.add(jLabel16, java.awt.BorderLayout.CENTER);

        accumulateCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        accumulateCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        accumulateCheckBox.setText("Acumular");
        accumulateCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accumulateCheckBox.setPreferredSize(new java.awt.Dimension(50, 23));
        jPanel13.add(accumulateCheckBox, java.awt.BorderLayout.CENTER);

        jPanel20.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        jPanel19.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel21.setMinimumSize(new java.awt.Dimension(226, 120));
        jPanel21.setPreferredSize(new java.awt.Dimension(226, 150));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel10.setMinimumSize(new java.awt.Dimension(227, 60));
        jPanel10.setPreferredSize(new java.awt.Dimension(227, 80));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 102));
        jLabel13.setText("Columnas:");
        jLabel13.setPreferredSize(new java.awt.Dimension(50, 18));
        jPanel22.add(jLabel13, java.awt.BorderLayout.CENTER);

        completionCheckBox.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        completionCheckBox.setForeground(new java.awt.Color(0, 51, 102));
        completionCheckBox.setText("Finalizacion");
        completionCheckBox.setMinimumSize(new java.awt.Dimension(100, 30));
        completionCheckBox.setPreferredSize(new java.awt.Dimension(100, 30));
        completionCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                completionCheckBoxItemStateChanged(evt);
            }
        });
        jPanel22.add(completionCheckBox, java.awt.BorderLayout.PAGE_END);

        jPanel10.add(jPanel22, java.awt.BorderLayout.LINE_START);

        columnsSpinner.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        columnsSpinner.setModel(new javax.swing.SpinnerNumberModel(4, 4, 25, 1));

        completionSpinner.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        completionSpinner.setModel(new javax.swing.SpinnerNumberModel(2, 2, null, 1));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(columnsSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(completionSpinner, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(columnsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(completionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setMinimumSize(new java.awt.Dimension(227, 80));
        jPanel11.setPreferredSize(new java.awt.Dimension(227, 80));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText(" Filas:");
        jLabel11.setPreferredSize(new java.awt.Dimension(33, 25));
        jPanel17.add(jLabel11, java.awt.BorderLayout.CENTER);

        jLabel12.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText(" Nombre:");
        jLabel12.setPreferredSize(new java.awt.Dimension(59, 30));
        jPanel17.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        jPanel11.add(jPanel17, java.awt.BorderLayout.LINE_START);

        idTextField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        idTextField.setForeground(new java.awt.Color(102, 0, 255));

        rowsSpinner.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        rowsSpinner.setModel(new javax.swing.SpinnerNumberModel(4, 4, 25, 1));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(rowsSpinner))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rowsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel19.add(jPanel21, java.awt.BorderLayout.PAGE_START);

        jPanel9.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel9, java.awt.BorderLayout.CENTER);

        gameSettingsPanel.add(jPanel3);

        getContentPane().add(gameSettingsPanel, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(100, 35));
        jToolBar1.setMinimumSize(new java.awt.Dimension(100, 35));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 35));

        jLabel4.setText("               ");
        jToolBar1.add(jLabel4);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/json.png"))); // NOI18N
        jToolBar1.add(jLabel5);

        importFile.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        importFile.setForeground(new java.awt.Color(0, 51, 102));
        importFile.setText(" Importar");
        importFile.setBorderPainted(false);
        importFile.setFocusPainted(false);
        importFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        importFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importFileActionPerformed(evt);
            }
        });
        jToolBar1.add(importFile);

        jLabel2.setText("               ");
        jToolBar1.add(jLabel2);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detener.png"))); // NOI18N
        jToolBar1.add(jLabel6);

        closeButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 51, 102));
        closeButton.setText("Cancelar");
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        closeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(closeButton);

        jLabel3.setText("               ");
        jToolBar1.add(jLabel3);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/galaxia.png"))); // NOI18N
        jToolBar1.add(jLabel7);

        playButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        playButton.setForeground(new java.awt.Color(0, 51, 102));
        playButton.setText("  Jugar  ");
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(playButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Metodo encargado de agregar eliminar y agregar items al comboBox addButton. Establece en false el valor booleano de cancelled.
    private void addButtonPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_addButtonPopupMenuWillBecomeVisible
        cancelled = false;
        addButton.removeAllItems();
        addButton.addItem("Humano");
        addButton.addItem("Facil");
        addButton.addItem("Dificil");
    }//GEN-LAST:event_addButtonPopupMenuWillBecomeVisible

    /*
    Metodo encargado agregar un nuevo jugador.
    Si la variable cancelled tiene un valor false se agrega un nuevo jugador en base al item seleccionado
    en el JComboBox addButton. Por ultimo se remueven todos los items de addButton y se agrega uno nuevo.
    */
    private void addButtonPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_addButtonPopupMenuWillBecomeInvisible
        if(!cancelled){
            addPlayer(addButton.getSelectedItem().toString());
        }
        addButton.removeAllItems();
        addButton.addItem("Agregar");
    }//GEN-LAST:event_addButtonPopupMenuWillBecomeInvisible

    
    private void importFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importFileActionPerformed
         try{

                fileChooser = new JFileChooser();
                configureFileChooser(fileChooser);
                fileChooser.showOpenDialog(this);      
                String data = konquestFrame.getFilesDriver().openFile(fileChooser.getSelectedFile().toString());
                konquestFrame.getMapConfigFileDriver().clearLists();
                konquestFrame.getAnalisisDriver().doMapConfigFileAnalysis(data, konquestFrame);
                
                getLists();
                initializePlayersArea();
                initializePlanetsArea(true);
                initializeMapArea();
                showMessages();
                 
            }
        catch(NullPointerException e){
        }
    }//GEN-LAST:event_importFileActionPerformed
    
    //Metodo encargado de eliminar un jugador.
    private void deletePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePlayerActionPerformed
        if(playersTable.getSelectedRow() == -1){
            if (!(playersTable.getRowCount() <= 0)){
                planetsSettings.removeDeletedOwner(planets, players.get(players.size() - 1).getName());
            } 
        }
        else{
            planetsSettings.removeDeletedOwner(planets, players.get(playersTable.getSelectedRow()).getName());
        }
        playersSettings.deletePlayer(playersTable, players);
        initializePlayersArea();
        initPlanetsComboBox();
        initializePlanetsArea(false);
    }//GEN-LAST:event_deletePlayerActionPerformed

    //Metodo encargado de establecer el valor booleano de cancelled en true.
    private void addButtonPopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_addButtonPopupMenuCanceled
        cancelled = true;
    }//GEN-LAST:event_addButtonPopupMenuCanceled

    //Metodo encargado de actualizar los datos de un jugador
    private void playersTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_playersTablePropertyChange
        if (playersTable.getSelectedRow() != -1){
            playersSettings.updatePlayer(playersTableModel, playersTable, players);
            initializePlayersArea();
            initializePlanetsArea(true);
        }
    }//GEN-LAST:event_playersTablePropertyChange

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    //Metodo encargado de habilitar y deshabilitar el spinner completionSpinner en base al estado del checkBox completionCheckBox.
    private void completionCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_completionCheckBoxItemStateChanged
        if(completionCheckBox.isSelected()){
            completionSpinner.setEnabled(true);
        }
        else{
            completionSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_completionCheckBoxItemStateChanged

    //Metodo encargado de habilitar y deshabilitar el spinner neutralPlanetsSpinner en base al estado del checkBox randomCheckBox.
    private void randomCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_randomCheckBoxItemStateChanged
       if(randomCheckBox.isSelected()){
           neutralPlanetsSpinner.setEnabled(true);
       }
       else{
           neutralPlanetsSpinner.setEnabled(false);
       }
    }//GEN-LAST:event_randomCheckBoxItemStateChanged

    /*
    Metodo encargado de realizar las validaciones necesarias para poder comenzar con el juego.
        1. Valida que el nombre del mapa no se encuentre vacio y sea valido. Si no es valido lanza un mensaje
           indicando el error, caso contrario actualiza todos los datos del mapa y procede a la siguiente validacion.
        2. Valida que la cantidad de panetas no sea mayor a la cantidad de espacios disponibles. Si la validacion
           se realiza exitosamente procede con la siguiente validacion, caso contrario lanza un mensaje indicando
           el error.
        3. Valida que existan 2 jugadores como minimo para empezar el juego. Si no es asi lanza un mensaje indicando
           el error.
        4. Valida que todos los jugadores tengan asignado por lo menos un planeta. Si la validacion se realiza exitosamente
           se limpian todos los registros creados durante la validacion y se procede a asignar a los jugadores todos los 
           planetas de los que es propietario. Por ultimo se cierra la ventana actual. Caso contrario se lanza un mensaje 
           indicando el error.
    */
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        //------------------------  1   ------------------------   
        if(idTextField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Nombre del mapa no valido", "Error", 0);
        }
        else{
            map = mapSettings.updateMap(map, idTextField, rowsSpinner, columnsSpinner, completionSpinner, neutralPlanetsSpinner, productionSpinner, completionCheckBox, accumulateCheckBox, blindMapCheckBox, randomCheckBox, showSpaceShipsCheckBox, showStatisticsCheckBox);
            //------------------------  2   ------------------------   
            if(mapSettings.validateIntegrity(map.getRows(), map.getColumns(), planets.size())){
                //------------------------  3   ------------------------   
                if(players.size() < 2){
                    JOptionPane.showMessageDialog(this, "Se requieren al menos 2 jugadores para iniciar una partida", "Error", 0);
                }
                else{
                    //------------------------  4   ------------------------   
                    if(!playersSettings.verifyPlanets(planets, players)){  
                        for(int i = 0; i < players.size(); i++){
                            players.get(i).getPlanets().clear();
                        }
                        playersSettings.assignPlanets(planets, players);
                        playersSettings.assignColors(players);
                        startGame = true;
                        this.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Cada jugador debe de tener por lo menos 1 planeta propio.", "Error", 0);
                    }   
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "La cantidad de planetas supera la cantidad de cuadros disponibles.", "Error", 0);
            }
        }        
    }//GEN-LAST:event_playButtonActionPerformed

    //Metodo encargado de agregar un planeta.
    private void addPlanetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPlanetActionPerformed
        planetsCounter = planetsSettings.addPlanet(planets, planetsCounter);
        initializePlanetsArea(false);
    }//GEN-LAST:event_addPlanetActionPerformed

    //Metodo encargado de eliminar un planeta
    private void deletePlanetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePlanetActionPerformed
        planetsSettings.deletePlanet(planetsTable, planets);
        initializePlanetsArea(false);
    }//GEN-LAST:event_deletePlanetActionPerformed

    //Metodo encargado de modificar un planeta.
    private void planetsTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_planetsTablePropertyChange
        if (planetsTable.getSelectedRow() != -1){
            planetsSettings.updatePlanet(planetsTableModel, planetsTable, planets);
            initializePlanetsArea(false);
        }
    }//GEN-LAST:event_planetsTablePropertyChange

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
            java.util.logging.Logger.getLogger(GameSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameSettings dialog = new GameSettings(new Konquest(), true);
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
    private javax.swing.JCheckBox accumulateCheckBox;
    private javax.swing.JComboBox<String> addButton;
    private javax.swing.JButton addPlanet;
    private javax.swing.JCheckBox blindMapCheckBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JSpinner columnsSpinner;
    private javax.swing.JCheckBox completionCheckBox;
    private javax.swing.JSpinner completionSpinner;
    private javax.swing.JButton deletePlanet;
    private javax.swing.JButton deletePlayer;
    private javax.swing.JPanel gameSettingsPanel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JButton importFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea messagesArea;
    private javax.swing.JSpinner neutralPlanetsSpinner;
    private javax.swing.JTable planetsTable;
    private javax.swing.JButton playButton;
    private javax.swing.JTable playersTable;
    private javax.swing.JSpinner productionSpinner;
    private javax.swing.JCheckBox randomCheckBox;
    private javax.swing.JSpinner rowsSpinner;
    private javax.swing.JCheckBox showSpaceShipsCheckBox;
    private javax.swing.JCheckBox showStatisticsCheckBox;
    // End of variables declaration//GEN-END:variables
}
