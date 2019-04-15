/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis Xmin Abstiegsparser
 * - Testklasse Programme
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

import org.junit.Test;

public class TestXScanner3 extends TokenStreamTest {
	
	@Test
	public void program1() throws Exception {
		testTokenList("program test1;\nbegin\nend.",false, 
				new Token(Token.PROGRAM,"program",1,1),
				new Token(Token.ID,"test1",1,9),
				new Token(Token.SEMICOLON,";",1,14),
				new Token(Token.BEGIN,"begin",2,1),
				new Token(Token.END,"end",3,1),
				new Token(Token.DOT,".",3,4));
	}

	@Test
	public void program2() throws Exception {
		testTokenList(	"program test2;\n"+
						"	x : int;\n"+
						"	y : float;\n"+
						"	z : string;\n"+
						"begin\n"+
						"	x := 4+5+6.2;\n"+
						"	y := 3.56+1.2e3+45.e-67+4e34+3E-1;\n"+
						"	z := \"Hello \\\"World\\\"\" + \":\";\n"+
						"	z := \"Peter\" + 4;\n"+
						"	a := 3+4;\n"+
						"end.", false,
						// program test2;
						new Token(Token.PROGRAM,"program",1,1),
						new Token(Token.ID,"test2",1,9),
						new Token(Token.SEMICOLON,";",1,14),
						// 	x : int;
						new Token(Token.ID,"x",2,2),
						new Token(Token.COLON,":",2,4),
						new Token(Token.INT,"int",2,6),
						new Token(Token.SEMICOLON,";",2,9),
						// 	y : float;
						new Token(Token.ID,"y",3,2),
						new Token(Token.COLON,":",3,4),
						new Token(Token.FLOAT,"float",3,6),
						new Token(Token.SEMICOLON,";",3,11),
						// 	z : string;
						new Token(Token.ID,"z",4,2),
						new Token(Token.COLON,":",4,4),
						new Token(Token.STRING,"string",4,6),
						new Token(Token.SEMICOLON,";",4,12),
						// begin
						new Token(Token.BEGIN,"begin",5,1),
						// 	x := 4+5+6.2;
						new Token(Token.ID,"x",6,2),
						new Token(Token.ASSIGN,":=",6,4),
						new Token(Token.INTCONST,"4",6,7),
						new Token(Token.PLUS,"+",6,8),
						new Token(Token.INTCONST,"5",6,9),
						new Token(Token.PLUS,"+",6,10),
						new Token(Token.FLOATCONST,"6.2",6,11),
						new Token(Token.SEMICOLON,";",6,14),
						// 	y := 3.56+1.2e3+45.e-67+4e34+3E-1;
						new Token(Token.ID,"y",7,2),
						new Token(Token.ASSIGN,":=",7,4),
						new Token(Token.FLOATCONST,"3.56",7,7),
						new Token(Token.PLUS,"+",7,11),
						new Token(Token.FLOATCONST,"1.2e3",7,12),
						new Token(Token.PLUS,"+",7,17),
						new Token(Token.FLOATCONST,"45.e-67",7,18),
						new Token(Token.PLUS,"+",7,25),
						new Token(Token.FLOATCONST,"4e34",7,26),
						new Token(Token.PLUS,"+",7,30),
						new Token(Token.FLOATCONST,"3E-1",7,31),
						new Token(Token.SEMICOLON,";",7,35),
						// 	z := \"Hello \\\"World\\\"\" + \":\";
						new Token(Token.ID,"z",8,2),
						new Token(Token.ASSIGN,":=",8,4),
						new Token(Token.STRINGCONST,"\"Hello \\\"World\\\"\"",8,7),
						new Token(Token.PLUS,"+",8,25),
						new Token(Token.STRINGCONST,"\":\"",8,27),
						new Token(Token.SEMICOLON,";",8,30),
						// 	z := \"Peter\" + 4;
						new Token(Token.ID,"z",9,2),
						new Token(Token.ASSIGN,":=",9,4),
						new Token(Token.STRINGCONST,"\"Peter\"",9,7),
						new Token(Token.PLUS,"+",9,15),
						new Token(Token.INTCONST,"4",9,17),
						new Token(Token.SEMICOLON,";",9,18),
						// 	a := 3+4;
						new Token(Token.ID,"a",10,2),
						new Token(Token.ASSIGN,":=",10,4),
						new Token(Token.INTCONST,"3",10,7),
						new Token(Token.PLUS,"+",10,8),
						new Token(Token.INTCONST,"4",10,9),
						new Token(Token.SEMICOLON,";",10,10),
						// end.
						new Token(Token.END,"end",11,1),
						new Token(Token.DOT,".",11,4));
	}
}
