package com.hluther.drivers;

import com.hluther.gui.Konquest;
import com.hluther.lexer.GameSaveFileLexer;
import com.hluther.lexer.MapConfigFileLexer;
import com.hluther.parser.GameSaveFileParser;
import com.hluther.parser.MapConfigFileParser;
import java.io.StringReader;

/**
 *
 * @author helmuth
 */
public class Analisys {
    
    public void doMapConfigFileAnalysis(String text, Konquest konquestFrame){
        System.out.println("sdafsdfad");
        try { 
            new MapConfigFileParser(new MapConfigFileLexer(new StringReader(text), konquestFrame), konquestFrame).parse();
        } 
        catch (Exception ex) {}
    }
    
    public void doGameSaveFileAnalysis(String text, Konquest konquestFrame){
        try { 
            new GameSaveFileParser(new GameSaveFileLexer(new StringReader(text), konquestFrame), konquestFrame).parse();
        } 
        catch (Exception ex) {}
    }
}
