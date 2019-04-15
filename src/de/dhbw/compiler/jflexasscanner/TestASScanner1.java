/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testfälle Aufgabe 1
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import org.junit.Test;

public class TestASScanner1 extends TokenStreamTest {
	
	@Test
	public void tokenLSBR() throws Exception {
		testTokenList("  [ ", false, new Token(Token.LSBR,"[",1,3));
	}

	@Test
	public void tokenRSBR() throws Exception {
		testTokenList("  ] ", false, new Token(Token.RSBR,"]",1,3));
	}

	@Test
	public void tokenCOMMA() throws Exception {
		testTokenList("  , ", false, new Token(Token.COMMA,",",1,3));
	}

	@Test
	public void tokenZAHL() throws Exception {
		testTokenList("  0 ", false, new Token(Token.NUM,"0",1,3));
		testTokenList("  9 ", false, new Token(Token.NUM,"9",1,3));
		testTokenList("  1234567890 ", false, new Token(Token.NUM,"1234567890",1,3));
	}

	@Test
	public void tokenID() throws Exception {
		testTokenList("  a ", false, new Token(Token.ID,"a",1,3));
		testTokenList("  A ", false, new Token(Token.ID,"A",1,3));
		testTokenList("  x ", false, new Token(Token.ID,"x",1,3));
		testTokenList("  X ", false, new Token(Token.ID,"X",1,3));
		testTokenList("  0a ", false, new Token(Token.ID,"0a",1,3));
		testTokenList("  X0 ", false, new Token(Token.ID,"X0",1,3));
		testTokenList("  1234567890a ", false, new Token(Token.ID,"1234567890a",1,3));
		testTokenList("  abcdefghijklmnopqrstuvwxyz ", false, new Token(Token.ID,"abcdefghijklmnopqrstuvwxyz",1,3));
		testTokenList("  ABCDEFGHIJKLMNOPQRSTUVWXYZ ", false, new Token(Token.ID,"ABCDEFGHIJKLMNOPQRSTUVWXYZ",1,3));
		testTokenList("  NULL ", false, new Token(Token.ID,"NULL",1,3));
		testTokenList("  NulL ", false, new Token(Token.ID,"NulL",1,3));
	}

	@Test
	public void tokenNULL() throws Exception {
		testTokenList("  null ", false, new Token(Token.NULL,"null",1,3));
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
	
	public void invalidGatter() throws Exception {
		testTokenList(" # ", false, new Token(Token.INVALID,"#",1,3));	
	}
	
	@Test
	public void sentence1() throws Exception {
		testTokenList(" [ [ ] hallo ,,,Duda,123,76", false, 
				new Token(Token.LSBR,"[",1,2),
				new Token(Token.LSBR,"[",1,4),
				new Token(Token.RSBR,"]",1,6),
				new Token(Token.ID,"hallo",1,8),
				new Token(Token.COMMA,",",1,14),
				new Token(Token.COMMA,",",1,15),
				new Token(Token.COMMA,",",1,16),
				new Token(Token.ID,"Duda",1,17),
				new Token(Token.COMMA,",",1,21),
				new Token(Token.NUM,"123",1,22),
				new Token(Token.COMMA,",",1,25),
				new Token(Token.NUM,"76",1,26));
	}

	
	@Test
	public void sentence2() throws Exception {
		testTokenList("[34, x,peter,  34kurt, g56, null,null, 8]", false, 
				new Token(Token.LSBR,"[",1,1),
				new Token(Token.NUM,"34",1,2),
				new Token(Token.COMMA,",",1,4),
				new Token(Token.ID,"x",1,6),
				new Token(Token.COMMA,",",1,7),
				new Token(Token.ID,"peter",1,8),
				new Token(Token.COMMA,",",1,13),
				new Token(Token.ID,"34kurt",1,16),
				new Token(Token.COMMA,",",1,22),
				new Token(Token.ID,"g56",1,24),
				new Token(Token.COMMA,",",1,27),
				new Token(Token.NULL,"null",1,29),
				new Token(Token.COMMA,",",1,33),
				new Token(Token.NULL,"null",1,34),
				new Token(Token.COMMA,",",1,38),
				new Token(Token.NUM,"8",1,40),
				new Token(Token.RSBR,"]",1,41));
	}
	
	@Test
	public void sentence3() throws Exception {
		testTokenList("[ a, b, \n c, \r\nd]", false, 
				new Token(Token.LSBR,"[",1,1),
				new Token(Token.ID,"a",1,3),
				new Token(Token.COMMA,",",1,4),
				new Token(Token.ID,"b",1,6),
				new Token(Token.COMMA,",",1,7),
				new Token(Token.ID,"c",2,2),
				new Token(Token.COMMA,",",2,3),
				new Token(Token.ID,"d",3,1),
				new Token(Token.RSBR,"]",3,2));
	}

	
}
