/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis Xmin Abstiegsparser
 * - Testklasse Token
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

import org.junit.Test;

public class TestXScanner1 extends TokenStreamTest {
	
	@Test
	public void tokenID() throws Exception {
		testTokenList("  a ", false, new Token(Token.ID,"a",1,3));
		testTokenList("  A ", false, new Token(Token.ID,"A",1,3));
		testTokenList("  x ", false, new Token(Token.ID,"x",1,3));
		testTokenList("  X ", false, new Token(Token.ID,"X",1,3));
		testTokenList("  X0 ", false, new Token(Token.ID,"X0",1,3));
		testTokenList("  a1 ", false, new Token(Token.ID,"a1",1,3));
		testTokenList("  abcdefghijklmnopqrstuvwxyz ", false, new Token(Token.ID,"abcdefghijklmnopqrstuvwxyz",1,3));
		testTokenList("  ABCDEFGHIJKLMNOPQRSTUVWXYZ ", false, new Token(Token.ID,"ABCDEFGHIJKLMNOPQRSTUVWXYZ",1,3));
		testTokenList("  NULL ", false, new Token(Token.ID,"NULL",1,3));
		testTokenList("  NulL ", false, new Token(Token.ID,"NulL",1,3));
		testTokenList("  null ", false, new Token(Token.ID,"null",1,3));
		testTokenList("  elsethen ", false, new Token(Token.ID,"elsethen",1,3));
		testTokenList("  beginner ", false, new Token(Token.ID,"beginner",1,3));
		testTokenList("  0a1 ", false, new Token(Token.INTCONST,"0",1,3), new Token(Token.ID,"a1",1,4));
		testTokenList("  9a8 ", false, new Token(Token.INTCONST,"9",1,3), new Token(Token.ID,"a8",1,4));
	}

	@Test
	public void tokenIntConst1() throws Exception {
		testTokenList("  0 ", false, new Token(Token.INTCONST,"0",1,3));
		testTokenList("  9 ", false, new Token(Token.INTCONST,"9",1,3));
		testTokenList("  1234567890 ", false, new Token(Token.INTCONST,"1234567890",1,3));
	}

	@Test
	public void tokenIntConst2() throws Exception {
		testTokenList("  00 ", true, new Token(Token.INTCONST,"0",1,3), 
									 new Token(Token.INTCONST,"0",1,4));
		testTokenList("  004500 ", true, new Token(Token.INTCONST,"0",1,3), 
										 new Token(Token.INTCONST,"0",1,4), 
										 new Token(Token.INTCONST,"4500",1,5));
		testTokenList("  078 ", true, new Token(Token.INTCONST,"0",1,3), 
									  new Token(Token.INTCONST,"78",1,4));
		testTokenList("  -0 ", true, new Token(Token.MINUS, "-", 1,3), 
									 new Token(Token.INTCONST,"0",1,4));
		testTokenList("  -1 ", true, new Token(Token.MINUS, "-", 1,3), 
				 					 new Token(Token.INTCONST,"1",1,4));
	}
	
	@Test
	public void tokenFloatConst1() throws Exception {
		testTokenList("  0. ", false, new Token(Token.FLOATCONST,"0.",1,3));
		testTokenList("  1. ", false, new Token(Token.FLOATCONST,"1.",1,3));
		testTokenList("  1.2 ", false, new Token(Token.FLOATCONST,"1.2",1,3));
		testTokenList("  1.23 ", false, new Token(Token.FLOATCONST,"1.23",1,3));
		testTokenList("  3.4e5 ", false, new Token(Token.FLOATCONST,"3.4e5",1,3));
		testTokenList("  3.4e567 ", false, new Token(Token.FLOATCONST,"3.4e567",1,3));
		testTokenList("  3.4e-5 ", false, new Token(Token.FLOATCONST,"3.4e-5",1,3));
		testTokenList("  3.4e-567 ", false, new Token(Token.FLOATCONST,"3.4e-567",1,3));
		testTokenList("  6.7E8 ", false, new Token(Token.FLOATCONST,"6.7E8",1,3));
		testTokenList("  6.7E-8 ", false, new Token(Token.FLOATCONST,"6.7E-8",1,3));
		testTokenList("  6.7E-890 ", false, new Token(Token.FLOATCONST,"6.7E-890",1,3));
		testTokenList("  0.0e ", false, 
				new Token(Token.FLOATCONST,"0.0",1,3),
				new Token(Token.ID,"e",1,6));
		testTokenList("  0.0E ", false,  
				new Token(Token.FLOATCONST,"0.0",1,3),
				new Token(Token.ID,"E",1,6));
		testTokenList("  0.0e- ", false,  
				new Token(Token.FLOATCONST,"0.0",1,3),
				new Token(Token.ID,"e",1,6),
				new Token(Token.MINUS,"-",1,7));
		testTokenList("  0.0E- ", false,  
				new Token(Token.FLOATCONST,"0.0",1,3),
				new Token(Token.ID,"E",1,6),
				new Token(Token.MINUS,"-",1,7));
		testTokenList("  0.0-e ", false, new Token(Token.FLOATCONST,"0.0",1,3),
										 new Token(Token.MINUS,"-",1,6),
										 new Token(Token.ID,"e",1,7));
		testTokenList("  0.03 ", false, new Token(Token.FLOATCONST,"0.03",1,3));
	}

	@Test
	public void tokenFloatConst2() throws Exception {
		testTokenList("  -1. ", false, new Token(Token.MINUS, "-", 1,3), 
				 				new Token(Token.FLOATCONST,"1.",1,4));
		testTokenList("  -1.2 ", false, new Token(Token.MINUS, "-", 1,3), 
				 					    new Token(Token.FLOATCONST,"1.2",1,4));
		testTokenList("  3.4e05 ", false, new Token(Token.FLOATCONST,"3.4e0",1,3), 
										  new Token(Token.INTCONST,"5",1,8));
		testTokenList("  06.7e8 ", false, new Token(Token.INTCONST,"0",1,3), 
										  new Token(Token.FLOATCONST,"6.7e8",1,4));
		testTokenList("  9.01e-05. ", false, new Token(Token.FLOATCONST,"9.01e-0",1,3), 
				    						 new Token(Token.FLOATCONST,"5.",1,10));
		testTokenList("  6.07E089 ", false, new Token(Token.FLOATCONST,"6.07E0",1,3), 
				  							new Token(Token.INTCONST,"89",1,9));
	}

	@Test
	public void tokenStringConst() throws Exception {
		testTokenList("  \"a\" ", false, new Token(Token.STRINGCONST,"\"a\"",1,3));
		testTokenList("  \"\" ", false, new Token(Token.STRINGCONST,"\"\"",1,3));
		testTokenList("  \"abcdefghijklmnopqrstuvwxyz\" ", false, 
				new Token(Token.STRINGCONST,"\"abcdefghijklmnopqrstuvwxyz\"",1,3));
		testTokenList("  \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\" ", false, 
				new Token(Token.STRINGCONST,"\"ABCDEFGHIJKLMNOPQRSTUVWXYZ\"",1,3));
		testTokenList("  \"\\\".: abcd\"", false, 
				new Token(Token.STRINGCONST,"\"\\\".: abcd\"",1,3));
		testTokenList("  \"+\" ", false, new Token(Token.INVALID,"\"+",1,3), 
										 new Token(Token.INVALID,"\" ",1,5));
		testTokenList("  \";\" ", false, new Token(Token.INVALID,"\";",1,3), 
				 						 new Token(Token.INVALID,"\" ",1,5));
		testTokenList("  \"?\" ", false, new Token(Token.INVALID,"\"?",1,3), 
				 						 new Token(Token.INVALID,"\" ",1,5));
		testTokenList("  \"\\\" ", false, new Token(Token.INVALID,"\"\\\" ",1,3));
		testTokenList("  \"abcd ", false, new Token(Token.INVALID,"\"abcd ",1,3));
	}

	@Test
	public void tokenLBR() throws Exception {
		testTokenList("  ( ", false, new Token(Token.LBR,"(",1,3));
	}

	@Test
	public void tokenRBR() throws Exception {
		testTokenList("  ) ", false, new Token(Token.RBR,")",1,3));
	}

	@Test
	public void tokenMULT() throws Exception {
		testTokenList("  * ", false, new Token(Token.MULT,"*",1,3));
	}
	
	@Test
	public void tokenPLUS() throws Exception {
		testTokenList("  + ", false, new Token(Token.PLUS,"+",1,3));
	}
	
	@Test
	public void tokenMINUS() throws Exception {
		testTokenList("  - ", false, new Token(Token.MINUS,"-",1,3));
	}
	
	@Test
	public void tokenDOT() throws Exception {
		testTokenList("  . ", false, new Token(Token.DOT,".",1,3));
	}
	
	@Test
	public void tokenDIV() throws Exception {
		testTokenList("  / ", false, new Token(Token.DIV,"/",1,3));
	}
	
	@Test
	public void tokenCOLON() throws Exception {
		testTokenList("  : ", false, new Token(Token.COLON,":",1,3));
	}
	
	@Test
	public void tokenASSIGN() throws Exception {
		testTokenList("  := ", false, new Token(Token.ASSIGN,":=",1,3));
	}
	
	@Test
	public void tokenSEMICOLON() throws Exception {
		testTokenList("  ; ", false, new Token(Token.SEMICOLON,";",1,3));
	}
	
	@Test
	public void tokenLESS() throws Exception {
		testTokenList("  < ", false, new Token(Token.LESS,"<",1,3));
	}
	
	@Test
	public void tokenEQUALS() throws Exception {
		testTokenList("  = ", false, new Token(Token.EQUALS,"=",1,3));
	}
	
	@Test
	public void tokenMORE() throws Exception {
		testTokenList("  > ", false, new Token(Token.MORE,">",1,3));
	}

	@Test
	public void tokenBEGIN() throws Exception {
		testTokenList("  begin ", false, new Token(Token.BEGIN,"begin",1,3));
	}

	@Test
	public void tokenELS() throws Exception {
		testTokenList("  else ", false, new Token(Token.ELSE,"else",1,3));
	}

	@Test
	public void tokenEND() throws Exception {
		testTokenList("  end ", false, new Token(Token.END,"end",1,3));
	}

	@Test
	public void tokenFLOAT() throws Exception {
		testTokenList("  float ", false, new Token(Token.FLOAT,"float",1,3));
	}

	@Test
	public void tokenFOR() throws Exception {
		testTokenList("  for ", false, new Token(Token.FOR,"for",1,3));
	}

	@Test
	public void tokenIF() throws Exception {
		testTokenList("  if ", false, new Token(Token.IF,"if",1,3));
	}

	@Test
	public void tokenINT() throws Exception {
		testTokenList("  int ", false, new Token(Token.INT,"int",1,3));
	}

	@Test
	public void tokenPRINT() throws Exception {
		testTokenList("  print ", false, new Token(Token.PRINT,"print",1,3));
	}

	@Test
	public void tokenPROGRAM() throws Exception {
		testTokenList("  program ", false, new Token(Token.PROGRAM,"program",1,3));
	}

	@Test
	public void tokenREAD() throws Exception {
		testTokenList("  read ", false, new Token(Token.READ,"read",1,3));
	}

	@Test
	public void tokenSTRING() throws Exception {
		testTokenList("  string ", false, new Token(Token.STRING,"string",1,3));
	}

	@Test
	public void tokenTHEN() throws Exception {
		testTokenList("  then ", false, new Token(Token.THEN,"then",1,3));
	}

	@Test
	public void tokenWHILE() throws Exception {
		testTokenList("  while ", false, new Token(Token.WHILE,"while",1,3));
	}

	@Test
	public void tokenEOF() throws Exception {
		testTokenList(" ", false);
		testTokenList(" ", false, new Token(Token.EOF,"",1,2));
		testTokenList("", false, new Token(Token.EOF,"",1,1));
	}

	@Test
	public void invalidÄ() throws Exception {
		testTokenList(" ä ", false, new Token(Token.INVALID,"ä",1,2));	
	}
	
	@Test
	public void invalidGatter() throws Exception {
		testTokenList(" # ", false, new Token(Token.INVALID,"#",1,2));	
	}
	
	
}
