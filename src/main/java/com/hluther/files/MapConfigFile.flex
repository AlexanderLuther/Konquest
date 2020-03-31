package com.hluther.lexer;
import com.hluther.gui.Konquest;
import com.hluther.parser.MapConfigFileSym;
import java_cup.runtime.*;

%%//

/* 
----------------------------------- Opciones y declaraciones de JFlex -----------------------------------
*/
%class MapConfigFileLexer
%cup
%cupdebug
%line 
%column
%public

Letter = [a-zA-Z]
Number = [0-9]
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]


/* 
----------------------------------- Codigo Java -----------------------------------
*/
%{
    private Konquest konquestFrame;
    
    public MapConfigFileLexer(java.io.Reader in, Konquest konquestFrame) {
        this.konquestFrame = konquestFrame;    
        this.zzReader = in;
    }
        
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn, value);
    }

    private void printToken(String token){
        System.out.println(token);
    }

    private void printError(String lexeme, int line, int column){
        konquestFrame.printMapConfigFileLexicalError(lexeme, line, column);
    }
%}

%%//

/* 
----------------------------------- Reglas Lexicas -----------------------------------
*/
<YYINITIAL>{
    "MAPA"                      {printToken("MAP"); return symbol(MapConfigFileSym.MAP, new String("MAPA"));}    
    "NEUTRALES"                 {printToken("NEUTRAL"); return symbol(MapConfigFileSym.NEUTRAL, new String("NEUTRALES"));}
    "PLANETAS"                  {printToken("PLANETS"); return symbol(MapConfigFileSym.PLANETS, new String("PLANETAS"));}
    "PLANETAS_NEUTRALES"        {printToken("NEUTRALPLANETS"); return symbol(MapConfigFileSym.NEUTRALPLANETS, new String("PLANETAS_NEUTRALES"));}
    "JUGADORES"                 {printToken("PLAYERS"); return symbol(MapConfigFileSym.PLAYERS, new String("JUGADORES"));}
    "HUMANO"                    {printToken("HUMAN"); return symbol(MapConfigFileSym.HUMAN, new String("HUMANO"));}
    "DIFICIL"                    {printToken("HARD"); return symbol(MapConfigFileSym.HARD, new String("DIFICIL"));}
    "FACIL"                     {printToken("EASY"); return symbol(MapConfigFileSym.EASY, new String("FACIL"));}
    "true"                      {printToken("TRUE"); return symbol(MapConfigFileSym.TRUE, new String("true"));}
    "false"                     {printToken("FALSE"); return symbol(MapConfigFileSym.FALSE, new String("false"));}
    "id"                        {printToken("ID"); return symbol(MapConfigFileSym.ID, new String("id"));}
    "tamaño"                    {printToken("SIZE"); return symbol(MapConfigFileSym.SIZE, new String("tamaño"));}
    "filas"                     {printToken("ROWS"); return symbol(MapConfigFileSym.ROWS, new String("filas"));}
    "columnas"                  {printToken("COLUMNS"); return symbol(MapConfigFileSym.COLUMNS, new String("columnas"));}
    "alAzar"                    {printToken("RANDOM"); return symbol(MapConfigFileSym.RANDOM, new String("alAzar"));}
    "planetasNeutrales"         {printToken("NEUTRALP"); return symbol(MapConfigFileSym.NEUTRALP, new String("planetasNeutrales"));}
    "mapaCiego"                 {printToken("BLIND"); return symbol(MapConfigFileSym.BLIND, new String("mapaCiego"));}
    "acumular"                  {printToken("ACCUMULATE"); return symbol(MapConfigFileSym.ACCUMULATE, new String("acumular"));}
    "mostrarNaves"              {printToken("SHOWSPACESHIPS"); return symbol(MapConfigFileSym.SHOWSPACESHIPS, new String("mostrarNaves"));}
    "mostrarEstadisticas"       {printToken("SHOWSTATISTICS"); return symbol(MapConfigFileSym.SHOWSTATISTICS, new String("mostrarEstadisticas"));}
    "produccion"                {printToken("PRODUCTION"); return symbol(MapConfigFileSym.PRODUCTION, new String("produccion"));}
    "finalizacion"              {printToken("COMPLETION"); return symbol(MapConfigFileSym.COMPLETION, new String("finalizacion"));}
    "naves"                     {printToken("SPACESHIPS"); return symbol(MapConfigFileSym.SPACESHIPS, new String("naves"));}
    "porcentajeMuertes"         {printToken("DEATHRATE"); return symbol(MapConfigFileSym.DEATHRATE, new String("porcentajeMuertes"));}
    "planetas"                  {printToken("PLAYERPLANETS"); return symbol(MapConfigFileSym.PLAYERPLANETS, new String("planetas"));}
    "tipo"                      {printToken("TYPE"); return symbol(MapConfigFileSym.TYPE, new String("tipo"));}
    "nombre"                    {printToken("NAME"); return symbol(MapConfigFileSym.NAME, new String("nombre"));}
    "{"                         {printToken("CURLYBRACKETO"); return symbol(MapConfigFileSym.CURLYBRACKETO, new String("{"));} 
    "}"                         {printToken("CURLYBRACKETC"); return symbol(MapConfigFileSym.CURLYBRACKETC, new String("}"));}
    "["                         {printToken("SQUAREBRACKETO"); return symbol(MapConfigFileSym.SQUAREBRACKETO, new String("["));}
    "]"                         {printToken("SQUAREBRACKETC"); return symbol(MapConfigFileSym.SQUAREBRACKETC, new String("]"));}
    ":"                         {printToken("COLON"); return symbol(MapConfigFileSym.COLON, new String(":"));}
    ","                         {printToken("COMMA"); return symbol(MapConfigFileSym.COMMA, new String(","));}
    "0" |(("1"|"2"|"3"|"4"|"5"|"6"|"7"|"8"|"9") {Number}*)                             {printToken("INTEGER"); return symbol(MapConfigFileSym.INTEGER, new String(yytext()));}
    ("\"") ({Letter} {Letter}? {Letter}?) ("\"")                           {printToken("NAMEPLANET"); return symbol(MapConfigFileSym.NAMEPLANET, new String(yytext()));}
    ("\"") {Letter} ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ("\"")       {printToken("NAMEPLAYER"); return symbol(MapConfigFileSym.NAMEPLAYER, new String(yytext()));}
    ("\"") ({Letter} | "_" | "$") ({Letter} | {Number} | "_" | "$" | "-")*  ("\"")         {printToken("NAMEID"); return symbol(MapConfigFileSym.NAMEID, new String(yytext()));}
    ("0.") {Number}? {Number}? {Number}? {Number}? {Number}? {Number}?          {printToken("DOUBLE"); return symbol(MapConfigFileSym.DOUBLE, new String(yytext()));}
    {WhiteSpace}    {}//Ignore
     .              {printToken("ERROR"); printError(new String(yytext()), (yyline+1), yycolumn);}  
}
