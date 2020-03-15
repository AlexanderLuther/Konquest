package com.hluther.drivers;

import com.hluther.gui.Konquest;
import com.hluther.lexer.MapConfigFileLexer;
import com.hluther.parser.MapConfigFileParser;
import java.io.StringReader;

/**
 *
 * @author helmuth
 */
public class AnalisysDriver {
    
    public void doMapConfigFileAnalysis(String text, Konquest konquestFrame){
        try { 
            new MapConfigFileParser(new MapConfigFileLexer(new StringReader(text), konquestFrame), konquestFrame).parse();
        } 
        catch (Exception ex) {}
    }
}
