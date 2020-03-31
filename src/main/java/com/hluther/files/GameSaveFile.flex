package com.hluther.lexer;
import com.hluther.gui.Konquest;
import com.hluther.parser.GameSaveFileSym;
import java_cup.runtime.*;
%%//

/* 
----------------------------------- Opciones y declaraciones de JFlex -----------------------------------
*/
%class GameSaveFileLexer
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
    
    public GameSaveFileLexer(java.io.Reader in, Konquest konquestFrame) {
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
        konquestFrame.printGameSaveLexicalError(lexeme, line, column);
    }
%}

%%//

/* 
----------------------------------- Reglas Lexicas -----------------------------------
*/
<YYINITIAL>{
    "MAPA"                      {printToken("MAP"); return symbol(GameSaveFileSym.MAP, new String("MAPA"));}    
    "NEUTRALES"                 {printToken("NEUTRAL"); return symbol(GameSaveFileSym.NEUTRAL, new String("NEUTRALES"));}
    "PLANETAS"                  {printToken("PLANETS"); return symbol(GameSaveFileSym.PLANETS, new String("PLANETAS"));}
    "PLANETAS_NEUTRALES"        {printToken("NEUTRALPLANETS"); return symbol(GameSaveFileSym.NEUTRALPLANETS, new String("PLANETAS_NEUTRALES"));}
    "JUGADORES"                 {printToken("PLAYERS"); return symbol(GameSaveFileSym.PLAYERS, new String("JUGADORES"));}
    "HUMANO"                    {printToken("HUMAN"); return symbol(GameSaveFileSym.HUMAN, new String("HUMANO"));}
    "DIFICIL"                   {printToken("HARD"); return symbol(GameSaveFileSym.HARD, new String("DIFICIL"));}
    "FACIL"                     {printToken("EASY"); return symbol(GameSaveFileSym.EASY, new String("FACIL"));}
    "ENVIOS"                    {printToken("SENDINGS"); return symbol(GameSaveFileSym.SENDINGS, new String("ENVIOS"));}
    "true"                      {printToken("TRUE"); return symbol(GameSaveFileSym.TRUE, new String("true"));}
    "false"                     {printToken("FALSE"); return symbol(GameSaveFileSym.FALSE, new String("false"));}
    "tamaño"                    {printToken("SIZE"); return symbol(GameSaveFileSym.SIZE, new String("tamaño"));}
    "filas"                     {printToken("ROWS"); return symbol(GameSaveFileSym.ROWS, new String("filas"));}
    "columnas"                  {printToken("COLUMNS"); return symbol(GameSaveFileSym.COLUMNS, new String("columnas"));}
    "fila"                      {printToken("ROW"); return symbol(GameSaveFileSym.ROW, new String("fila"));}
    "columna"                   {printToken("COLUMN"); return symbol(GameSaveFileSym.COLUMN, new String("columna"));}
    "mapaCiego"                 {printToken("BLIND"); return symbol(GameSaveFileSym.BLIND, new String("mapaCiego"));}
    "acumular"                  {printToken("ACCUMULATE"); return symbol(GameSaveFileSym.ACCUMULATE, new String("acumular"));}
    "mostrarNaves"              {printToken("SHOWSPACESHIPS"); return symbol(GameSaveFileSym.SHOWSPACESHIPS, new String("mostrarNaves"));}
    "mostrarEstadisticas"       {printToken("SHOWSTATISTICS"); return symbol(GameSaveFileSym.SHOWSTATISTICS, new String("mostrarEstadisticas"));}
    "produccion"                {printToken("PRODUCTION"); return symbol(GameSaveFileSym.PRODUCTION, new String("produccion"));}
    "finalizacion"              {printToken("COMPLETION"); return symbol(GameSaveFileSym.COMPLETION, new String("finalizacion"));}
    "naves"                     {printToken("SPACESHIPS"); return symbol(GameSaveFileSym.SPACESHIPS, new String("naves"));}
    "porcentajeMuertes"         {printToken("DEATHRATE"); return symbol(GameSaveFileSym.DEATHRATE, new String("porcentajeMuertes"));}
    "tipo"                      {printToken("TYPE"); return symbol(GameSaveFileSym.TYPE, new String("tipo"));}
    "nombre"                    {printToken("NAME"); return symbol(GameSaveFileSym.NAME, new String("nombre"));}
    "propietario"               {printToken("OWNER"); return symbol(GameSaveFileSym.OWNER, new String("propietario"));}
    "neutral"                   {printToken("NEUTRALTYPE"); return symbol(GameSaveFileSym.NEUTRALTYPE, new String("neutral"));}
    "color"                     {printToken("COLOR"); return symbol(GameSaveFileSym.COLOR, new String("color"));}
    "azul"                      {printToken("BLUE"); return symbol(GameSaveFileSym.BLUE, new String("azul"));}
    "amarillo"                  {printToken("YELLOW"); return symbol(GameSaveFileSym.YELLOW, new String("amarillo"));}
    "verde"                     {printToken("GREEN"); return symbol(GameSaveFileSym.GREEN, new String("verde"));}
    "rojo"                      {printToken("RED"); return symbol(GameSaveFileSym.RED, new String("rojo"));}
    "naranja"                   {printToken("ORANGE"); return symbol(GameSaveFileSym.ORANGE, new String("naranja"));}
    "grisClaro"                 {printToken("LIGHTGRAY"); return symbol(GameSaveFileSym.LIGHTGRAY, new String("grisClaro"));}
    "cyan"                      {printToken("CYAN"); return symbol(GameSaveFileSym.CYAN, new String("cyan"));}
    "grisOscuro"                {printToken("DARKGRAY"); return symbol(GameSaveFileSym.DARKGRAY, new String("grisOscuro"));}
    "blanco"                    {printToken("WHITE"); return symbol(GameSaveFileSym.WHITE, new String("blanco"));}
    "magenta"                   {printToken("MAGENTA"); return symbol(GameSaveFileSym.MAGENTA, new String("magenta"));}
    "turnoEnvio"                {printToken("INITIALTURN"); return symbol(GameSaveFileSym.INITIALTURN, new String("turnoEnvio"));}
    "turnoLlegada"              {printToken("ARRIVALTURN"); return symbol(GameSaveFileSym.ARRIVALTURN, new String("turnoLlegada"));}
    "filaInicial"               {printToken("INITIALROW"); return symbol(GameSaveFileSym.INITIALROW, new String("filaInicial"));}
    "columnaInicial"            {printToken("INITIALCOLUMN"); return symbol(GameSaveFileSym.INITIALCOLUMN, new String("columnaInicial"));}
    "filaObjetivo"              {printToken("TARGETROW"); return symbol(GameSaveFileSym.TARGETROW, new String("filaObjetivo"));}
    "columnaObjetivo"           {printToken("TARGETCOLUMN"); return symbol(GameSaveFileSym.TARGETCOLUMN, new String("columnaObjetivo"));}
    "{"                         {printToken("CURLYBRACKETO"); return symbol(GameSaveFileSym.CURLYBRACKETO, new String("{"));} 
    "}"                         {printToken("CURLYBRACKETC"); return symbol(GameSaveFileSym.CURLYBRACKETC, new String("}"));}
    "["                         {printToken("SQUAREBRACKETO"); return symbol(GameSaveFileSym.SQUAREBRACKETO, new String("["));}
    "]"                         {printToken("SQUAREBRACKETC"); return symbol(GameSaveFileSym.SQUAREBRACKETC, new String("]"));}
    ":"                         {printToken("COLON"); return symbol(GameSaveFileSym.COLON, new String(":"));}
    ","                         {printToken("COMMA"); return symbol(GameSaveFileSym.COMMA, new String(","));}
    "0" |(("1"|"2"|"3"|"4"|"5"|"6"|"7"|"8"|"9") {Number}*)                             {printToken("INTEGER"); return symbol(GameSaveFileSym.INTEGER, new String(yytext()));}
    ("\"") ({Letter} {Letter}? {Letter}?) ("\"")                           {printToken("NAMEPLANET"); return symbol(GameSaveFileSym.NAMEPLANET, new String(yytext()));}
    ("\"") {Letter} ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ({Letter} | {Number})? ("\"")       {printToken("NAMEPLAYER"); return symbol(GameSaveFileSym.NAMEPLAYER, new String(yytext()));}
    ("0.") {Number}? {Number}? {Number}? {Number}? {Number}? {Number}?          {printToken("DOUBLE"); return symbol(GameSaveFileSym.DOUBLE, new String(yytext()));}
    {WhiteSpace}    {}//Ignore
     .              {printToken("ERROR"); printError(new String(yytext()), (yyline+1), yycolumn);}  
}