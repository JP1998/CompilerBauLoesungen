/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * - Scanner-Definition
 * 
 * **********************************************
 */


package de.dhbw.compiler.jflexxscanner;

%%

%class JFlexXScanner
%type Token
%function nextToken


%unicode
%line
%column

%public
%final


%%

/* Schluesselworte */
while                         { return new Token(Token.WHILE, yytext(), yyline+1, yycolumn+1); }
then                          { return new Token(Token.THEN, yytext(), yyline+1, yycolumn+1); }
string                        { return new Token(Token.STRING, yytext(), yyline+1, yycolumn+1); }
read                          { return new Token(Token.READ, yytext(), yyline+1, yycolumn+1); }
program                       { return new Token(Token.PROGRAM, yytext(), yyline+1, yycolumn+1); }
print                         { return new Token(Token.PRINT, yytext(), yyline+1, yycolumn+1); }
int                           { return new Token(Token.INT, yytext(), yyline+1, yycolumn+1); }
if                            { return new Token(Token.IF, yytext(), yyline+1, yycolumn+1); }
for                           { return new Token(Token.FOR, yytext(), yyline+1, yycolumn+1); }
float                         { return new Token(Token.FLOAT, yytext(), yyline+1, yycolumn+1); }
end                           { return new Token(Token.END, yytext(), yyline+1, yycolumn+1); }
else                          { return new Token(Token.ELSE, yytext(), yyline+1, yycolumn+1); }
begin                         { return new Token(Token.BEGIN, yytext(), yyline+1, yycolumn+1); }

/* Operatoren */
>                             { return new Token(Token.MORE, yytext(), yyline+1, yycolumn+1); }
=                             { return new Token(Token.EQUALS, yytext(), yyline+1, yycolumn+1); }
\<                            { return new Token(Token.LESS, yytext(), yyline+1, yycolumn+1); }
;                             { return new Token(Token.SEMICOLON, yytext(), yyline+1, yycolumn+1); }
:=                            { return new Token(Token.ASSIGN, yytext(), yyline+1, yycolumn+1); }
:                             { return new Token(Token.COLON, yytext(), yyline+1, yycolumn+1); }
\/                            { return new Token(Token.DIV, yytext(), yyline+1, yycolumn+1); }
\.                            { return new Token(Token.DOT, yytext(), yyline+1, yycolumn+1); }
-                             { return new Token(Token.MINUS, yytext(), yyline+1, yycolumn+1); }
\+                            { return new Token(Token.PLUS, yytext(), yyline+1, yycolumn+1); }
\*                            { return new Token(Token.MULT, yytext(), yyline+1, yycolumn+1); }
\(                            { return new Token(Token.LBR, yytext(), yyline+1, yycolumn+1); }
\)                            { return new Token(Token.RBR, yytext(), yyline+1, yycolumn+1); }

/* Konstanten */
/* Identifier */
[a-zA-Z][a-zA-Z0-9]*                               { return new Token(Token.ID, yytext(), yyline+1, yycolumn+1); }
/* Strings */
\"([a-zA-Z0-9\ \.:]|\\\")*\"                       { return new StringConstToken(Token.STRINGCONST, yytext(), yyline+1, yycolumn+1); }
/* Integer */
(0|[1-9]\d*)                                       { return new IntConstToken(Token.INTCONST, yytext(), yyline+1, yycolumn+1); }
/* Floats */
(0|[1-9]\d*)(\.\d*)?([eE]-?(0|[1-9]\d*))?          { return new FloatConstToken(Token.FLOATCONST, yytext(), yyline+1, yycolumn+1); }

/* Besondere Werte */
<<EOF>>                                            { return new Token(Token.EOF, "", yyline+1, yycolumn+1); }
\s                                                 { }
\"([a-zA-Z0-9\ \.:]|\\\")*[^a-zA-Z0-9\ \.:]?       { return new Token(Token.INVALID, yytext(), yyline+1, yycolumn+1); }
[^]			                                       { return new Token(Token.INVALID, yytext(), yyline+1, yycolumn+1); }

