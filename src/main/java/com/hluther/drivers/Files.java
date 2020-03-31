package com.hluther.drivers;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author helmuth
 */
public class Files {

    private BufferedReader bufferReader;
    private String data;
    private String text;

    /*
    Metodo que recibe como parametro un path, el cual utiliza para la apertura de 
    un archivo y su posterior lectura. Devuelve un String con todos los datos 
    contenidos dentro del archivo.
    */
    public String openFile(String path){
        text = "";
	try {
            bufferReader = new BufferedReader(new FileReader(path));
            while ((data = bufferReader.readLine()) != null){    
                text = text + data + "\n";
            } 
	}
        catch (EOFException ex) {
            System.out.println("ERROR: Lectura finalizada");
	}
        catch (IOException ex) {
            System.out.println("ERROR: No se puede leer archivo");
	}
        finally{
            try {
		bufferReader.close();
            } 
            catch (IOException ex) {
		System.out.println("ERROR: No se pudo cerrar el archivo");
            }
	}
        return text;
    }
    
    /*
    Metodo encargado de crear archivos dentro la ruta que se especifica como parametro.
    */
    public void createFile(String path, String data){
        FileWriter fichero;
	try {
            fichero = new FileWriter(path.replace(".json", "")+".json");
            fichero.write(data);
            fichero.close();
	} catch (IOException ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }
}
    
