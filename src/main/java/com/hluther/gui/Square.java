package com.hluther.gui;

import com.hluther.entityclasses.Planet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 * @author helmuth
 */
public class Square extends javax.swing.JPanel implements MouseListener{
    
    private int coordinateX;
    private int coordinateY;
    private Planet planet;
    private Color color;
    private Board board;
    private ImageIcon image;
    private JLabel planetNameLabel = new JLabel();
    private JLabel spaceShipsAmountLabel = new JLabel();
  
    public Square(int x, int y, Board board) {
        initComponents();        
        this.coordinateX = x;
        this.coordinateY = y;
        this.board = board;
    }
     
//------------------------------------------------- Getters y Setters de la clase -------------------------------------------------//
    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.setBackground(color);
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
    
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
//---------------------------------------------------------------------------------------------------------------------------------//
        
    /*
    Metodo encargado de inicializar la casilla.
        1. Estable el borde de la casilla.
        2. Establece en false el atributo opaque de la casilla.
        3. Establece el layout de la casilla.
        4. Agrega un MouseListener a la casilla.
        5. Establece el tipo de fuente y el color de texto de los JLabel planetsNameLabel y spaceShipsAmountLabel.
        
    */
    private void initComponents() {
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.addMouseListener(this);   
        planetNameLabel.setFont(new Font("Serif", Font.BOLD, 14));
        spaceShipsAmountLabel.setFont(new Font("Serif", Font.BOLD, 14));
        planetNameLabel.setForeground(Color.white);
        spaceShipsAmountLabel.setForeground(Color.white);
    }
    
    //Metodo encargado de establecerlas etiquetas de la casilla mediante el llamado a otros metodos
    public void setGuiInformation(){
        setForegroundLabelsColor();
        insertPlanetName();
        if(board.getMap().isShowSpaceShips()){
            insertSpaceShipsAmount();
        }
        insertStatistics();
    }
    
    /*
    Metodo encargado de insertar un TooTip a la casilla dependiendo de algunas validaciones.
        1. Si es planeta neutra.
            1.1 Si el mapa permite o no mostrar estadisticas
    
        2. Si no es planeta neutral.
            2.1 Si el mapa es o no un mapa ciego
    */
    public void insertStatistics(){
        if(planet.isNeutral()){
            if(board.getMap().isShowStatistics()){
                this.setToolTipText("<html> <font color=\"purple\"> Nombre: "+planet.getName()+"<br>"
                                  + "Produccion: "+planet.getProduction()+"<br>"
                                  + "Porcentaje de Muertes: "+planet.getDeathRate()+"</font> </html>");
            }
            else{
                this.setToolTipText("<html> <font color=\"purple\"> Nombre: " + planet.getName() + "</font> </html>");
            }
        }
        else{
            if(board.getMap().isBlindMap()){
                this.setToolTipText("<html> <font color=\"purple\"> Nombre: "+planet.getName()+"<br>"
                                  + "Propietario: "+planet.getOwner()+"</font> </html>");
            }
            else{
                this.setToolTipText("<html> <font color=\"purple\"> Nombre: "+planet.getName()+"<br>"
                                  + "Propietario: "+planet.getOwner()+"<br>"
                                  + "Produccion: "+planet.getProduction()+"<br>"
                                  + "Porcentaje de Muertes: "+planet.getDeathRate()+"</font> </html>");
            }
        }
    }
    
    //Metodo encargado de insertar en la casilla un JLabel con el nombre del planeta.
    private  void insertPlanetName(){
        planetNameLabel.setText(planet.getName());
        this.add(planetNameLabel, BorderLayout.NORTH);
    }
    
    //Metodo encargado de insertar en la casilla un JLabel con la cantidad de naves que tiene el planeta.
    private void insertSpaceShipsAmount(){
        spaceShipsAmountLabel.setText(String.valueOf(planet.getSpaceShips()));
        this.add(spaceShipsAmountLabel, BorderLayout.SOUTH);
    }
    
    /*
    Metodo encargado de establecer el color del texto de las JLabel de la clase.
    Por defecto el color es blanco, pero si el color de la casilla es distinto a rojo, magenta, gris oscuro
    o azul entonces se modifica el color a rojo.
    */
    private void setForegroundLabelsColor(){
        if(this.color != null){
            if(this.color == Color.red || this.color == Color.MAGENTA || this.color == Color.darkGray || this.color == Color.blue); //{No hacer nada} 
            else{
                planetNameLabel.setForeground(Color.red);
                spaceShipsAmountLabel.setForeground(Color.red);
            } 
        }
    }
    
    /*
    Metodo encargado de establecer el valor del atributo image.
    */
    public void setImage(int index){
        switch(index){
            case 0:
                image = getImage("/planet1.png");
            break;
            case 1:
                image = getImage("/planet2.png");
            break;
            case 2:
                image = getImage("/planet3.png");
            break;
            case 3:
                image = getImage("/planet4.png");
            break;
        }
    }
     
    /*
    Metodo encargado de obtener una nueva instancia del objeto ImageIcon en base al parametro
    path que contiene la ruta de la imagen que se desea almacenar.
    */
    private ImageIcon getImage(String path) {
        java.net.URL localizacion = this.getClass().getResource(path);
        if (localizacion != null) {
            return new ImageIcon(localizacion);
        } 
        else {
            System.err.println("No se ha encontrado el archivo: " + path);
            return null;
        }
    }
    
    /*
    Sobrecarga del metodo paintComponent. Establece el fondo de la casilla al valor 
    contenido dentro de la variable image solo si el valor de las misma no es null.
    */
    @Override
     public void paintComponent(Graphics g){
        if(image != null){
            super.paintComponent(g);
            g.drawImage(image.getImage(), this.getWidth()/4, this.getHeight()/4, this.getWidth()/2, this.getHeight()/2, this);
        }
    }
   
    @Override
    public void mouseClicked(MouseEvent e){
        this.setVisible(false);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}