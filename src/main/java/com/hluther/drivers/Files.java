package com.hluther.drivers;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
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
    Metodo encargado de crear la carpeta principal que almacenara todos los proyectos.
    */
    public void createRootFolder(){
        File rootFolder = new File("/home/helmuth/Documentos/ProyectosCSV");
        if(!rootFolder.exists()){
            rootFolder.mkdir();
        }
    }
    
    /*
    Metodo encargado de crear carpetas dentro la ruta que se especifica como parametro.
    */
    public void createFolder(String path){
        File folder = new File("/home/helmuth/Documentos/ProyectosCSV/" + path);
        folder.mkdir();    
    }
    
    /*
    Metodo encargado de crear archivos dentro la ruta que se especifica como parametro.
    */
    public void createFile(String path){
        File file = new File ("/home/helmuth/Documentos/ProyectosCSV/"+path+".csv");
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
    Metodo encarado de borrar un archivo
    */
    public void deleteFile(String path){
        File file = new File ("/home/helmuth/Documentos/ProyectosCSV/"+path+".csv");
        file.delete();
    }
    
    /*
    Metodo encargado de borrar una carpeta y tods los archivos que contenga.
    */
    public void deleteFolder(String path){
        File file = new File ("/home/helmuth/Documentos/ProyectosCSV/"+path);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                File fileR = new File (f.getPath());
                fileR.delete();
            }
        }
        file.delete();
    }

}
    
