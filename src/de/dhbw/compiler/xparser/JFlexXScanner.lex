/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X Abstiegsparser
 * - Scanner-Definition
 * 
 * **********************************************
 */


package de.dhbw.compiler.xparser;

%%

%class JFlexXScanner
%type Token
%function nextToken


%unicode
%line
%column

%public
%final

%xstate INT, FRACTION, EXPVZ, EXP, STRING

%{
	private int intvalue=0;
	private String textvalue ="";
	private int fracvalue=0;
	private int fraclength=0;
	private int expvalue=0;
	private int expsign=1;
	private int startline =0;
	private int startcolumn =0;
	private String stringvalue="";
%}



%%

read					{ return new Token(Token.READ, yytext(), yyline+1, yycolumn+1); }
print					{ return new Token(Token.PRINT, yytext(), yyline+1, yycolumn+1); }
int						{ return new Token(Token.INT, yytext(), yyline+1, yycolumn+1); }
float					{ return new Token(Token.FLOAT, yytext(), yyline+1, yycolumn+1); }
string					{ return new Token(Token.STRING, yytext(), yyline+1, yycolumn+1); }
\+						{ return new Token(Token.PLUS, yytext(), yyline+1, yycolumn+1); }
\-						{ return new Token(Token.MINUS, yytext(), yyline+1, yycolumn+1); }
\*						{ return new Token(Token.MULT, yytext(), yyline+1, yycolumn+1); }
\/						{ return new Token(Token.DIV, yytext(), yyline+1, yycolumn+1); }
:=						{ return new Token(Token.ASSIGN, yytext(), yyline+1, yycolumn+1); }
\(						{ return new Token(Token.LBR, yytext(), yyline+1, yycolumn+1); }
\)						{ return new Token(Token.RBR, yytext(), yyline+1, yycolumn+1); }
\<						{ return new Token(Token.LESS, yytext(), yyline+1, yycolumn+1); }
>						{ return new Token(Token.MORE, yytext(), yyline+1, yycolumn+1); }
=						{ return new Token(Token.EQUALS, yytext(), yyline+1, yycolumn+1); }
if						{ return new Token(Token.IF, yytext(), yyline+1, yycolumn+1); }
then					{ return new Token(Token.THEN, yytext(), yyline+1, yycolumn+1); }
else					{ return new Token(Token.ELSE, yytext(), yyline+1, yycolumn+1); }
while					{ return new Token(Token.WHILE, yytext(), yyline+1, yycolumn+1); }
for						{ return new Token(Token.FOR, yytext(), yyline+1, yycolumn+1); }
;						{ return new Token(Token.SEMICOLON, yytext(), yyline+1, yycolumn+1); }
begin					{ return new Token(Token.BEGIN, yytext(), yyline+1, yycolumn+1); }
end						{ return new Token(Token.END, yytext(), yyline+1, yycolumn+1); }
program					{ return new Token(Token.PROGRAM, yytext(), yyline+1, yycolumn+1); }
\.						{ return new Token(Token.DOT, yytext(), yyline+1, yycolumn+1); }
:						{ return new Token(Token.COLON, yytext(), yyline+1, yycolumn+1); }


[a-zA-Z][a-zA-Z0-9]*	{ return new Token(Token.ID, yytext(), yyline+1, yycolumn+1); }

0						{ return new IntConstToken(Token.INTCONST, yytext(), yyline+1, yycolumn+1, 0); }
0/\.					{ yybegin(INT); textvalue=yytext(); startline=yyline+1; startcolumn=yycolumn+1;
						  intvalue=yytext().charAt(0)-'0'; fracvalue=0; fraclength=0; expvalue=0; }
[1-9]					{ yybegin(INT); textvalue=yytext(); startline=yyline+1; startcolumn=yycolumn+1;
						  intvalue=yytext().charAt(0)-'0'; fracvalue=0; fraclength=0; expvalue=0; }

\"						{ yybegin(STRING); textvalue="\""; stringvalue=""; startline=yyline+1; startcolumn=yycolumn+1; }

(\/\*)~(\*\/)			{ /* eat comments */ }
[\ \t\b\f\r\n]+			{ /* eat whitespace */ }
<<EOF>>					{ return new Token(Token.EOF, yytext(), yyline+1, yycolumn+1); }
[^]						{ return new Token(Token.INVALID,  yytext(), yyline+1, yycolumn+1); }

<STRING> {
	[a-zA-Z \.:]		{ textvalue+=yytext();  stringvalue+=yytext(); }
	\\\"				{ textvalue+="\\\""; stringvalue+="\""; }
	\"					{ yybegin(YYINITIAL); return new StringConstToken(Token.STRINGCONST, textvalue+"\"", startline, startcolumn, stringvalue); }
	[^]					{ yybegin(YYINITIAL); return new Token(Token.INVALID, textvalue+yytext(), startline, startcolumn); }
	<<EOF>>				{ yybegin(YYINITIAL); return new Token(Token.INVALID, textvalue, startline, startcolumn); } 
}

<INT> {
	[0-9]					{ intvalue = 10*intvalue + yytext().charAt(0)-'0'; 
							  textvalue+=yytext();}
	\.						{ yybegin(FRACTION); 
							  textvalue+=yytext();}
	[eE]/-?[0-9]			{ yybegin(EXPVZ);  textvalue += yytext(); }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new IntConstToken(Token.INTCONST, textvalue, startline, startcolumn, intvalue); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new IntConstToken(Token.INTCONST, textvalue, startline, startcolumn, intvalue);}
}

<FRACTION> {
	[0-9]					{ fracvalue= 10*fracvalue+ yytext().charAt(0)-'0'; 
							  fraclength++;
							  textvalue+=yytext();}
	[eE]/-?[0-9]			{ yybegin(EXPVZ); 
							  textvalue+=yytext();}
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatConstToken(Token.FLOATCONST, textvalue, startline, startcolumn,intvalue+fracvalue/Math.pow(10,fraclength)); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatConstToken(Token.FLOATCONST, textvalue, startline, startcolumn,intvalue+fracvalue/Math.pow(10,fraclength)); }	
}

<EXPVZ> {
	-0|0					{ yybegin(YYINITIAL);
							  return new FloatConstToken(Token.FLOATCONST, textvalue+yytext(), startline, startcolumn,intvalue+fracvalue/Math.pow(10,fraclength)); }
	-[1-9]					{ yybegin(EXP); expvalue=yytext().charAt(1)-'0'; expsign=-1; textvalue+=yytext(); }
	[1-9]					{ yybegin(EXP); expvalue=yytext().charAt(0)-'0'; expsign=1; textvalue+=yytext(); }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new Token(Token.INVALID, textvalue, startline, startcolumn); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new Token(Token.INVALID, textvalue, startline, startcolumn); }
}

<EXP> {
	[0-9]					{ expvalue = 10* expvalue + yytext().charAt(0)-'0'; textvalue+=yytext(); }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatConstToken(Token.FLOATCONST, textvalue, startline, startcolumn, (intvalue+fracvalue/Math.pow(10,fraclength))*Math.pow(10,expvalue*expsign)); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatConstToken(Token.FLOATCONST, textvalue, startline, startcolumn, (intvalue+fracvalue/Math.pow(10,fraclength))*Math.pow(10,expvalue*expsign)); } 
}

