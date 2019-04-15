/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 *
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 *
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

%%

%class JFlexASScanner
%function nextToken
%type  Token

%unicode
%line
%column
%public
%final

%xstate NUM, FRAC, EXP

%{
private static String unescape(String str) {
    return str.substring(1, str.length() - 1).replace("\\\"", "\"").replace("\\\\", "\\");
}
%}

%eofval{
%eofval}


%%

\[                            { return new Token(Token.LSBR, yytext(), yyline+1, yycolumn+1); }
\]                            { return new Token(Token.RSBR, yytext(), yyline+1, yycolumn+1); }
,                             { return new Token(Token.COMMA, yytext(), yyline+1, yycolumn+1); }
null                          { return new Token(Token.NULL, yytext(), yyline+1, yycolumn+1); }
\.\.                          { return new Token(Token.TO, yytext(), yyline+1, yycolumn+1); }
[0-9]+                        { return new NumToken(Token.NUM, yytext(), yyline+1, yycolumn+1); }
[0-9a-zA-Z]+                  { return new Token(Token.ID, yytext(), yyline+1, yycolumn+1); }
[0-9]+ / (\.\.)*[^\.]         { return new NumToken(Token.NUM, yytext(), yyline+1, yycolumn+1); }
[0-9]+\.[0-9]*(\^[0-9]+)?     { return new FracToken(Token.FRAC, yytext(), yyline+1, yycolumn+1); }
\"([0-9a-zA-Z ]|\\\\|\\\")*\" { return new StringToken(Token.STR, yytext(), yyline+1, yycolumn+1, unescape(yytext())); }
<<EOF>>                       { return new Token(Token.EOF, "", yyline+1, yycolumn+1); }
\s                            { }
[^]			                  { return new Token(Token.INVALID, yytext(), yyline+1, yycolumn+1); }
